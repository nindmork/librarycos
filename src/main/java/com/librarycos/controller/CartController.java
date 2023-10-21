package com.librarycos.controller;


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

import com.librarycos.entity.Book;
import com.librarycos.entity.Customers;

import com.librarycos.service.BookService;

import com.librarycos.service.CustomerService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CartController {
@Autowired private BookService bservice;

@Autowired private CustomerService customerService;

	
	
	
	/*@GetMapping("/cartview")
	public String testviewCart(Model model,@CookieValue(value = "customerid", defaultValue = "") String customerId) {	
		Integer customerIdi = Integer.valueOf(customerId);
		Customers customer = customerService.getById(customerIdi);	
		List<MyBook> bookitem = cartitemService.listCartItems(customer);
		
		model.addAttribute("cartview",bookitem);
		model.addAttribute("customer",customer);
		return "/cartview";
		
	}
	*/
}
