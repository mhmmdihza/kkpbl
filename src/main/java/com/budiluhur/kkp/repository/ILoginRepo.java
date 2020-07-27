package com.budiluhur.kkp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.budiluhur.kkp.entity.login;


public interface ILoginRepo extends CrudRepository<login, Long> {
	@Query("SELECT u FROM login u WHERE u.nrk = ?1 and u.password = ?2")
	login findUserByNrkAndPassword(String nrk, String password);
	
}