package com.librarycos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.ModelAndView;

import com.librarycos.entity.Book;
import com.librarycos.entity.Customers;
import com.librarycos.entity.Rental;

import com.librarycos.service.CustomerService;

import com.librarycos.service.RentalService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MyBookController {
	

	@Autowired private CustomerService customerService;
	@Autowired private RentalService rentalService;
/*	@Autowired private RentalDetailService rentalDService;

	@GetMapping("/my_books")
	public String listFirstPage(Model model) {
		return listByPage(1,model,"firstname","asc", null);
	}
	
	@GetMapping("/my_books/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword){

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
		return "MyBook";
	}
		
	@GetMapping("/my_books/{customerid}")
	public String getAllMybooks(Model showModel,HttpServletResponse response,@PathVariable("customerid")int customerId){
		Customers customer = customerService.getById(customerId);
		List<Rental> rental = rentalService.listRental(customer);
		List<Book> booklist = new ArrayList<>();
		List<RentalDetail> rentalDetaillist = new ArrayList<>();
		for(Rental rentals : rental) {
			List<RentalDetail> rentalDetails = rentalDService.listRentalDetail(rentals);
			for(RentalDetail rentalDetail : rentalDetails) {
				Book book = rentalDetail.getBook();
				booklist.add(book);
				rentalDetaillist.add(rentalDetail);
			}	
		}
		showModel.addAttribute("customers",customer);
		showModel.addAttribute("book",booklist);
		showModel.addAttribute("rentaldetail",rentalDetaillist);
		showModel.addAttribute("rental",rental);
		
		
		return "/MyBook2";
	}

	
*/
}
