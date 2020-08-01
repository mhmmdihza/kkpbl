package com.budiluhur.kkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.kkp.entity.barang;
import com.budiluhur.kkp.entity.login;
import com.budiluhur.kkp.entity.sektor;
import com.budiluhur.kkp.entity.unitKendaraan;
import com.budiluhur.kkp.repository.IBarangRepo;
import com.budiluhur.kkp.repository.ISektorRepo;
import com.budiluhur.kkp.repository.IUnitKendaraanRepo;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Component", tags = "Component")
public class componentController {
	

	@Value("${si_security_key:not_defined}")
	protected String securityKey;
	
	@Autowired
	IBarangRepo ibr;
	
	@Autowired
	ISektorRepo isr;
	
	@Autowired
	IUnitKendaraanRepo iur;
	
	@RequestMapping(method = RequestMethod.POST, value = "/savebarang")
	@ResponseBody
	public ResponseEntity<barang> saveBarang(@RequestBody barang b) {
		return ResponseEntity.ok(ibr.save(b));
	}
	@RequestMapping(method = RequestMethod.POST, value = "/findbarang")
	@ResponseBody
	public ResponseEntity<barang> findBarang(@RequestBody barang b) {
		return ResponseEntity.ok(ibr.findById(b.getId()).get());
	}
	@RequestMapping(method = RequestMethod.POST, value = "/findallbarang")
	@ResponseBody
	public ResponseEntity<Iterable<barang>> findAllBarang() {
		return ResponseEntity.ok(ibr.findAll());
	}
	@RequestMapping(method = RequestMethod.POST, value = "/deletebarang")
	@ResponseBody
	public ResponseEntity<String> deleteBarang(@RequestBody barang b) {
		ibr.delete(b);
		return ResponseEntity.ok("sukses menghapus barang : " + b.getNamaBarang());
	}
	@RequestMapping(method = RequestMethod.POST, value = "/savesektor")
	@ResponseBody
	public ResponseEntity<sektor> saveSektor(@RequestBody sektor b) {
		return ResponseEntity.ok(isr.save(b));
	}
	@RequestMapping(method = RequestMethod.POST, value = "/findsektor")
	@ResponseBody
	public ResponseEntity<sektor> findSektor(@RequestBody sektor b) {
		return ResponseEntity.ok(isr.findById(b.getId()).get());
	}
	@RequestMapping(method = RequestMethod.POST, value = "/findallsektor")
	@ResponseBody
	public ResponseEntity<Iterable<sektor>> findAllSektor() {
		return ResponseEntity.ok(isr.findAll());
	}
	@RequestMapping(method = RequestMethod.POST, value = "/deletesektor")
	@ResponseBody
	public ResponseEntity<String> deleteSektor(@RequestBody sektor b) {
		isr.delete(b);
		return ResponseEntity.ok("sukses menghapus sektor : " + b.getNamaSektor());
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/saveunitKendaraan")
	@ResponseBody
	public ResponseEntity<unitKendaraan> saveUnitKendaraan(@RequestBody unitKendaraan b) {
		return ResponseEntity.ok(iur.save(b));
	}
	@RequestMapping(method = RequestMethod.POST, value = "/findunitKendaraan")
	@ResponseBody
	public ResponseEntity<unitKendaraan> findUnitKendaraan(@RequestBody unitKendaraan b) {
		return ResponseEntity.ok(iur.findById(b.getPlatNo()).get());
	}
	@RequestMapping(method = RequestMethod.POST, value = "/findallunitKendaraan")
	@ResponseBody
	public ResponseEntity<Iterable<unitKendaraan>> findAllUnitKendaraan() {
		return ResponseEntity.ok(iur.findAll());
	}
	@RequestMapping(method = RequestMethod.POST, value = "/deleteunitKendaraan")
	@ResponseBody
	public ResponseEntity<String> deleteUnitKendaraan(@RequestBody unitKendaraan b) {
		iur.delete(b);
		return ResponseEntity.ok("sukses menghapus unit Kendaraan plat no : " + b.getPlatNo());
	}
}