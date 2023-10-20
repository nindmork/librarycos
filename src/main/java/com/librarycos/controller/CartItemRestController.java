package com.librarycos.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import com.librarycos.entity.Book;
import com.librarycos.entity.Customers;

import com.librarycos.service.CartException;

import com.librarycos.service.CustomerService;


import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletResponse;



@RestController
public class CartItemRestController {
	

	@Autowired
	private CustomerService customerService;
/*
	@PostMapping("/cart/add/{bookId}/{quantity}/{customerId}")
	public String addBookToCart(@PathVariable("bookId") Integer bookId,
			@PathVariable("quantity")Integer quantity,@PathVariable("customerId")Integer customerId) {
		try {
			Customers customer = customerService.getById(customerId);
			Integer updatedQuantity = cartitemService.addBook(bookId, quantity, customer);
			return updatedQuantity + " added to cart.";
		}catch (CartException ex){
			return ex.getMessage();
		}	
	}

	@DeleteMapping("/cart/remove/{bookId}/{customerId}")
	public String removeBook(@PathVariable("bookId") Integer bookId, @PathVariable("customerId")Integer customerId) {
		//Customers customer = customerService.getById(customerId);
		System.out.println(bookId);
		System.out.println(customerId);
		cartitemService.removeBook(bookId,customerId);
		return "ลบแล้ว";
	}
	@DeleteMapping("/refund/{rentaldetail_id}")
	public String Refund (@PathVariable("rentaldetail_id")int rental_detail_id,HttpServletResponse response) {
		List<RentalDetail> rentalDetails = rentalDService.listRentalDetailById(rental_detail_id);
		for(RentalDetail rentalDetail : rentalDetails) {
			Book book = rentalDetail.getBook();
			int rentalQuantity = rentalDetail.getQuantity();
			int bookQuantity = rentalDetail.getQuantity();
			book.setQuantity(rentalQuantity+bookQuantity);
		}
		rentalDService.deleteById(rental_detail_id);
		return "/refund";
	}
	
	*/
}
