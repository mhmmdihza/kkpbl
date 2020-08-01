package com.budiluhur.kkp.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.budiluhur.kkp.entity.formDetail;
import com.budiluhur.kkp.entity.formHeader;


@Service
@Transactional
public class saveDetail {
	@PersistenceContext
	EntityManager em;
	
	public void saveDetail(formHeader fh) {
		StringBuilder sq = new StringBuilder();
		sq.append("insert into form_detail(barang,form_id,qty) values");
		 //formHeader fAfter = ifr.save(fh); 
		int n = 0;
		 for(formDetail fd:fh.getFormDetail()) {
			 
			 sq.append("("+fd.getBarang().getId()+","+fh.getId()+",'"+fd.getQty()+"')");
			 if(fh.getFormDetail().size()-1!=n) {
				 sq.append(",");
			 }
			 //fd.setFormHeader(fAfter); 
			 //ifdr.save(fd); 
			 n++;
		 }
		 System.out.println("detail " + sq.toString());
		 try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Query q = em.createNativeQuery(sq.toString());
		 q.executeUpdate();
	}
	
}