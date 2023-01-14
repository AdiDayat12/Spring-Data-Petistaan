package com.scaleupindia.repository;

import java.util.List;

import com.scaleupindia.entity.Owner;

/**
 * @author abhishekvermaa10
 *
 */
public interface CustomOwnerRepository {
	List<Owner> findAllOwnersWithCriteraOnGenderAndCity(String maleOwnerCity, String femaleOwnerCity);
	
	List<Owner> findAllOwnersWithCriteraOnNotEqualOwnerPetGender();
}