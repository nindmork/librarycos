package com.librarycos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;



import com.librarycos.entity.Customers;





public interface CustomerRepository extends CrudRepository<Customers, Integer> ,
JpaRepository<Customers,Integer>{

	@Query("SELECT u FROM Customers u WHERE u.email = :email")
	public Customers getCustomersByEmail(@Param("email") String email);
	
	
	@Query("SELECT u FROM Customers u WHERE CONCAT (u.id,' ',u.email,' ',firstname,' ',citynumber,' ',phonenumber) LIKE %?1%")
	public Page<Customers> findAll(String keyword, Pageable pageable);
	
	
	@Query(value = "SELECT cus.firstname, u.first_name FROM customers cus "
			+ "JOIN users u ON cus.user_id = u.id "
			+ "WHERE cus.id IN (SELECT re.customer_id FROM record re)"
			, nativeQuery = true)
	public List<Object[]> findCusAndUserHasRental();
	
	@Query(value = "SELECT cus.firstname, u.first_name FROM customers cus "
			+ "JOIN users u ON cus.user_id = u.id "
			+ "WHERE cus.id NOT IN (SELECT re.customer_id FROM record re)"
			, nativeQuery = true)
	public List<Object[]> findCusAndUserNotHasRental();
	
}


