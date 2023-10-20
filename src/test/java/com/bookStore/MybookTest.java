package com.bookStore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.librarycos.entity.Book;
import com.librarycos.entity.Customers;
import com.librarycos.entity.MyBook;
import com.librarycos.repository.CartItemepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class MybookTest {
	
	@Autowired
	private CartItemepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testSaveitem() {
		Integer customerId = 1;
		Integer bookId = 5;
		//หาข้อมูลใน Entity 
		Customers customer = entityManager.find(Customers.class, customerId);
		Book book = entityManager.find(Book.class, bookId);
		
		MyBook newBook = new MyBook();
		newBook.setCustomer(customer);
		newBook.setBook(book);
		newBook.setQuantity(1);
		
		MyBook savedItem = repo.save(newBook);
		assertThat(savedItem.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testfindCustomer() {
		Integer customerId = 1 ;
		List<MyBook> listItems = repo.findByCustomer(new Customers(customerId));
		listItems.forEach(System.out::println);
		assertThat(listItems.size()).isEqualTo(2);
	}
	
	@Test
	public void testUpdateQuantity() {
		Integer customerId = 1;
		Integer bookId = 5;
		Integer quantity = 5;
		repo.updateQuantity(quantity, customerId, bookId);
		//MyBook item = repo.findByCustomerAndBook(new Customers(bookId), new Book(bookId));
		//assertThat(item.getQuantity()),isEqualTo(4);
	}
	
	public void deleteByCustomerAndBook() {
		Integer customerId = 1;
		Integer bookId = 6;
		repo.deleteByCustomerAndBook(customerId, bookId);
		MyBook item = repo.findByCustomerAndBook(new Customers(customerId),new Book (bookId));
		assertThat(item).isNull();
	}
	
}
