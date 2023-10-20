package com.bookStore.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name="cartitem")
public class MyBook {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customers customer;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	
	private Date rentaEndtime;
	
	public MyBook() {	
		super();
	}
	


	public Date getRentaEndtime() {
		return rentaEndtime;
	}

	public void setRentaEndtime(Date rentaEndtime) {
		this.rentaEndtime = rentaEndtime;
	}

	public MyBook(int id) {
		this.id = id;
	}

	public MyBook(int id, int quantity, Customers customer, Book book, Date rentaEndtime) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.customer = customer;
		this.book = book;
		this.rentaEndtime = rentaEndtime;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int i) {
		this.quantity = i;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}


}
