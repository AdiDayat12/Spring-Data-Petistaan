package com.scaleupindia.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scaleupindia.h2.entity.Pet;

/**
 * @author abhishekvermaa10
 *
 */
public interface PetRepository extends JpaRepository<Pet, Integer> {

}