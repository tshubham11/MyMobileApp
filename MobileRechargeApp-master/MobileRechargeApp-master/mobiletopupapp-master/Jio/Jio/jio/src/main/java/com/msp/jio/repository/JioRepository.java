package com.msp.jio.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.jio.entity.Jio;
//@Repository
public interface JioRepository extends JpaRepository<Jio, Integer> {

	public Jio findByMobileNumber(String mobileNumber);
}
