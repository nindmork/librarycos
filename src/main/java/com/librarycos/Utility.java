package com.librarycos;



import jakarta.servlet.http.HttpServletRequest;

public class Utility {
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		
		return siteURL.replace(request.getServletPath(), "");
	}
	
	public static String getEmailOfAuthenticatedUser(HttpServletRequest request) {
		Object principal = request.getUserPrincipal();
		if (principal == null) return null;
		
		String userEmail = null;
		userEmail = request.getUserPrincipal().getName();
		return userEmail;
	}	
}
