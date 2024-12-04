package com.abhishekvermaa10.repository;

import java.time.LocalDate;
import java.util.List;

import com.abhishekvermaa10.entity.Owner;

/**
 * @author abhishekvermaa10
 */
public interface CustomOwnerRepository {
	
	Owner findOwnerById(int ownerId);
	
	List<Owner> findByFirstNameStartsWith(String firstName);

	Owner findOwnerByPetId(int petId);

	List<Owner> findAllOwnersByPetDateOfBirthRange(LocalDate startDate, LocalDate endDate);

	List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize);
}
