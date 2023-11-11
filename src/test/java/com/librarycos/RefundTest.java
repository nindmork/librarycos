package com.librarycos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.librarycos.service.RentalDetailService;
import com.librarycos.service.RentalService;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)			
@Rollback(false)
public class RefundTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired private RentalDetailService rentalDService;
	
	@Test
	public void deleteOrder() {
		int rental_detail_id = 1;
		rentalDService.deleteById(rental_detail_id);
	}
}
