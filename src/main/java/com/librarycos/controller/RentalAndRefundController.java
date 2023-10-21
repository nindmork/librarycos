package com.librarycos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.librarycos.entity.Book;
import com.librarycos.entity.Customers;
import com.librarycos.service.BookService;
import com.librarycos.service.CustomerService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
@Controller
public class RentalAndRefundController {

	@Autowired private BookService bservice;

	@Autowired private CustomerService customerService;
	
	@GetMapping("/find_customer")
	public String getAllCustomer(Model model) {
		
		return listByPagefindCustomer(1,model,"firstname","asc", null);
	}
	
	@GetMapping("/find_customer/page/{pageNum}")
	public String listByPagefindCustomer(@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword) {
		Page<Customers> page = customerService.listByPage(pageNum, sortField, sortDir, keyword);
		List<Customers> listCustomers = page.getContent();
		long startCount = (pageNum - 1) * CustomerService.CUSTOMERS_PER_PAGE + 1;
		long endCount = startCount + CustomerService.CUSTOMERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; 
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("customers", listCustomers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "findCustomer";
	}
	
	
	
	@GetMapping("/book_rental/{customerid}/page/{pageNum}")
	public String listByPage(@PathVariable(name = "customerid")String customerId,@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword
			) {
		Page<Book> page = bservice.listByPage(pageNum, sortField, sortDir, keyword);
		List<Book> listBook = page.getContent();
		long startCount = (pageNum - 1) * BookService.BOOK_PER_PAGE + 1;
		long endCount = startCount + BookService.BOOK_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; 
		int cusid= Integer.valueOf(customerId);
		Customers customer = customerService.getById(cusid);
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("customer", customer);
		model.addAttribute("book", listBook);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "bookrental";
	
	}
	
	
	@GetMapping("/book_rental/{customerid}")
	public String getAllBookrental(Model model, HttpServletResponse response,@PathVariable("customerid")String customerId) {
		/// create a cookie
	    Cookie cookie = new Cookie("customerid", customerId);
	    cookie.setPath("/cartview");
	    //add a cookie to the response
	    //cookie.setMaxAge(7);
	    response.addCookie(cookie);
		/*List<Book>list=bservice.getAllBook();	
		return new ModelAndView("bookrental","book",list);*/
		
	    return listByPage(customerId,1,model,"name","asc", null );
	}
}
