package com.abhishekvermaa10.mysql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhishekvermaa10.mysql.entity.Owner;

/**
 * @author abhishekvermaa10
 */
public interface OwnerRepository extends JpaRepository<Owner, Integer>{
	
	Optional<Owner> findByPetId(int petId);
	
}
