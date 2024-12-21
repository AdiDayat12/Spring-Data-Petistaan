package com.abhishekvermaa10.service;

import java.util.List;

import com.abhishekvermaa10.exception.OwnerNotFoundException;

/**
 * @author abhishekvermaa10
 */
public interface OwnerService {

	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int pageNumber, int numberOfRecordsPerPage);

}
