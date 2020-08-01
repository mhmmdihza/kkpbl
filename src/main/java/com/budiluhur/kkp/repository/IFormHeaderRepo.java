package com.budiluhur.kkp.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.budiluhur.kkp.entity.formHeader;


@Transactional
public interface IFormHeaderRepo extends CrudRepository<formHeader, Long> {
	
	@Query(value = "SELECT * FROM form_header INNER JOIN people ON form_header.people=people.nrk where form_header.people =:nrk",nativeQuery = true)
	List<formHeader> findFormByPeopleNrk(@Param("nrk") String nrk);
	
	@Query(value = "SELECT * FROM form_header INNER JOIN sektor ON form_header.sektor=sektor.id where sektor.id =:sektor",nativeQuery = true)
	List<formHeader> findFormBySektor(@Param("sektor") Long sektor);
	
}