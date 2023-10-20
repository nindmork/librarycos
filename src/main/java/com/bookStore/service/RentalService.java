package com.bookStore.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.entity.Customers;
import com.bookStore.entity.MyBook;
import com.bookStore.entity.Book;
import com.bookStore.entity.Rental;
import com.bookStore.entity.RentalDetail;
import com.bookStore.entity.RentalStatus;

import com.bookStore.repository.RentalRepository;

@Service
public class RentalService {
	
	
	@Autowired private RentalRepository repo;

   public Date calculateRentalEndTime(Date rentalStartTime, int rentalDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalStartTime);
        calendar.add(Calendar.DAY_OF_YEAR, rentalDays);
        return calendar.getTime();
    }
   
	public Rental createRental(Customers customer,List<MyBook> cartItems ) {
		Rental newOrder = new Rental();
		newOrder.setRentalTime(new Date());
		newOrder.setStatus(RentalStatus.NEW);
		newOrder.setCustomer(customer);
		newOrder.setFirstname(customer.getFirstname());
		newOrder.setLastname(customer.getLastname());
		newOrder.setCitynumber(customer.getCitynumber());
		newOrder.setEmail(customer.getEmail());
		newOrder.setPhonenumber(customer.getPhonenumber());
		newOrder.setRentaEndtime(calculateRentalEndTime(new Date(),5));
		newOrder.setRentalDays(5);
		
		Set<RentalDetail> rentalDetails = newOrder.getOrderDetails();
		
		for (MyBook cartItem : cartItems) {
			Book book = cartItem.getBook();
			RentalDetail rentalDetail = new RentalDetail();
			rentalDetail.setOrder(newOrder);
			rentalDetail.setBook(book);
			rentalDetail.setQuantity(cartItem.getQuantity());
			rentalDetails.add(rentalDetail);
		}
		
		return repo.save(newOrder);
	}
	

	
	public List<Rental> listRental(Customers customer){	
		return repo.findByCustomer(customer);

	}
/*	
	
	public List<RentalDetail> listRentalDetail(Rental rental) {
		return repoD.findByOrder(rental);
	}*/
}
