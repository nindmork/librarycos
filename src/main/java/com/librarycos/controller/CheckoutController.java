package com.librarycos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.librarycos.entity.Book;
import com.librarycos.entity.Customers;
import com.librarycos.entity.MyBook;
import com.librarycos.service.BookService;
import com.librarycos.service.CartItemService;
import com.librarycos.service.CustomerService;
import com.librarycos.service.RentalService;



@Controller
public class CheckoutController {

	@Autowired
	private CartItemService cartitemService;
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private RentalService rentalService;
	
	@Autowired
	private BookService bservice;
	
	@PostMapping("/place_order/{customerId}")
	public String placeOrder(@PathVariable("customerId")Integer customerId) {
		Customers customer = customerService.getById(customerId);	
		List<MyBook> cartItems = cartitemService.listCartItems(customer);
		for(MyBook cartItem : cartItems) {
			int cartQuantity = cartItem.getQuantity();
			Book book = cartItem.getBook();
			int stockbook = book.getQuantity();
			if (cartQuantity <= stockbook) {
				book.setQuantity(stockbook- cartQuantity);
				bservice.save(book);
				rentalService.createRental(customer, cartItems);
				cartitemService.deleteByCustomer(customer);
			}else {
				
			}
			
		}
		
		cartitemService.deleteByCustomer(customer);
		
		return null;
	}
	
	@GetMapping("/rental_completed")
	public String rental_completed() {	
		return "/rental_completed";
	}
}
