package com.scaleupindia.repository.impl;

import java.util.List;

import com.scaleupindia.entity.Owner;
import com.scaleupindia.entity.Pet;
import com.scaleupindia.enums.Gender;
import com.scaleupindia.repository.CustomOwnerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * @author abhishekvermaa10
 *
 */
public class CustomOwnerRepositoryImpl implements CustomOwnerRepository {
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public List<Owner> findAllOwnersWithCriteraOnGenderAndCity(String maleOwnerCity, String femaleOwnerCity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);

		Root<Owner> root = criteriaQuery.from(Owner.class);

		Predicate subPredicate1 = criteriaBuilder.equal(root.get("gender"), Gender.M);
		Predicate subPredicate2 = criteriaBuilder.equal(root.get("city"), maleOwnerCity);
		Predicate predicate1 = criteriaBuilder.and(subPredicate1, subPredicate2);

		Predicate subPredicate3 = criteriaBuilder.equal(root.get("gender"), Gender.F);
		Predicate subPredicate4 = criteriaBuilder.equal(root.get("city"), femaleOwnerCity);
		Predicate predicate2 = criteriaBuilder.and(subPredicate3, subPredicate4);

		criteriaQuery.where(criteriaBuilder.or(predicate1, predicate2));

		TypedQuery<Owner> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		List<Owner> ownerList = typedQuery.getResultList();
		entityManager.close();
		return ownerList;
	}

	@Override
	public List<Owner> findAllOwnersWithCriteraOnNotEqualOwnerPetGender() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);

		Root<Owner> root = criteriaQuery.from(Owner.class);
		Join<Owner, Pet> join = root.join("pet");

		Predicate predicate = criteriaBuilder.notEqual(root.get("gender"), join.get("gender"));

		criteriaQuery.where(predicate);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery.multiselect(root, join));
		List<Tuple> tupleList = typedQuery.getResultList();
		List<Owner> ownerList = tupleList.stream().map(tuple -> tuple.get(0, Owner.class)).toList();
		entityManager.close();
		return ownerList;
	}
}
