package com.bookStore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.Customers;
import com.bookStore.entity.MyBook;
import com.bookStore.service.BookService;
import com.bookStore.service.CartItemService;
import com.bookStore.service.CustomerService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CartController {
@Autowired private BookService bservice;
@Autowired private CartItemService cartitemService;	
@Autowired private CustomerService customerService;

	@GetMapping("/find_customer")
	public String getAllCustomer(Model model) {
		/*List<Customers> list = customerService.getAllcustomers();
		ModelAndView showCustomers = new ModelAndView();
		showCustomers.setViewName("findCustomer");
		showCustomers.addObject("customers",list);
		return showCustomers;*/
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
	    return listByPage(1,model,"name","asc", null);
	}
	
	@GetMapping("/book_rental/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword) {
		Page<Book> page = bservice.listByPage(pageNum, sortField, sortDir, keyword);
		List<Book> listBook = page.getContent();
		long startCount = (pageNum - 1) * BookService.BOOK_PER_PAGE + 1;
		long endCount = startCount + BookService.BOOK_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; 
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("book", listBook);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "bookrental";
	
	}
	
	
	@GetMapping("/cartview")
	public String testviewCart(Model model,@CookieValue(value = "customerid", defaultValue = "") String customerId) {	
		Integer customerIdi = Integer.valueOf(customerId);
		Customers customer = customerService.getById(customerIdi);	
		List<MyBook> bookitem = cartitemService.listCartItems(customer);
		
		model.addAttribute("cartview",bookitem);
		model.addAttribute("customer",customer);
		return "/cartview";
		
	}
	
}
