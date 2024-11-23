package com.abhishekvermaa10.service;

import java.util.List;

import com.abhishekvermaa10.dto.OwnerDTO;

/**
 * @author abhishekvermaa10
 */
public interface OwnerService {

	List<OwnerDTO> findAllOwners();

	List<OwnerDTO> findAllSortedOwners(String sortingParameter, boolean sortDescending);

	List<OwnerDTO> findAllPaginatedOwners(int pageNumber, int numberOfRecordsPerPage);

	List<OwnerDTO> findAllPaginatedAndSortedOwners(int pageNumber, int numberOfRecordsPerPage, String sortingParameter,
			boolean sortDescending);

}
