package com.budiluhur.kkp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "form_header")
public class formHeader {
 
	@Id
    @Column(name ="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="tgl_inq")
	private Date tglInq;
	
	@Column(name="reason")
	private String reason;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "approval_kepsek_sektor", referencedColumnName = "nrk")
	private people approvedByKepsek;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "approval_kasudin", referencedColumnName = "nrk")
	private people approvedByKasudin;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "people", referencedColumnName = "nrk")
	private people people;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sektor", referencedColumnName = "id")
	private sektor sektor;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unit_kendaraan", referencedColumnName = "plat_no")
	private unitKendaraan unitKendaraan;
	
	@Column(name="form_sign")
	private String formSign;
	
	@OneToMany(mappedBy = "formHeader", cascade = { CascadeType.MERGE,
			CascadeType.PERSIST }, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<formDetail> formDetail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTglInq() {
		return tglInq;
	}

	public void setTglInq(Date tglInq) {
		this.tglInq = tglInq;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public people getApprovedByKepsek() {
		return approvedByKepsek;
	}

	public void setApprovedByKepsek(people approvedByKepsek) {
		this.approvedByKepsek = approvedByKepsek;
	}

	public people getApprovedByKasudin() {
		return approvedByKasudin;
	}

	public void setApprovedByKasudin(people approvedByKasudin) {
		this.approvedByKasudin = approvedByKasudin;
	}

	public people getPeople() {
		return people;
	}

	public void setPeople(people people) {
		this.people = people;
	}

	public sektor getSektor() {
		return sektor;
	}

	public void setSektor(sektor sektor) {
		this.sektor = sektor;
	}

	public unitKendaraan getUnitKendaraan() {
		return unitKendaraan;
	}

	public void setUnitKendaraan(unitKendaraan unitKendaraan) {
		this.unitKendaraan = unitKendaraan;
	}

	public String getFormSign() {
		return formSign;
	}

	public void setFormSign(String formSign) {
		this.formSign = formSign;
	}

	public List<formDetail> getFormDetail() {
		return formDetail;
	}

	public void setFormDetail(List<formDetail> formDetail) {
		if (this.formDetail == null) {
			this.formDetail = new ArrayList<>();
		}
		this.formDetail.clear();
		if (formDetail != null) {
			this.formDetail.addAll(formDetail);
		}
	}
	
}