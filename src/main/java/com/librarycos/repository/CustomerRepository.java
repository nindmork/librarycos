package com.librarycos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.librarycos.entity.Customers;
import com.librarycos.entity.User;


@Repository
public interface CustomerRepository extends CrudRepository<Customers, Integer> ,
JpaRepository<Customers,Integer>{

	@Query("SELECT u FROM Customers u WHERE u.email = :email")
	public Customers getUserByEmail(@Param("email") String email);
	
	
	
	@Query("SELECT u FROM Customers u WHERE CONCAT (u.id,' ',u.email,' ',firstname,' ',citynumber,' ',phonenumber) LIKE %?1%")
	public Page<Customers> findAll(String keyword, Pageable pageable);
}
