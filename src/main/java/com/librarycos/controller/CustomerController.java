package com.librarycos.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.librarycos.ControllerHelper;
import com.librarycos.entity.Customers;
import com.librarycos.entity.User;
import com.librarycos.service.CustomerService;


import jakarta.servlet.http.HttpServletRequest;


@Controller
public class CustomerController {

	@Autowired private CustomerService customerService;
	@Autowired private ControllerHelper controllerHelper;
	@Autowired
	private CustomerService customersservice;
	
	
	
	@GetMapping("/customer/new")
	public String newCustomer(Model model) {
		Customers customer = new Customers();
		model.addAttribute("customer",customer);
		model.addAttribute("pageTitle","ลงทะเบียนผู้ยืม");
		return "customer_form";
	}
	
	
	
	@GetMapping("/customers/edit/{id}")
	public String editCustomers(@PathVariable(name ="id") Integer id,Model model) {	
		Customers customer = customersservice.getById(id);
		model.addAttribute("customer",customer);
		model.addAttribute("pageTitle","Edit Customer");
		return "customer_form";
	}
	

	@PostMapping("/customer/save")
	public String saveCustomer(Customers customer,HttpServletRequest request, RedirectAttributes redirectAttributes
			) throws IOException {
				redirectAttributes.addFlashAttribute("message", "The customer has been saved sucessfully.");
		User user = controllerHelper.getAuthenticatedUser(request);
		customersservice.save(customer, user);
		return "redirect:/customers";
	}
	
	@GetMapping("/customers")
	public String listCustomers(Model model) {
		return listByPage(1,model,"firstname","asc", null);
	}		
	
	@GetMapping("/customer/delete/{id}")
	public String deleteUser(@PathVariable(name ="id") Integer id ,Model model) {
		customerService.delete(id);
		
		return "redirect:/customers";
	}
	
	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword) {
		Page<Customers> page = customersservice.listByPage(pageNum, sortField, sortDir, keyword);
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
		return "customers";
	}
	
	

}
