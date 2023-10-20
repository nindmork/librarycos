package com.bookStore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;


import com.bookStore.entity.RentalDetail;
import com.bookStore.entity.Book;
import com.bookStore.entity.Customers;
import com.bookStore.service.CartException;
import com.bookStore.service.CustomerService;
import com.bookStore.service.RentalDetailService;

import jakarta.servlet.http.HttpServletResponse;

import com.bookStore.service.CartItemService;



@RestController
public class CartItemRestController {
	
	@Autowired
	private CartItemService cartitemService;
	@Autowired
	private CustomerService customerService;
	@Autowired private RentalDetailService rentalDService;
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
	
	
}
