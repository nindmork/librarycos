package com.librarycos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String isbn;
	private String name;
	private String author;
	private int dayofrents;
	
	
	
	public Book() {
	}
	
	

	public Book(int id) {

		this.id = id;
	}



	public Book(int id, String isbn, String name, String author, int dayofrents, User user) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.dayofrents = dayofrents;
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getDayofrents() {
		return dayofrents;
	}

	public void setDayofrents(int dayofrents) {
		this.dayofrents = dayofrents;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
