package com.scaleupindia.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scaleupindia.dto.OwnerDTO;
import com.scaleupindia.exception.OwnerNotFoundException;
import com.scaleupindia.repository.OwnerRepository;
import com.scaleupindia.service.OwnerService;

/**
 * @author abhishekvermaa10
 *
 */
@Service
public class OwnerServiceImpl implements OwnerService {
	@Autowired
	private OwnerRepository ownerRepository;
	@Value("${owner.not.found}")
	private String ownerNotFound;

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		ownerRepository.saveOwner(ownerDTO);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		OwnerDTO ownerDTO = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(ownerDTO)) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		}
		return ownerDTO;
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		OwnerDTO ownerDTO = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(ownerDTO)) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		}
		int petId = ownerDTO.getPetDTO().getId();
		ownerRepository.updatePetDetails(petId, petName);
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		OwnerDTO ownerDTO = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(ownerDTO)) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		}
		int petId = ownerDTO.getPetDTO().getId();
		ownerRepository.deleteOwner(ownerId, petId);
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAllOwners();
	}
}
