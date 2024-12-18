package com.abhishekvermaa10.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.h2.entity.Pet;
import com.abhishekvermaa10.h2.repository.PetRepository;
import com.abhishekvermaa10.mysql.entity.Owner;
import com.abhishekvermaa10.mysql.repository.OwnerRepository;
import com.abhishekvermaa10.service.PetService;
import com.abhishekvermaa10.util.OwnerMapper;
import com.abhishekvermaa10.util.PetMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;
	private final OwnerRepository ownerRepository;
	private final PetMapper petMapper;
	private final OwnerMapper ownerMapper;
	@Value("${pet.not.found}")
	private String petNotFound;
	@Value("${owner.pet.not.found}")
	private String ownerPetNotFound;

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException, OwnerNotFoundException {
		Pet pet = petRepository.findById(petId)
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, petId)));
		Owner owner = ownerRepository.findByPetId(petId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerPetNotFound, petId)));
		PetDTO petDTO = petMapper.petToPetDTO(pet);
		petDTO.setOwnerDTO(ownerMapper.ownerToOwnerDTO(owner));
		return petDTO;
	}

}
