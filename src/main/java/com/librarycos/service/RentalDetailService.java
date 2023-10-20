package com.librarycos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarycos.entity.Rental;
import com.librarycos.entity.RentalDetail;
import com.librarycos.repository.RentalDetailRepository;
@Service
public class RentalDetailService {
	
	@Autowired private RentalDetailRepository repoD;
	
	public List<RentalDetail> listRentalDetail(Rental rental){
		return repoD.findByOrder(rental);
	}
	
	public List<RentalDetail> listRentalDetailById(int rgId){
		return repoD.findById(rgId);
	}
	
	public void deleteById(int id) {
		repoD.deleteById(id);
	}
	
}
