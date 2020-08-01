package com.budiluhur.kkp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unit_kendaraan")
public class unitKendaraan {
	
	@Id
	@Column(name ="plat_no")
	private String platNo;
	
	@Column(name ="detail_unit")
	private String detailUnit;
	
	@Column(name ="stnk")
	private String stnk;

	public String getPlatNo() {
		return platNo;
	}

	public void setPlatNo(String platNo) {
		this.platNo = platNo;
	}

	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}

	public String getStnk() {
		return stnk;
	}

	public void setStnk(String stnk) {
		this.stnk = stnk;
	}
	
	
}