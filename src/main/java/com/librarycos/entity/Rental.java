package com.librarycos.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "rentals")
public class Rental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128, nullable = true, unique = false)
	private String email;
	
	@Column(name = "firstname", length = 45, nullable = true)
	private String firstname;
	
	@Column(name = "lastname", length = 45, nullable = true)
	private String lastname;
	
	@Column(name = "citynumber", length = 256, nullable = true)
	private String citynumber;
	
	@Column(name = "phonenumber", length = 45, nullable = true)
	private String phonenumber;
	
	private Date rentalTime;
	
	private Date rentaEndtime;
	
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customers customer;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)	
	private Set<RentalDetail> orderDetails = new HashSet<>();

	private int rentalDays;
	
	@Enumerated(EnumType.STRING)
	private RentalStatus status;
	
	
	public Date getRentaEndtime() {
		return rentaEndtime;
	}

	public void setRentaEndtime(Date rentaEndtime) {
		this.rentaEndtime = rentaEndtime;
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

	public void setCitynumber(String string) {
		this.citynumber = string;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public Date getRentalTime() {
		return rentalTime;
	}

	public void setRentalTime(Date rentalTime) {
		this.rentalTime = rentalTime;
	}

	public void setPhonenumber(String string) {
		this.phonenumber = string;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public Set<RentalDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<RentalDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public int getRentalDays() {
		return rentalDays;
	}

	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}

	public RentalStatus getStatus() {
		return status;
	}

	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", rentalTime=" + rentalTime + ", customer=" + customer.getFirstname()+customer.getLastname() + "]";
	}
	
}
	
	
	
