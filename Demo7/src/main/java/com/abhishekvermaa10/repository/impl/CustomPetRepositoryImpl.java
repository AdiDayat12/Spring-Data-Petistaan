package com.abhishekvermaa10.repository.impl;

import java.time.LocalDate;

import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.repository.CustomPetRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

/**
 * @author abhishekvermaa10
 */
public class CustomPetRepositoryImpl implements CustomPetRepository {
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Pet findPetById(int petId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
			Root<Pet> root = criteriaQuery.from(Pet.class);
			Predicate where = criteriaBuilder.conjunction();
			where = criteriaBuilder.and(where, criteriaBuilder.equal(root.get("id"), petId));
			criteriaQuery.select(root).where(where);
			return entityManager.createQuery(criteriaQuery)
					.getSingleResult();
		}
	}

	@Override
	public Double findAverageAgeOfPet() {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
			Root<Pet> root = criteriaQuery.from(Pet.class);
			Selection<Double> selection = criteriaBuilder.avg(criteriaBuilder.diff(LocalDate.now().getYear(),
					criteriaBuilder.function("year", Integer.class, root.get("birthDate"))));
			criteriaQuery.select(selection);
			return entityManager.createQuery(criteriaQuery)
					.getSingleResult();
		}
	}

}
