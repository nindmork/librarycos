package com.bookStore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.Customers;

import com.bookStore.entity.Rental;
import com.bookStore.entity.RentalDetail;

import com.bookStore.service.CustomerService;
import com.bookStore.service.RentalDetailService;
import com.bookStore.service.RentalService;


import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MyBookController {
	

	@Autowired private CustomerService customerService;
	@Autowired private RentalService rentalService;
	@Autowired private RentalDetailService rentalDService;

	@GetMapping("/my_books")
	public ModelAndView getMyBooks(){
		List<Customers> list = customerService.getAllcustomers();
		ModelAndView showCustomers = new ModelAndView();
		showCustomers.setViewName("MyBook");
		showCustomers.addObject("customers",list);
		return showCustomers;
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
	
	

	

}
