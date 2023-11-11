package com.librarycos.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "record")
public class Rental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date rentalTime;
	private Date rentaEndtime;
	
	@Enumerated(EnumType.STRING)
	private RentalStatus status;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "user_rentid")
	private User userrent;

	@ManyToOne
	@JoinColumn(name = "user_refundid")
	private User userrefund;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customers customer;
	
	
	
	public Rental() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rental(Integer id, Date rentalTime, Date rentaEndtime, RentalStatus status, Book book, User userrent,
			User userrefund, Customers customer) {
		super();
		this.id = id;
		this.rentalTime = rentalTime;
		this.rentaEndtime = rentaEndtime;
		this.status = status;
		this.book = book;
		this.userrent = userrent;
		this.userrefund = userrefund;
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRentalTime() {
		return rentalTime;
	}

	public void setRentalTime(Date rentalTime) {
		this.rentalTime = rentalTime;
	}

	public Date getRentaEndtime() {
		return rentaEndtime;
	}

	public void setRentaEndtime(Date rentaEndtime) {
		this.rentaEndtime = rentaEndtime;
	}

	public RentalStatus getStatus() {
		return status;
	}

	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUserrent() {
		return userrent;
	}

	public void setUserrent(User userrent) {
		this.userrent = userrent;
	}

	public User getUserrefund() {
		return userrefund;
	}

	public void setUserrefund(User userrefund) {
		this.userrefund = userrefund;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Rental [id=" + id + ", rentalTime=" + rentalTime + ", rentaEndtime=" + rentaEndtime + ", status="
				+ status + ", book=" + book + ", userrent=" + userrent + ", userrefund=" + userrefund + ", customer="
				+ customer + "]";
	}
	
}
	
	
	
