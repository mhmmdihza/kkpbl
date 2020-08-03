package com.budiluhur.kkp.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.kkp.entity.barang;
import com.budiluhur.kkp.entity.emailReport;
import com.budiluhur.kkp.entity.login;
import com.budiluhur.kkp.entity.sektor;
import com.budiluhur.kkp.entity.unitKendaraan;
import com.budiluhur.kkp.repository.IBarangRepo;
import com.budiluhur.kkp.repository.ISektorRepo;
import com.budiluhur.kkp.repository.IUnitKendaraanRepo;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Component", tags = "Component")
@Transactional
public class componentController {
	
	@PersistenceContext
	EntityManager em;

	@Value("${si_security_key:not_defined}")
	protected String securityKey;
	
	@Autowired
	IBarangRepo ibr;
	
	@Autowired
	ISektorRepo isr;
	
	@Autowired
	IUnitKendaraanRepo iur;
	
	@Autowired
    private JavaMailSender javaMailSender;

	
	
	@RequestMapping(method = RequestMethod.POST, value = "/generate")
	@ResponseBody
	public ResponseEntity<String> sendEmailReport(@RequestBody emailReport ems) throws MessagingException, IOException{
		System.out.println("email to "+ ems.getEmail());
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT form_header.id,form_header.tgl_inq,form_header.people,form_header.sektor,form_header.unit_kendaraan,form_header.reason,form_detail.barang,form_detail.qty from form_header,form_detail  ");
		sb.append("where form_header.tgl_inq >= '"+ems.getStartDate()+"' and form_header.tgl_inq <= '"+ems.getEndDate()+"'");
		sb.append(" order by form_header.id desc");
		System.out.println("Query "+sb.toString());
		Query q = em.createNativeQuery(sb.toString());
		List rs = q.getResultList();
		List<String> list = new ArrayList();
		sb = new StringBuilder("id form,"
				+ "tgl pengajuan,"
				+ "nrk,"
				+ "sektor"
				+ ",unit/kendaraan,"
				
				+ "keterangan,"
				+ "barang,"
				+ "qty"
				+ "\n");
		for (int i = 0; i < rs.size(); i++) {
			Object[] obj = (Object[]) rs.get(i);
			Optional<barang> br = ibr.findById(Long.valueOf(((Integer) obj[6]).toString()));
			sb.append((((Integer) obj[0]).toString()));
			sb.append(",");
			sb.append(((Date) obj[1]).toString());
			sb.append(",");
			sb.append(((String) obj[2]));
			sb.append(",");
			sb.append(((Integer) obj[3]).toString());
			sb.append(",");
			sb.append(((String) obj[4]));
			sb.append(",");
			sb.append(((String) obj[5]));
			sb.append(",");
			sb.append(br.get().getNamaBarang());
			sb.append(",");
			sb.append(((String) obj[7]));
			sb.append("\n");		
		}
		
		System.out.println(" resultList "+sb.toString());
		
		MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(ems.getEmail());

        helper.setSubject("Report pengajuan "+new Date());

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Report attachment</h1>", true);
        
        String fileName = "report"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        
        File f = new File(Paths.get("").toAbsolutePath().toString(),fileName);
        f.createNewFile();
        FileWriter myWriter = new FileWriter(Paths.get("").toAbsolutePath().toString() + "/"+fileName);
        myWriter.write(sb.toString());
        myWriter.close();
        
        helper.addAttachment("report.csv", f);
        
        javaMailSender.send(msg);
        
        f.delete();
        
		return ResponseEntity.ok("ok");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/query")
	@ResponseBody
	public ResponseEntity<String> saveQuery(@RequestBody barang b) {
		StringBuilder sb = new StringBuilder();
		sb.append(b.getNamaBarang());
		Query q = em.createNativeQuery(sb.toString());
		System.out.println("sb"+sb);
		q.executeUpdate();
		return ResponseEntity.ok("ok");
	}
	
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
	//
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
