package com.abhishekvermaa10.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.repository.PetRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

/**
 * @author abhishekvermaa10
 */
@Repository
public class PetRepositoryImpl implements PetRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Optional<Pet> findById(int petId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			Pet pet = entityManager.find(Pet.class, petId);
			return Optional.ofNullable(pet);
		}
	}

}
