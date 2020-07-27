package com.budiluhur.kkp.repository;

import org.springframework.data.repository.CrudRepository;

import com.budiluhur.kkp.entity.barang;


public interface IBarangRepo extends CrudRepository<barang, Long> {
	
}