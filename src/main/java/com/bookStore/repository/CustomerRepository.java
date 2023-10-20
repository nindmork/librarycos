package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.bookStore.entity.Customers;


@Repository
public interface CustomerRepository extends CrudRepository<Customers, Integer> ,
JpaRepository<Customers,Integer>{

	@Query("SELECT u FROM Customers u WHERE u.email = :email")
	public Customers getUserByEmail(@Param("email") String email);
	
	
}
