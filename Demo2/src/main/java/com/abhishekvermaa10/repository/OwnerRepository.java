package com.abhishekvermaa10.repository;

import java.util.List;
import java.util.Optional;

import com.abhishekvermaa10.entity.Owner;

/**
 * @author abhishekvermaa10
 */
public interface OwnerRepository {
	
	void save(Owner owner);

	Optional<Owner> findById(int ownerId);

	void updatePetDetails(int ownerId, String petName);

	void deleteById(int ownerId);

	List<Owner> findAll();
	
}
