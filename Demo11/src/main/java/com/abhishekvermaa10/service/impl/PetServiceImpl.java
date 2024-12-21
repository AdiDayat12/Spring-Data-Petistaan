package com.abhishekvermaa10.service.impl;

import org.springframework.stereotype.Service;

import com.abhishekvermaa10.repository.PetRepository;
import com.abhishekvermaa10.service.PetService;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;

	@Override
	public Double findAverageAgeOfPet() {
		return petRepository.findAverageAgeOfPet()
				.orElse(0.0);
	}

}
