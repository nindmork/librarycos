package com.bookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.entity.Customers;

import com.bookStore.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	public Customers getByEmail (String email) {
		return customerRepo.getUserByEmail(email);
	}
	public Customers getById(Integer id) {		
		return customerRepo.getReferenceById(id);
	}
	
	public List<Customers> getAllcustomers(){
		return  customerRepo.findAll();
	}
	
	public Customers save(Customers customer) {
		return customerRepo.save(customer);
	}
}
