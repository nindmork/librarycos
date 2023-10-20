package com.bookStore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rental_details")
public class RentalDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Rental order;
	
	

	public RentalDetail() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Rental getOrder() {
		return order;
	}

	public void setOrder(Rental newOrder) {
		this.order = newOrder;
	}
	
	
}
