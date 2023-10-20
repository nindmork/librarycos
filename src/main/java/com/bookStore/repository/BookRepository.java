package com.bookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.bookStore.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer>  {

	/*public List<Book> findByRentalDetail(RentalDetail rentalDetail);
	
	SELECT * FROM book where id = (
			SELECT book_id FROM book.rental_details where order_id = (
			SELECT id FROM book.rentals where customer_id = 1)); */
	
	
/*	@Query("DELETE FROM MyBook c WHERE c.customer.id = ?1 AND c.book.id = ?2")
	public void deleteByCustomerAndBook(Integer customerId, Integer bookId);*/
	
	//@Query(value = "SELECT * FROM my_entity WHERE id IN (SELECT id FROM other_table WHERE condition = :condition)", nativeQuery = true)
	@Query(value = "SELECT * FROM book WHERE id = ?1", nativeQuery = true)
	public List<Book> getBookID(int id);
	
	

	
}
