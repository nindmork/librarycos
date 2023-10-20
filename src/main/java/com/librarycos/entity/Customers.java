package com.librarycos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	public Customers() {
		super();
	}

	public Customers(Integer id) {
		this.id = id;
	}

	public Customers(String email, String firstname, String lastname, String citynumber, String phonenumber) {
		super();
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.citynumber = citynumber;
		this.phonenumber = phonenumber;
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

}
