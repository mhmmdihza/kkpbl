package com.budiluhur.kkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.kkp.entity.login;
import com.budiluhur.kkp.entity.people;
import com.budiluhur.kkp.repository.ILoginRepo;
import com.budiluhur.kkp.repository.IPeopleRepo;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Login", tags = "Login")
public class loginController {
	

	@Value("${si_security_key:not_defined}")
	protected String securityKey;
	
	@Autowired
	ILoginRepo ilr;
	
	@Autowired
	IPeopleRepo ipr;
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	@ResponseBody
	public ResponseEntity<login> getUserName(@RequestBody login l) {
		return ResponseEntity.ok(ilr.findUserByNrkAndPassword(l.getNrk(), l.getPassword()));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findperson")
	@ResponseBody
	public ResponseEntity<people> findperson(@RequestBody login l) {
		login lo = ilr.findUserByNrkAndPassword(l.getNrk(), l.getPassword());
		if(lo==null) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok(ipr.findById(l.getNrk()).get());
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/newPerson")
	@ResponseBody
	public ResponseEntity<people> newPerson(@RequestBody people l) {
		return ResponseEntity.ok(ipr.save(l));
	}
	@RequestMapping(method = RequestMethod.POST, value = "/newLogin")
	@ResponseBody
	public ResponseEntity<login> newLogin(@RequestBody login l) {
		return ResponseEntity.ok(ilr.save(l));
	}
}