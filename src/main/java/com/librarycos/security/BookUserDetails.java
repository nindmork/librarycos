package com.librarycos.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.librarycos.entity.Role;
import com.librarycos.entity.User;
import com.librarycos.repository.RentalRepository;
import com.librarycos.repository.UserRepository;
import com.librarycos.service.UserService;



public class BookUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private User user;
	
	@Autowired private UserRepository userRepository;
	//@Autowired private RoleRepository repoRepository;

	public BookUserDetails(User user) {
		this.user = user;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println(user.getRoles().getName());
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		authories.add(new SimpleGrantedAuthority(user.getRoles().getName()));
		return authories;
	}
	
	/*
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		System.out.println(user.getRoles());
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		
		for (Role role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getName()));
			
		}
		
		return authories;
	}*/

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled();
	}
	

	public String getFullname() {
		return this.user.getFirstName() + " " + this.user.getLastName();
	}
	
	public void setFirstName(String firstName) {
		this.user.setFirstName(firstName);
	}

	public void setLastName(String lastName) {
		this.user.setLastName(lastName);
	}	
	

}
