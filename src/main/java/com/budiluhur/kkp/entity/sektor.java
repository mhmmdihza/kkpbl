package com.budiluhur.kkp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sektor")
public class sektor {
 
	@Id
    @Column(name ="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name ="nama_sektor")
	private String namaSektor;
	
	@Column(name="alamat_sektor")
	private String alamatSektor;
	
	@Column(name="telpon")
	private String telpon;
	
	@Column(name="kode_pos")
	private String kodePos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNamaSektor() {
		return namaSektor;
	}

	public void setNamaSektor(String namaSektor) {
		this.namaSektor = namaSektor;
	}

	public String getAlamatSektor() {
		return alamatSektor;
	}

	public void setAlamatSektor(String alamatSektor) {
		this.alamatSektor = alamatSektor;
	}

	public String getTelpon() {
		return telpon;
	}

	public void setTelpon(String telpon) {
		this.telpon = telpon;
	}

	public String getKodePos() {
		return kodePos;
	}

	public void setKodePos(String kodePos) {
		this.kodePos = kodePos;
	}
	
	
	 
}