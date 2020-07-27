package com.budiluhur.kkp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "form_detail")
public class formDetail {
 
	@Id
    @Column(name ="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "form_id", nullable = false)
	@JsonBackReference
	private formHeader formHeader;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "barang", referencedColumnName = "id")
	private barang barang;
	
	@Column(name ="qty")
	private String qty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public formHeader getFormHeader() {
		return formHeader;
	}

	public void setFormHeader(formHeader formHeader) {
		this.formHeader = formHeader;
	}

	public barang getBarang() {
		return barang;
	}

	public void setBarang(barang barang) {
		this.barang = barang;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}
	
	
}