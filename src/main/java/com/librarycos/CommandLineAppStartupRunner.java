package com.librarycos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.librarycos.service.RentalService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

	@Autowired private RentalService rentalervice;
	
    @Override
    public void run(String...args) throws Exception {
    	rentalervice.updateRentalExpired();

    }
    
    
    
}