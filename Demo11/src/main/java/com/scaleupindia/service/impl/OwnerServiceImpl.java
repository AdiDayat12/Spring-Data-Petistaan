package com.scaleupindia.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scaleupindia.dto.OwnerDTO;
import com.scaleupindia.dto.PetDTO;
import com.scaleupindia.exception.OwnerNotFoundException;
import com.scaleupindia.exception.PetNotFoundException;
import com.scaleupindia.h2.entity.Pet;
import com.scaleupindia.h2.repository.PetRepository;
import com.scaleupindia.mysql.entity.Owner;
import com.scaleupindia.mysql.repository.OwnerRepository;
import com.scaleupindia.service.OwnerService;
import com.scaleupindia.util.MapperUtil;

/**
 * @author abhishekvermaa10
 *
 */
@Service
public class OwnerServiceImpl implements OwnerService {
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private PetRepository petRepository;
	@Value("${owner.not.found}")
	private String ownerNotFound;
	@Value("${pet.not.found}")
	private String petNotFound;

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Pet pet = MapperUtil.convertPetDtoToEntity(ownerDTO.getPetDTO());
		Pet savedPet = petRepository.save(pet);
		Owner owner = MapperUtil.convertOwnerDtoToEntity(ownerDTO);
		owner.setPetId(savedPet.getId());
		ownerRepository.save(owner);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if (optionalOwner.isEmpty()) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		} else {
			Owner owner = optionalOwner.get();
			OwnerDTO ownerDTO = MapperUtil.convertOwnerEntityToDto(owner);
			Optional<Pet> optionalPet = petRepository.findById(owner.getPetId());
			if (optionalPet.isEmpty()) {
				throw new PetNotFoundException(petNotFound + owner.getPetId());
			} else {
				Pet pet = optionalPet.get();
				PetDTO petDTO = MapperUtil.convertPetEntityToDto(pet);
				ownerDTO.setPetDTO(petDTO);
				return ownerDTO;
			}
		}
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException, PetNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if (optionalOwner.isEmpty()) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		} else {
			int petId = optionalOwner.get().getPetId();
			Optional<Pet> optionalPet = petRepository.findById(petId);
			if (optionalPet.isEmpty()) {
				throw new PetNotFoundException(petNotFound + petId);
			} else {
				Pet existingPet = optionalPet.get();
				existingPet.setName(petName);
				petRepository.save(existingPet);
			}
		}
	}

	@Transactional(rollbackFor = PetNotFoundException.class)
	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if (optionalOwner.isEmpty()) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		} else {
			int petId = optionalOwner.get().getPetId();
			ownerRepository.deleteById(ownerId);
			Optional<Pet> optionalPet = petRepository.findById(petId);
			if (optionalPet.isEmpty()) {
				throw new PetNotFoundException(petNotFound + petId);
			} else {
				petRepository.deleteById(petId);
			}
		}
	}
}
