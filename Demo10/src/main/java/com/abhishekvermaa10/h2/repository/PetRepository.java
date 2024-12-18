package com.abhishekvermaa10.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhishekvermaa10.h2.entity.Pet;

/**
 * @author abhishekvermaa10
 */
public interface PetRepository extends JpaRepository<Pet, Integer>{
	
}
