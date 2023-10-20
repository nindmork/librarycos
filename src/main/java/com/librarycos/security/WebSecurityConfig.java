package com.librarycos.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class WebSecurityConfig {

	@Bean
	public UserDetailsService UserDetailsService() {
		return new BookUserDetailsService();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	 public DaoAuthenticationProvider authenticationProvider() {
	    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    	authProvider.setUserDetailsService(UserDetailsService());
	    	authProvider.setPasswordEncoder(passwordEncoder());
	    	return authProvider;
	    }

	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	  http.authorizeHttpRequests(auth ->auth		
	    			  .requestMatchers("/users/**").hasAnyAuthority("Admin")
	    			  .anyRequest().authenticated())
	    	  .formLogin(formLogin -> formLogin.loginPage("/login")
	    			  .usernameParameter("email")
	    	  			.defaultSuccessUrl("/", true)
	    	  			.permitAll())	   
	    	  .logout((logout) -> logout.logoutUrl("/logout").permitAll())
	    	  .rememberMe(rememberMe -> rememberMe.key("dfsafhfjhlkjdsjfkdasjf_12313uk,23898")// specify your secret key
	                  .tokenValiditySeconds(7*24*60*60)  // specify token validity time in seconds
	                  .userDetailsService(UserDetailsService()) // specify your UserDetailsService here
	                  
	          );
	    	
	        return http.build();
	    }
	 
	

}
