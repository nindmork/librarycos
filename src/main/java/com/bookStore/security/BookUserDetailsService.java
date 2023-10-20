package com.bookStore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bookStore.entity.User;
import com.bookStore.repository.UserRepository;


public class BookUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepo;
	
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
	
	/*@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new BookUserDetails(user);
		}
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}*/
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		System.out.println(email);
		if (user != null) {
			return new BookUserDetails(user);
		}
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}
}
