package com.abhishekvermaa10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhishekvermaa10.entity.Pet;

/**
 * @author abhishekvermaa10
 */
public interface PetRepository extends JpaRepository<Pet, Integer> {

	Optional<Pet> findById(int petId);
	
}
