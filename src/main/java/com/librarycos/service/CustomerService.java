package com.bookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bookStore.entity.Customers;
import com.bookStore.entity.User;
import com.bookStore.repository.CustomerRepository;

@Service
public class CustomerService {

	public static final int CUSTOMERS_PER_PAGE = 10;
	
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
	
	public void delete(Integer id) {		
		customerRepo.deleteById(id);
	}
	
	public Page<Customers> listByPage(int pageNum, String sortField, String sortDir, String keyword ){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMERS_PER_PAGE,sort);
		if (keyword != null) {
			return customerRepo.findAll(keyword, pageable);
		}
		return customerRepo.findAll(pageable);
	}
	
}
