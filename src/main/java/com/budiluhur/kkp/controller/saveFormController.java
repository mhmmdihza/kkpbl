package com.budiluhur.kkp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.kkp.entity.formDetail;
import com.budiluhur.kkp.entity.formHeader;
import com.budiluhur.kkp.entity.imageBase64;
import com.budiluhur.kkp.entity.people;
import com.budiluhur.kkp.repository.IFormDetailRepo;
import com.budiluhur.kkp.repository.IFormHeaderRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "FORM INQ", tags = "FORM")
public class saveFormController {

	@Autowired
	IFormHeaderRepo ifr;
	
	@Autowired
	IFormDetailRepo ifdr;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(method = RequestMethod.POST, value = "/form/save")
	@ResponseBody
	public ResponseEntity<formHeader> saveForm(@RequestBody formHeader fh) {
		imageFile im = new imageFile();
		imageBase64 imgbase = new imageBase64();
		imgbase.setImageBase64(fh.getFormSign());
		im.saveFormSign(fh.getId()+".png",imgbase);
		fh.setFormSign(fh.getId()+".png");
		fh.setTglInq(new Date());
		
		formHeader fAfter = ifr.save(fh);
		for(formDetail fd:fh.getFormDetail()) {
			fd.setFormHeader(fAfter);
			ifdr.save(fd);
		}
		
		return ResponseEntity.ok(fh);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/form/findform")
	@ResponseBody
	public ResponseEntity<formHeader> findForm(@RequestBody formHeader fh) {
		return ResponseEntity.ok(ifr.findById(fh.getId()).get());
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "/form/findAllForm")
	@ResponseBody
	public ResponseEntity<Iterable<formHeader>> findAllForm() {
		return ResponseEntity.ok(ifr.findAll());
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/form/approve/{status}")
	@ResponseBody
	public ResponseEntity<String> approveForm(@RequestBody formHeader fh,@RequestBody people p,@PathVariable("status")String status) {
		formHeader form = ifr.findById(fh.getId()).get();
		if(status.equalsIgnoreCase("APPROVED1")) {
			fh.setReason("APPROVED1");
			ifr.save(fh);
		}else if(status.equalsIgnoreCase("APPROVED2")){
			fh.setReason("APPROVED2");
			ifr.save(fh);
		}
		else {
			String reason;
			reason = "Rejected By "+p.getNik() +"("+p.getJabatan()+") - "+fh.getReason();
			fh.setReason(reason);
			ifr.save(fh);
		}
		return ResponseEntity.ok(fh.getReason());
	}
}