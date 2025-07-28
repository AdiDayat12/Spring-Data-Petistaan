package com.abhishekvermaa10.repository;

import java.util.List;

import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abhishekvermaa10.entity.Owner;

/**
 * @author abhishekvermaa10
 */
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

	@Query("SELECT o.id, o.firstName, o.lastName, o.pet.name FROM Owner o JOIN o.pet")
	List<Object[]> findIdAndFirstNameAndLastNameAndPetName(Pageable pageable);

}
