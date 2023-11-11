package com.librarycos.entity;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
	@Column(name = "firstname", length = 45, nullable = false)
	private String firstname;
	
	@Column(name = "lastname", length = 45, nullable = false)
	private String lastname;
	
	@Column(name = "citynumber", length = 256, nullable = false)
	private String citynumber;
	
	@Column(name = "phonenumber", length = 45, nullable = false)
	private String phonenumber;
	
	private Date risgisDate;

	//@ManyToOne(cascade = CascadeType.ALL) ต้องไม่ใส่ถ้าใสจะลบ user ได้และค่่่่าเป็น null
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Customers() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Customers(Integer id, String email, String firstname, String lastname, String citynumber, String phonenumber,
			Date risgisDate, User user) {
		super();
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.citynumber = citynumber;
		this.phonenumber = phonenumber;
		this.risgisDate = risgisDate;
		this.user = user;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public String getcusFirstname() {
		String cusFirstname = firstname;
		return cusFirstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCitynumber() {
		return citynumber;
	}

	public void setCitynumber(String citynumber) {
		this.citynumber = citynumber;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Date getRisgisDate() {
		return risgisDate;
	}

	public void setRisgisDate(Date risgisDate) {
		this.risgisDate = risgisDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}


