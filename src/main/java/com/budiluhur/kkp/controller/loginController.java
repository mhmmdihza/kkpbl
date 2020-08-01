package com.budiluhur.kkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.kkp.common.Encryption;
import com.budiluhur.kkp.entity.login;
import com.budiluhur.kkp.entity.people;
import com.budiluhur.kkp.repository.ILoginRepo;
import com.budiluhur.kkp.repository.IPeopleRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Login", tags = "Login")
public class loginController extends Encryption{
	

	@Value("${si_security_key:not_defined}")
	protected String securityKey;
	
	@Autowired
	ILoginRepo ilr;
	
	@Autowired
	IPeopleRepo ipr;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	@ResponseBody
	public ResponseEntity<String> getUserName(@RequestBody login l) throws JsonProcessingException, Exception {
		l = ilr.findUserByNrkAndPassword(l.getNrk(), l.getPassword());
		try {
		System.out.println("encrypt"+encryptAES(mapper.writeValueAsString(l),securityKey));
		System.out.println("decrypt"+decryptAES(encryptAES(mapper.writeValueAsString(l),securityKey),securityKey));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(encryptAES(mapper.writeValueAsString(l),securityKey));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/loginbyjabatan")
	@ResponseBody
	public ResponseEntity<String> getUser(@RequestBody people p) throws JsonProcessingException, Exception {
		login l = ilr.findUserByNrkPasswordAndJabatan(p.getNrk(),p.getJabatan());
		System.out.println("decrypt"+decryptAES(encryptAES(mapper.writeValueAsString(l),securityKey),securityKey));
		return ResponseEntity.ok(encryptAES(mapper.writeValueAsString(l),securityKey));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findperson")
	@ResponseBody
	public ResponseEntity<String> findperson(@RequestBody login l) throws JsonProcessingException, Exception {
		try {
		login lo = ilr.findUserByNrkAndPassword(l.getNrk(), l.getPassword());
		if(lo==null) {
			return ResponseEntity.badRequest().body(null);
		}
		people p = ipr.findById(l.getNrk()).get();
		System.out.println("decrypt"+decryptAES(encryptAES(mapper.writeValueAsString(p),securityKey),securityKey));
		return ResponseEntity.ok(encryptAES(mapper.writeValueAsString(p),securityKey));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		

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