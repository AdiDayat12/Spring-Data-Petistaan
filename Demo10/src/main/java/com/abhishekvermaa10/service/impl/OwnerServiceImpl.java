package com.abhishekvermaa10.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.h2.entity.Pet;
import com.abhishekvermaa10.h2.repository.PetRepository;
import com.abhishekvermaa10.mysql.entity.Owner;
import com.abhishekvermaa10.mysql.repository.OwnerRepository;
import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.util.OwnerMapper;
import com.abhishekvermaa10.util.PetMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	private final PetRepository petRepository;
	private final OwnerMapper ownerMapper;
	private final PetMapper petMapper;
	@Value("${owner.not.found}")
	private String ownerNotFound;
	@Value("${pet.not.found}")
	private String petNotFound;

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Pet pet = petMapper.petDTOToPet(ownerDTO.getPetDTO());
		Pet savedPet = petRepository.save(pet);
		Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
		owner.setPetId(savedPet.getId());
		ownerRepository.save(owner);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		Pet pet = petRepository.findById(owner.getPetId())
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, owner.getPetId())));
		OwnerDTO ownerDTO = ownerMapper.ownerToOwnerDTO(owner);
		ownerDTO.setPetDTO(petMapper.petToPetDTO(pet));
		return ownerDTO;
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException, PetNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		Pet pet = petRepository.findById(owner.getPetId())
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, owner.getPetId())));
		pet.setName(petName);
		petRepository.save(pet);
	}

	@Transactional(transactionManager = "mysqlTransactionManager", rollbackFor = PetNotFoundException.class)
	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		int petId = owner.getPetId();
		ownerRepository.deleteById(ownerId);
		boolean petExists = petRepository.existsById(petId);
		if (!petExists) {
			throw new PetNotFoundException(String.format(petNotFound, petId));
		} else {
			petRepository.deleteById(petId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll().stream().map(owner -> {
			OwnerDTO ownerDTO = ownerMapper.ownerToOwnerDTO(owner);
			petRepository.findById(owner.getPetId())
			.ifPresent(pet -> ownerDTO.setPetDTO(petMapper.petToPetDTO(pet)));
			return ownerDTO;
		}).toList();
	}

}
