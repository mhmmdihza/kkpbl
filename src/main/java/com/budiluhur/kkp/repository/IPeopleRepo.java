package com.budiluhur.kkp.repository;

import org.springframework.data.repository.CrudRepository;

import com.budiluhur.kkp.entity.people;


public interface IPeopleRepo extends CrudRepository<people, String> {
	
}