package com.scaleupindia.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import com.scaleupindia.dto.OwnerDTO;
import com.scaleupindia.exception.OwnerNotFoundException;
import com.scaleupindia.repository.OwnerRepository;
import com.scaleupindia.repository.impl.OwnerRepositoryImpl;
import com.scaleupindia.service.OwnerService;

/**
 * @author abhishekvermaa10
 *
 */
public class OwnerServiceImpl implements OwnerService {
	private Properties properties;
	private OwnerRepository ownerRepository;
	private static final String OWNER_NOT_FOUND = "owner.not.found";

	public OwnerServiceImpl(Properties properties) {
		this.ownerRepository = new OwnerRepositoryImpl(properties);
		this.properties = properties;
	}

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		ownerRepository.saveOwner(ownerDTO);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		OwnerDTO ownerDTO = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(ownerDTO)) {
			throw new OwnerNotFoundException(properties.getProperty(OWNER_NOT_FOUND) + ownerId);
		}
		return ownerDTO;
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		OwnerDTO ownerDTO = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(ownerDTO)) {
			throw new OwnerNotFoundException(properties.getProperty(OWNER_NOT_FOUND) + ownerId);
		}
		int petId = ownerDTO.getPetDTO().getId();
		ownerRepository.updatePetDetails(petId, petName);
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		OwnerDTO ownerDTO = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(ownerDTO)) {
			throw new OwnerNotFoundException(properties.getProperty(OWNER_NOT_FOUND) + ownerId);
		}
		int petId = ownerDTO.getPetDTO().getId();
		ownerRepository.deleteOwner(ownerId, petId);
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAllOwners();
	}
}
