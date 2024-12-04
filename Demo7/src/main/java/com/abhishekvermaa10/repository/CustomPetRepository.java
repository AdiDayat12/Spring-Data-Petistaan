package com.abhishekvermaa10.repository;

import com.abhishekvermaa10.entity.Pet;

/**
 * @author abhishekvermaa10
 */
public interface CustomPetRepository {

	Pet findPetById(int petId);

	Double findAverageAgeOfPet();

}
