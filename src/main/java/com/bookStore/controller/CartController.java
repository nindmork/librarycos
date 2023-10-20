package com.bookStore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ModelAndView getAllCustomer() {
		List<Customers> list = customerService.getAllcustomers();
		ModelAndView showCustomers = new ModelAndView();
		showCustomers.setViewName("findCustomer");
		showCustomers.addObject("customers",list);
		return showCustomers;
	}
	
	@GetMapping("/book_rental/{customerid}")
	public ModelAndView getAllBookrental(HttpServletResponse response,@PathVariable("customerid")String customerId) {
		/// create a cookie
	    Cookie cookie = new Cookie("customerid", customerId);
	    cookie.setPath("/cartview");
	    //add a cookie to the response
	    //cookie.setMaxAge(7);
	    response.addCookie(cookie);
		List<Book>list=bservice.getAllBook();	
		return new ModelAndView("bookrental","book",list);
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
