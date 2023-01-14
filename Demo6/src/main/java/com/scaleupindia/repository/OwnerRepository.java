package com.scaleupindia.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scaleupindia.entity.Owner;

/**
 * @author abhishekvermaa10
 *
 */
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	List<Owner> findByFirstNameStartsWith(String firstName);

	Optional<Owner> findByPet_Id(int petId);

	List<Owner> findByPet_BirthDateBetween(LocalDate startDate, LocalDate endDate);

	List<Owner> findByCityIn(List<String> cities);
}