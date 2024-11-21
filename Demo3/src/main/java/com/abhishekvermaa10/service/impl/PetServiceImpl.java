package com.abhishekvermaa10.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.PetDTO;
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
		return petRepository.findById(petId)
				.map(petMapper::petToPetDTO)
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, petId)));
	}

}
