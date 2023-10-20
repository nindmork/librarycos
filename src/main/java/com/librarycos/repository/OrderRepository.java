package com.librarycos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.librarycos.entity.Rental;

public interface OrderRepository extends CrudRepository<Rental,Integer>,PagingAndSortingRepository<Rental, Integer> {

}
