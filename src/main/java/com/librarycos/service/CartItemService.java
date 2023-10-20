package com.bookStore.service;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.entity.Book;
import com.bookStore.entity.Customers;
import com.bookStore.entity.MyBook;

import com.bookStore.repository.CartItemepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartItemService {
	
	@Autowired
	private CartItemepository cartitemRepo;
	
	@Autowired
	private RentalService rService;
	
	public void saveMyBooks(MyBook book) {
		cartitemRepo.save(book);
	}
	
	public Integer addBook(Integer bookId,Integer quantity, Customers customer)
		throws CartException{
		Integer updateQuantity = quantity;
		Book book = new Book(bookId);
		MyBook bookItem = cartitemRepo.findByCustomerAndBook(customer, book);
		if(bookItem != null) {
			updateQuantity = bookItem.getQuantity() + quantity;	
			if (updateQuantity > 5) {
				throw new CartException("ไม่สามารถเพิ่มอีกเนื่องจากตอนนี้มีจำนวนทั้งหมด " + bookItem.getQuantity() + "เล่ม"
						+ "ซึ่งเกิดจากจำนวนสูงสุด");			
			}
		}
		else {
			bookItem = new MyBook();
			bookItem.setCustomer(customer);
			bookItem.setBook(book);
		}
		bookItem.setQuantity(updateQuantity);
		bookItem.setRentaEndtime(rService.calculateRentalEndTime(new Date(),5)); 
		cartitemRepo.save(bookItem);
		return updateQuantity;
		
	}
	
	public List<MyBook> listCartItems(Customers customer){
		return cartitemRepo.findByCustomer(customer);
	}
	
	
	public void deleteById(int id) {
		cartitemRepo.deleteById(id);
	}
	
	public void removeBook(Integer bookId,Integer customerId) {
		System.out.println(bookId);
		System.out.println(customerId);
		cartitemRepo.deleteByCustomerAndBook(customerId,bookId);
	}
	
	public void deleteByCustomer(Customers customer) {
		cartitemRepo.deleteByCustomer(customer.getId());
	}
	
}
