package com.abhishekvermaa10.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.repository.PetRepository;
import com.abhishekvermaa10.service.PetService;
import com.abhishekvermaa10.util.PetMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;
	private final PetMapper petMapper;
	@Value("${pet.not.found}")
	private String petNotFound;

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException {
		Pet pet = petRepository.findPetById(petId);
		if (Objects.isNull(pet)) {
			throw new PetNotFoundException(String.format(petNotFound, petId));
		} else {
			return petMapper.petToPetDTO(pet);
		}
	}

	@Override
	public Double findAverageAgeOfPet() {
		Double averageAge = petRepository.findAverageAgeOfPet();
		if (Objects.isNull(averageAge)) {
			averageAge = 0.0;
		}
		return averageAge;
	}

}
