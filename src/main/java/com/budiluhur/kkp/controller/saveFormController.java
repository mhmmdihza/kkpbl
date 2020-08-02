package com.budiluhur.kkp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.kkp.common.Encryption;
import com.budiluhur.kkp.common.saveDetail;
import com.budiluhur.kkp.entity.formDetail;
import com.budiluhur.kkp.entity.formHeader;
import com.budiluhur.kkp.entity.imageBase64;
import com.budiluhur.kkp.entity.login;
import com.budiluhur.kkp.entity.people;
import com.budiluhur.kkp.repository.IFormDetailRepo;
import com.budiluhur.kkp.repository.IFormHeaderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "FORM INQ", tags = "FORM")
@Transactional
public class saveFormController extends Encryption{

	@Autowired
	IFormHeaderRepo ifr;
	
	@Autowired
	IFormDetailRepo ifdr;
	
	@Autowired
	saveDetail sv;
	
	@PersistenceContext
	EntityManager em;
	
	@Value("${si_security_key:not_defined}")
	protected String securityKey;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(method = RequestMethod.POST, value = "/form/save")
	@ResponseBody
	public ResponseEntity<formHeader> saveForm(@RequestBody formHeader fh) {
		imageFile im = new imageFile();
		imageBase64 imgbase = new imageBase64();
		imgbase.setImageBase64(fh.getFormSign());
		fh.setFormSign(fh.getId()+".png");
		String nrkKasudin = "null";
		String nrkKepsek = "null";
		String reason = "'-'";
		fh.setTglInq(new Date());
		if(fh.getApprovedByKasudin()!=null) {
			nrkKasudin = fh.getApprovedByKasudin().getNrk();
			nrkKasudin = "'"+nrkKasudin+"'";
		}
		if(fh.getApprovedByKepsek()!=null) {
			nrkKepsek = fh.getApprovedByKepsek().getNrk();
			nrkKepsek = "'"+nrkKepsek+"'";
		}
		if(fh.getReason()!=null) {
			reason = fh.getReason();
			reason = "'"+reason+"'";
		}
		StringBuilder sq = new StringBuilder();
		sq.append("insert into form_header(tgl_inq,people,sektor,unit_kendaraan,form_sign,approval_kepsek_sektor,approval_kasudin,reason)values(");
		sq.append("NOW(),'"+fh.getPeople().getNrk()+"',"+fh.getSektor().getId()+",'"+fh.getUnitKendaraan().getPlatNo()+"','"
		+fh.getFormSign()+"',"
				+nrkKepsek+
				","+nrkKasudin+","+
		reason+")");
		System.out.println("sq "+sq);
		Query q = em.createNativeQuery(sq.toString());
		q.executeUpdate();
		
		q = em.createNativeQuery("SELECT id FROM form_header ORDER BY id DESC LIMIT 1");
		List rs = q.getResultList();
		q.setFlushMode(FlushModeType.COMMIT);
		
		em.flush();
		Integer id = rs.get(0) != null ? ((Integer) rs.get(0)) : null;
		
		System.out.println("id" +id);
		fh.setId(Long.parseLong(id.toString()));
		//
		im.saveFormSign(fh.getId()+".png",imgbase);
		fh.setFormSign(fh.getId()+".png");
		
		q = em.createNativeQuery("Update form_header set form_sign = '"+fh.getFormSign()+".png' where id = "+fh.getId());
		q.executeUpdate();
		
		
		
		try {
			System.out.println("mapper"+mapper.writeValueAsString(fh));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sq = new StringBuilder();
		sq.append("insert into form_detail(barang,form_id,qty) values");
		formHeader customer = em.getReference(formHeader.class,Long.parseLong(id.toString()));
		System.out.println("refference" +customer.getId());
		 //formHeader fAfter = ifr.save(fh); 
		int n = 0;
		 for(formDetail fd:fh.getFormDetail()) {
			 fd.setFormHeader(customer);
			 sq.append("("+fd.getBarang().getId()+","+fh.getId()+",'"+fd.getQty()+"')");
			 if(fh.getFormDetail().size()-1!=n) {
				 sq.append(",");
			 }
			 //em.persist(fd);
			 //fd.setFormHeader(fAfter); 
			 //ifdr.save(fd); 
			 n++;
		 }

		    try {
		    	File dir = new File(Paths.get("").toAbsolutePath().toString()+"/FORMDETAIL");
		    	if(!dir.exists()) {
		    		dir.mkdir();
		    	}
		    	File myObj = new File(Paths.get("").toAbsolutePath().toString()+"/FORMDETAIL/"+id+".txt");
		    	myObj.createNewFile();
		    	
		        FileWriter myWriter = new FileWriter(Paths.get("").toAbsolutePath().toString()+"/FORMDETAIL/"+id+".txt");
		        myWriter.write(sq.toString());
		        myWriter.close();
		        System.out.println("Successfully wrote to the file.");
		      } catch (IOException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }

		 //q = em.createNativeQuery(sq.toString());
		 //q.executeUpdate();
		
		 
		
		return ResponseEntity.ok(fh);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/form/update")
	@ResponseBody
	public void updateExecute(@RequestBody formHeader f) throws IOException {
		StringBuilder sq = new StringBuilder();
		String nrkKasudin = "null";
		if(f.getApprovedByKasudin()!=null) {
			nrkKasudin = f.getApprovedByKasudin().getNrk();
			nrkKasudin = "'"+nrkKasudin+"'";
		}	
		sq.append("update form_header ");
		sq.append("set approval_kepsek_sektor = '"+f.getApprovedByKepsek().getNrk()+"',");
		sq.append("approval_kasudin  = "+nrkKasudin+",");
		sq.append(" reason = '"+f.getReason()+"' ");
		sq.append("where id = "+f.getId());
		System.out.println("sq "+sq);
		Query q = em.createNativeQuery(sq.toString());
		q.executeUpdate();
		 
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/form/savedetail/{id}")
	@ResponseBody
	public void saveDetail(@PathVariable("id") String id) throws IOException {
		System.out.println("Sched");
		System.out.println("jumlah"+id);
		 //formHeader fAfter = ifr.save(fh); 
			File file = new File(Paths.get("").toAbsolutePath().toString()+"/FORMDETAIL/"+id+".txt");
			Scanner myReader = new Scanner(file);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        System.out.println(data);
		        Query q = em.createNativeQuery(data);
				 q.executeUpdate();
		     }
		      file.delete();
			
		 
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/form/findformbynrk")
	@ResponseBody
	public ResponseEntity<List<formHeader>> findFormByNrk(@RequestBody login l) {
		//em.createNativeQuery("SELECT * FROM form_header INNER JOIN people ON form_header.people=people.nrk where form_header.people ='"+l.getNrk()+"'");
		return ResponseEntity.ok(ifr.findFormByPeopleNrk(l.getNrk()));
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "/form/findformbysektor")
	@ResponseBody
	public ResponseEntity<List<formHeader>> findFormBySektor(@RequestBody people p) {
		//em.createNativeQuery("SELECT * FROM form_header INNER JOIN people ON form_header.people=people.nrk where form_header.people ='"+l.getNrk()+"'");
		return ResponseEntity.ok(ifr.findFormBySektor(p.getSektor().getId()));
		
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