package com.librarycos;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.annotation.Rollback;

import com.librarycos.entity.BookAndPrice;
import com.librarycos.entity.Rental;
import com.librarycos.entity.RentalStatus;

import com.librarycos.repository.BookRepository;
import com.librarycos.repository.BookRepository.BookAndPrice2;
import com.librarycos.repository.BookRepository.BookCount;
import com.librarycos.repository.BookRepository.BookCus;
import com.librarycos.repository.CustomerRepository;
import com.librarycos.repository.RentalRepository;
import com.librarycos.service.RentalService;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class Testquery {
	

	@Autowired
	private BookRepository bookRepo;
	
	

	@Autowired
	private CustomerRepository cusRepo;
	
	
	@Test
	public void findBookCountByStaff() {
		List<BookCount> BookCountlist = bookRepo.findBookCountByStaff();
		for (BookCount bookCount : BookCountlist) {
			
			System.out.println(bookCount.getName());
		}
	}
	
	@Test
	public void findBookAndCustomerWithStatus(){
		List<BookCus> BookCuslist = bookRepo.findBookAndCustomerWithStatus();
		for (BookCus bookCus : BookCuslist) {
			System.out.println(bookCus.getName());
			System.out.println(bookCus.getFirstname());
		}
	}
	
	@Test
	public void report4(){
		List<Object[]> cususerlist = cusRepo.findCusAndUserHasRental();
		//คนที่ยืม
		for (Object[] cusUser: cususerlist) {
			String firstname = (String) cusUser[0];
            String first_name = (String) cusUser[1];
			System.out.println(firstname);
			System.out.println(first_name);
		}
		//คนทีไม่ได้่ยืม
		List<Object[]> cususerlist2 =cusRepo.findCusAndUserNotHasRental();
		for (Object[] cusUser2: cususerlist2) {
			String firstname = (String) cusUser2[0];
            String first_name = (String) cusUser2[1];
			System.out.println(firstname);
			System.out.println(first_name);
		}
	}
	
	@Test
	public void report1() {
		
		List<BookAndPrice2> BookandP = bookRepo.findBookWithPrice();
		for (BookAndPrice2 aBookAndPrice : BookandP) {
			System.out.println(aBookAndPrice.getBookName());
			System.out.println(aBookAndPrice.getPrice());
		}
	}

}
