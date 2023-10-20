package com.bookStore.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.entity.Book;
import com.bookStore.entity.Customers;
import com.bookStore.entity.MyBook;

@Repository
public interface CartItemepository extends CrudRepository<MyBook,Integer>{
	
	//JpaRepository<MyBook,Integer>
	public List<MyBook> findByCustomer(Customers customer);
	
	//public MyBook findByCustomer(Customers customer);
	
	public MyBook findByCustomerAndBook(Customers customer, Book book);
	
	

	@Modifying
	@Query("UPDATE MyBook c SET c.quantity = ?1 WHERE c.customer.id = ?2 AND c.book.id = ?3")	
	public void updateQuantity(Integer quantity, Integer customerId, Integer bookId);

	@Modifying
	@Query("DELETE FROM MyBook c WHERE c.customer.id = ?1 AND c.book.id = ?2")
	public void deleteByCustomerAndBook(Integer customerId, Integer bookId);

	@Modifying
	@Query("DELETE MyBook c WHERE c.customer.id = ?1")
	public void deleteByCustomer(Integer customerId);
}

