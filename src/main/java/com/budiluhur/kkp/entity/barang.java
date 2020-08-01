package com.budiluhur.kkp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "barang")
public class barang {
	@Id
    @Column(name ="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name ="nama_barang")
	private String namaBarang;
	
	@Column(name ="satuan_kuantitas")
	private String satuanKuantitas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNamaBarang() {
		return namaBarang;
	}

	public void setNamaBarang(String namaBarang) {
		this.namaBarang = namaBarang;
	}

	public String getSatuanKuantitas() {
		return satuanKuantitas;
	}

	public void setSatuanKuantitas(String satuanKuantitas) {
		this.satuanKuantitas = satuanKuantitas;
	}

	
}