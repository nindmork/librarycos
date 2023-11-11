package com.librarycos;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.librarycos.entity.Customers;
import com.librarycos.entity.Role;
import com.librarycos.entity.User;
import com.librarycos.repository.CustomerRepository;
import com.librarycos.repository.RoleRepository;
import com.librarycos.repository.UserRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	
	private User user;
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private CustomerRepository crepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userMork = new User("Staff@gmail.com","Staff123","Staff", "Staff123");
		User savedUser = repo.save(userMork);
		assertThat(savedUser.getId()).isGreaterThan(0);
		System.out.println("test");
	}
	
	@Test
	public void testCreateUserWithTwoRole() {
		User userMild = new User("Mai@gmail.com","12345","Mild", "Mild T");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userMild.addRole(roleEditor);
		userMild.addRole(roleAssistant);
		User savedUser = repo.save(userMild);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userMork = repo.findById(12).get();
		System.out.println(userMork);
		assertThat(userMork).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userMork = repo.findById(12).get();
		userMork.setEnabled(true);
		repo.save(userMork);		
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userMild = repo.findById(13).get();
		Role roleEditor = new Role(3);
		Role roleSaleperson = new Role(2);
		userMild.getRoles().remove(roleEditor);
		userMild.addRole(roleSaleperson);
		repo.save(userMild);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 13;
		repo.deleteById(userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "Mai@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testfindAllUserRole() {
		Set<Role> roles = user.getRoles();
		
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		
		for (Role role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getName()));
		}
		
	}
	
	
	/*	@Test
	public void rentalfindBycus(){
		int customerId = 2;
		Rental rental = rentalService.findBycus(customerId);
	int id = rental.getId();
		System.out.println(id);
		Customers customer = customerService.getById(customerId);
		Rental rental = rentalService.listRental(customer);
		RentalDetail rentalDetail = rentalService.listRentalDetail(rental);
		List<Book> list = bookService.listBook(rentalDetail);
		
		return new ModelAndView("MyBook2","book",list);
		
	}*/
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 19;
		repo.updateEnabledStatus(id, false);
		
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 19;
		repo.updateEnabledStatus(id, true);
		
	}	
	
/*	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}*/
	
	@Test
	public void testSearchUsers() {
		String keyword = "Admin";
	
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));	
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
	
	@Test
	public void TestListFirstPage() {
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Customers> page = crepo.findAll(pageable);
		
		List<Customers> listCustomers = page.getContent();
		
		listCustomers.forEach(user -> System.out.println(user));	
		
		assertThat(listCustomers.size()).isGreaterThan(pageSize);
	}
}
