package com.librarycos;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeTest {
	
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "Admin123";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		System.out.println(encodedPassword);
	}

}
