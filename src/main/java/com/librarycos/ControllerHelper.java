package com.librarycos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.librarycos.entity.User;
import com.librarycos.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ControllerHelper {
	@Autowired private UserService userService;
	
	public User getAuthenticatedUser(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedUser(request);	
		return userService.getByEmail(email);
	}
}
