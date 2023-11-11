package com.librarycos.service;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarycos.entity.Book;
import com.librarycos.entity.BookStatus;
import com.librarycos.entity.Customers;

import com.librarycos.entity.Rental;

import com.librarycos.entity.RentalStatus;
import com.librarycos.entity.User;
import com.librarycos.repository.BookRepository;
import com.librarycos.repository.RentalRepository;

@Service
public class RentalService {
	
	
	@Autowired private RentalRepository repo;

	@Autowired private BookRepository bRepo;

	public Date calculateRentalEndTime(Date rentalStartTime, int rentalDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalStartTime);
        calendar.add(Calendar.DAY_OF_YEAR, rentalDays);
        return calendar.getTime();
    }
	
	public boolean checkRentalExpired(Date getRentaEndtime) throws ParseException{
		
		//สร้างวันที่ปลอมไว้เช็ค
		String dateString = "Sat Nov 18 17:16:24 ICT 2023";
		SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		Date fakedate = format.parse(dateString);
		//
		Date currentDate = new Date();
		boolean isDatePastEnd = currentDate.after(getRentaEndtime);
		//System.out.println("fakedate" + fakedate); 
		return isDatePastEnd;
	}
	
	public void createRental(Customers customer,Book book,User user) {
		Rental newOrder = new Rental();
		newOrder.setRentalTime(new Date());
		newOrder.setStatus(RentalStatus.กำลังยืม);
		book.setStatus(BookStatus.ถูกยืมแล้ว);
		newOrder.setRentaEndtime(calculateRentalEndTime(new Date(),book.getDayofrents()));
		newOrder.setBook(book);
		newOrder.setUserrent(user);
		newOrder.setUserrefund(null);
		newOrder.setCustomer(customer);	
		
		repo.save(newOrder);
	
	}
	

	
	public List<Rental> listRental(Customers customer){	
		return repo.findByCustomer(customer);

	}
	
	public void updateRentalExpired() throws ParseException{	
		
		List<Rental> rentallist = repo.findAll();
		for (Rental rental : rentallist) {
			Book book = rental.getBook();
			boolean isPastEnd = checkRentalExpired(rental.getRentaEndtime());
			//System.out.println(isPastEnd);
			if(rental.getStatus() != RentalStatus.คืนแล้ว) {
				if (isPastEnd ) {
					rental.setStatus(RentalStatus.เกินกำหนด);
					repo.save(rental);
					//System.out.println(rental.getStatus());
					book.setStatus(BookStatus.ถูกยืมแล้ว);
					bRepo.save(book);
				}else {
					rental.setStatus(RentalStatus.กำลังยืม);
					book.setStatus(BookStatus.ถูกยืมแล้ว);
					repo.save(rental);
					//System.out.println(rental.getStatus()); 
					bRepo.save(book);
				}
				
			}else {
				book.setStatus(BookStatus.พร้อมยืม);
				bRepo.save(book);
			}
		}
	}
	
	public Object[] expiredCalService(Rental rental) {
		Date pastTime = rental.getRentaEndtime();	
		Date currentTime = new Date();
		long timeDifferenceInMillis = pastTime.getTime() - currentTime.getTime(); 
		// แปลงเป็นวินาที
		long timeDifferenceInSeconds = timeDifferenceInMillis / 1000;
		// แปลงเป็นนาที
		long timeDifferenceInMinutes = timeDifferenceInSeconds / 60;
		// แปลงเป็นชั่วโมง
		long timeDifferenceInHours = timeDifferenceInMinutes / 60;
		// แปลงเป็นวัน
		long timeDifferenceInDays = timeDifferenceInHours / 24;
		long price = timeDifferenceInDays * 10;

		return new Object[]{timeDifferenceInDays, price};
		
		
	}
	public Rental findById(int id) {
		return repo.findById(id);
	}
	
	public void refund(Rental rental, User user) {
		if(rental.getStatus()!= RentalStatus.คืนแล้ว) {
			rental.setUserrefund(user);
			rental.setStatus(RentalStatus.คืนแล้ว);
			Book book = rental.getBook();
			book.setStatus(BookStatus.พร้อมยืม);
			repo.save(rental);
			bRepo.save(book);		
		}
	}
	
}
