package com.bookStore.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookStore.entity.Customers;

import com.bookStore.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired private CustomerService customerService;
	
	@Autowired
	private CustomerService customersservice;
	
	
	
	@GetMapping("/customer/new")
	public String newCustomer(Model model) {
		Customers customer = new Customers();
		model.addAttribute("customer",customer);
		model.addAttribute("pageTitle","Create New User");
		return "customer_form";
	}
	
	@PostMapping("/customer/save")
	public String saveCustomer(Customers customer, RedirectAttributes redirectAttributes
			) throws IOException {

		redirectAttributes.addFlashAttribute("message", "The customer has been saved sucessfully.");

		customersservice.save(customer);
		return "redirect:/customers";
	}
	
	@GetMapping("/customers")
	public ModelAndView listCustomers() {
		List<Customers> list = customerService.getAllcustomers();
		ModelAndView showCustomers = new ModelAndView();
		showCustomers.setViewName("customers");
		showCustomers.addObject("customers",list);
		
		return showCustomers;
	}
	

}
