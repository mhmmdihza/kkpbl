package com.budiluhur.kkp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.kkp.entity.unitKendaraan;
import com.budiluhur.kkp.entity.formDetail;
import com.budiluhur.kkp.entity.formHeader;
import com.budiluhur.kkp.entity.people;
import com.budiluhur.kkp.entity.unitKendaraan;
import com.budiluhur.kkp.entity.unitKendaraan;
import com.budiluhur.kkp.repository.IUnitKendaraanRepo;
import com.budiluhur.kkp.repository.IFormDetailRepo;
import com.budiluhur.kkp.repository.IFormHeaderRepo;
import com.budiluhur.kkp.repository.IPeopleRepo;
import com.budiluhur.kkp.repository.IUnitKendaraanRepo;
import com.budiluhur.kkp.repository.IUnitKendaraanRepo;

import io.swagger.annotations.Api;

@RestController
@Api(value = "TES", tags = "TES")
public class tesController {
	

	@Value("${si_security_key:not_defined}")
	protected String securityKey;
	
	@Autowired
	IPeopleRepo pR;
	
	@Autowired
	IUnitKendaraanRepo sR;
	
	@Autowired
	IUnitKendaraanRepo ibr;
	
	@Autowired
	IFormHeaderRepo ifr;
	
	@Autowired
	IFormDetailRepo ifdr;
	
	@Autowired
	IUnitKendaraanRepo iur;
	
	@PersistenceContext
	private EntityManager em;
	
    public static final String PROFILEPICTPATH = "profilepictpath";
    public static final String INITIALSIGNPATH = "initialsignpath";
    public static final String FORMSIGNPATH = "formsignpath";
	
	@RequestMapping(method = RequestMethod.GET, value = "/tescontroller")
	@ResponseBody
	public String getProfilePict() {
		return "scs";

	}
}