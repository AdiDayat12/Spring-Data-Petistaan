package com.scaleupindia.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scaleupindia.mysql.entity.Owner;

/**
 * @author abhishekvermaa10
 *
 */
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

}