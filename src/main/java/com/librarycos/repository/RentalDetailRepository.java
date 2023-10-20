package com.librarycos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.librarycos.entity.Rental;
import com.librarycos.entity.RentalDetail;

public interface RentalDetailRepository extends JpaRepository<RentalDetail,Integer>{

	public List<RentalDetail> findByOrder(Rental rental);
	
	public List<RentalDetail> findById(int rdId);
	/*@Query(value = "SELECT book_id FROM rental_details WHERE order_id = ?1", nativeQuery = true)
	public List<RentalDetail> getBookIDByRentalId(int rentalId);
	*/
}
