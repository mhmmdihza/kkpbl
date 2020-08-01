package com.budiluhur.kkp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.budiluhur.kkp.entity.login;


public interface ILoginRepo extends CrudRepository<login, Long> {
	@Query("SELECT u FROM login u WHERE u.nrk = ?1 and u.password = ?2")
	login findUserByNrkAndPassword(String nrk, String password);
	
	@Query(value ="SELECT * FROM login INNER JOIN people on login.nrk = people.nrk WHERE  login.nrk = :nrk and people.jabatan = :jabatan",nativeQuery = true)
	login findUserByNrkPasswordAndJabatan(@Param("nrk") String nrk,@Param("jabatan") String jabatan);
	
}