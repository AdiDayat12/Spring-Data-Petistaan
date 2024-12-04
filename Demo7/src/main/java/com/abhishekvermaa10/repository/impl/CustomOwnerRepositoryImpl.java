package com.abhishekvermaa10.repository.impl;

import java.time.LocalDate;
import java.util.List;

import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.repository.CustomOwnerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

/**
 * @author abhishekvermaa10
 */
public class CustomOwnerRepositoryImpl implements CustomOwnerRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Owner findOwnerById(int ownerId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);
			Root<Owner> root = criteriaQuery.from(Owner.class);
			root.fetch("pet");
			Predicate where = criteriaBuilder.conjunction();
			where = criteriaBuilder.and(where, criteriaBuilder.equal(root.get("id"), ownerId));
			criteriaQuery.select(root).where(where);
			return entityManager.createQuery(criteriaQuery)
					.getSingleResult();
		}
	}

	@Override
	public List<Owner> findByFirstNameStartsWith(String firstName) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);
			Root<Owner> root = criteriaQuery.from(Owner.class);
			root.fetch("pet");
			Predicate where = criteriaBuilder.conjunction();
			where = criteriaBuilder.and(where, criteriaBuilder.like(root.get("firstName"), firstName + "%"));
			criteriaQuery.select(root).where(where);
			return entityManager.createQuery(criteriaQuery)
					.getResultList();
		}
	}

	@Override
	public Owner findOwnerByPetId(int petId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);
			Root<Owner> root = criteriaQuery.from(Owner.class);
			root.fetch("pet");
			Join<Owner, Pet> join = root.join("pet");
			Predicate where = criteriaBuilder.conjunction();
			where = criteriaBuilder.and(where, criteriaBuilder.equal(join.get("id"), petId));
			criteriaQuery.select(root).where(where);
			return entityManager.createQuery(criteriaQuery)
					.getSingleResult();
		}
	}

	@Override
	public List<Owner> findAllOwnersByPetDateOfBirthRange(LocalDate startDate, LocalDate endDate) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);
			Root<Owner> root = criteriaQuery.from(Owner.class);
			root.fetch("pet");
			Join<Owner, Pet> join = root.join("pet");
			Predicate where = criteriaBuilder.conjunction();
			where = criteriaBuilder.and(where, criteriaBuilder.between(join.get("birthDate"), startDate, endDate));
			Order order = criteriaBuilder.asc(join.get("birthDate"));
			criteriaQuery.select(root).where(where).orderBy(order);
			return entityManager.createQuery(criteriaQuery)
					.getResultList();
		}
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
			Root<Owner> root = criteriaQuery.from(Owner.class);
			Join<Owner, Pet> join = root.join("pet");
			Selection<Object[]> selection = criteriaBuilder.array(root.get("id"), root.get("firstName"),
					root.get("lastName"), join.get("name"));
			criteriaQuery.select(selection);
			int skipSize = pageNumber * pageSize;
			return entityManager.createQuery(criteriaQuery)
					.setMaxResults(pageSize)
					.setFirstResult(skipSize)
					.getResultList();
		}
	}

}
