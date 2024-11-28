package com.abhishekvermaa10.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhishekvermaa10.entity.Owner;

/**
 * @author abhishekvermaa10
 */
public interface OwnerRepository extends JpaRepository<Owner, Integer>{
	
	Owner findById(int ownerId);
	
	List<Owner> findByFirstNameStartsWith(String firstName);
	
	Optional<Owner> findByPet_Id(int petId);
	
}
