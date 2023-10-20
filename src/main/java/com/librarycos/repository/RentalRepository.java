package com.bookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import com.bookStore.entity.Customers;

import com.bookStore.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental,Integer> , CrudRepository<Rental,Integer>{

	
	/*@Query("SELECT Rental FROM Rental r WHERE r.customer = ?1")
	public Rental findBycus(int id);*/
	
	//public List<MyBook> findByCustomer(Customers customer);
	//public MyBook findByCustomerAndBook(Customers customer, Book book);
	
	public List<Rental> findByCustomer(Customers customer);
	
	@Query(value = "SELECT id FROM rentals WHERE customer_id = ?1", nativeQuery = true)
	public List<Rental> getIDByCustomerId(int CustomerId);
	
	@Query("SELECT u FROM Customers u WHERE u.email = :email")
	public Customers getUserByEmail(@Param("email") String email);
	
}
