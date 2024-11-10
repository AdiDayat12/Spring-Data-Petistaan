package com.abhishekvermaa10.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.repository.OwnerRepository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

/**
 * @author abhishekvermaa10
 */
@Repository
public class OwnerRepositoryImpl implements OwnerRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void save(Owner owner) {
		throw new UnsupportedOperationException("Adding new owner is not supported.");
	}

	@Override
	public Optional<Owner> findById(int ownerId) {
		throw new UnsupportedOperationException("Fetching owner by ownerId is not supported.");
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) {
		throw new UnsupportedOperationException("Updating name of pet by ownerId is not supported.");
	}

	@Override
	public void deleteById(int ownerId) {
		throw new UnsupportedOperationException("Deleting owner by ownerId is not supported.");
	}

	@Override
	public List<Owner> findAll() {
		throw new UnsupportedOperationException("Fetching all owners is not supported.");
	}

}
