package com.abhishekvermaa10.service;

import java.util.List;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerIDDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author abhishekvermaa10
 */
public interface OwnerService {
	
	OwnerIDDTO saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwners();

	Page<OwnerPetInfoDTO> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(Pageable pageable);

}
