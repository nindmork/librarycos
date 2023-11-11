package com.librarycos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.librarycos.entity.Book;
import com.librarycos.entity.Customers;
import com.librarycos.entity.MyBook;
import com.librarycos.entity.Rental;
import com.librarycos.entity.RentalDetail;
import com.librarycos.entity.RentalStatus;
import com.librarycos.repository.OrderRepository;
import com.librarycos.service.CartItemService;
import com.librarycos.service.CustomerService;
import com.librarycos.service.RentalDetailService;
import com.librarycos.service.RentalService;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)	
@Rollback(false)
public class OrderRepositoryTest {

	@Autowired private OrderRepository repo;
	@Autowired private TestEntityManager entityManager;
	@Autowired private CustomerService customerService;
	@Autowired private CartItemService cartitemService;
	@Autowired private RentalService rentalService;
	@Autowired private RentalDetailService rentalDService;
	
		@Test
		public void testCreateNewOrderWithSigleBook() {
			
			Customers customer = entityManager.find(Customers.class, 1);
			Book book = entityManager.find(Book.class, 1);
		
			Rental mainOrder = new Rental();
			mainOrder.setRentalTime(new Date());
			mainOrder.setCustomer(customer);
			mainOrder.setFirstname(customer.getFirstname());
			mainOrder.setLastname(customer.getLastname());
			mainOrder.setCitynumber(customer.getCitynumber());
			mainOrder.setEmail(customer.getEmail());
			mainOrder.setPhonenumber(customer.getPhonenumber());
			mainOrder.setStatus(RentalStatus.NEW);
			mainOrder.setRentalDays(5);
			
			
			RentalDetail orderdetail = new RentalDetail();
			orderdetail.setBook(book);
			orderdetail.setOrder(mainOrder);
			orderdetail.setQuantity(1);
			
			mainOrder.getOrderDetails().add(orderdetail);
			Rental savedOrder =repo.save(mainOrder);
			assertThat(savedOrder.getId()).isGreaterThan(0);
		}
		
		@Test
		public void testListOrder() {
			Iterable<Rental> orders = repo.findAll();
			assertThat(orders).hasSizeGreaterThan(0);
			orders.forEach(System.out::println);
			
		}
		
		@Test
		public void testUpdateOrder() {
			int orderId = 1 ;
			Rental order = repo.findById(orderId).get();
			
			order.setRentalTime(new Date());;
			
			Rental updateOrder = repo.save(order);
			//assertThat(updateOrder.get)
		}
		
		@Test
		public void testGetOrder() {
			int orderId = 1;
			Rental order = repo.findById(orderId).get();
			System.out.println(order);
		}
		
		@Test
		public void testDeleteOrder() {
			int orderId = 1;
			repo.deleteById(orderId);
			
			
		}
		
		@Test
		public void placeOrder() {
			/*int customerId = 2;
			Customers customer = customerService.getById(customerId);	
			List<MyBook> cartItems = cartitemService.listCartItems(customer);
			
			rentalService.createRental(customer, cartItems);
			cartitemService.deleteByCustomer(customer);*/
			
			//return "redirect:/rental_completed";
		}
		
		
}
