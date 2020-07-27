package com.budiluhur.kkp.entity;

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

@Entity
@Table(name = "people")
public class people {
 
	@Id
    @Column(name ="nrk")
    private String nrk;
	
	@Column(name ="nama")
	private String nama;
	
	@Column(name ="nik")
	private String nik;
	
	@Column(name="alamat")
	private String alamat;
	
	@Column(name="nomerhp")
	private String nomerhp;
	
	@Column(name="jabatan")
	private String jabatan;
	 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sektor", referencedColumnName = "id")
	private sektor sektor;
	
	@Column(name="signature")
	private String signature;

	public String getNrk() {
		return nrk;
	}

	public void setNrk(String nrk) {
		this.nrk = nrk;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getNomerhp() {
		return nomerhp;
	}

	public void setNomerhp(String nomerhp) {
		this.nomerhp = nomerhp;
	}

	public String getJabatan() {
		return jabatan;
	}

	public void setJabatan(String jabatan) {
		this.jabatan = jabatan;
	}

	public sektor getSektor() {
		return sektor;
	}

	public void setSektor(sektor sektor) {
		this.sektor = sektor;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}
	
	
}