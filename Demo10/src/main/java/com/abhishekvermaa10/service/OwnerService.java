package com.abhishekvermaa10.service;

import java.util.List;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.exception.PetNotFoundException;

/**
 * @author abhishekvermaa10
 */
public interface OwnerService {

	void saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException;

	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException, PetNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException;

	List<OwnerDTO> findAllOwners();

}
