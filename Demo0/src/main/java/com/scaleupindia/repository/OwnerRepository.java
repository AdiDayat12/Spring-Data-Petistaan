package com.scaleupindia.repository;

import java.util.List;

import com.scaleupindia.dto.OwnerDTO;

/**
 * @author abhishekvermaa10
 *
 */
public interface OwnerRepository {
	void saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId);

	void updatePetDetails(int petId, String petName);

	void deleteOwner(int ownerId, int petId);

	List<OwnerDTO> findAllOwners();
}
