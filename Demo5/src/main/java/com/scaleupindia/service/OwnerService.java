package com.scaleupindia.service;

import java.util.List;

import com.scaleupindia.dto.OwnerDTO;
import com.scaleupindia.exception.OwnerNotFoundException;

/**
 * @author abhishekvermaa10
 *
 */
public interface OwnerService {
	void saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	long findTotalNumberOfOwners();

	List<OwnerDTO> findAllOwners();

	List<OwnerDTO> findAllSortedOwners(String sortingParameter, boolean sortDescending);

	List<OwnerDTO> findAllPaginatedOwners(int pageNumber, int numberOfRecordsPerPage);

	List<OwnerDTO> findAllPaginatedAndSortedOwners(int pageNumber, int numberOfRecordsPerPage, String sortingParameter,
			boolean sortDescending);
}