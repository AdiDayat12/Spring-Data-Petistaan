package com.scaleupindia.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scaleupindia.dto.OwnerDTO;
import com.scaleupindia.entity.Owner;
import com.scaleupindia.entity.Pet;
import com.scaleupindia.exception.OwnerNotFoundException;
import com.scaleupindia.repository.OwnerRepository;
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
	@Value("${owner.not.found}")
	private String ownerNotFound;

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Owner owner = MapperUtil.convertOwnerDtoToEntity(ownerDTO);
		ownerRepository.save(owner);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if (optionalOwner.isEmpty()) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		} else {
			Owner owner = optionalOwner.get();
			return MapperUtil.convertOwnerEntityToDto(owner);
		}
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if (optionalOwner.isEmpty()) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		} else {
			Owner owner = optionalOwner.get();
			Pet pet = owner.getPet();
			pet.setName(petName);
			owner.setPet(pet);
			ownerRepository.save(owner);
		}
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if (optionalOwner.isEmpty()) {
			throw new OwnerNotFoundException(ownerNotFound + ownerId);
		} else {
			ownerRepository.deleteById(ownerId);
		}
	}

	@Override
	public long findTotalNumberOfOwners() {
		return ownerRepository.count();
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll().stream().map(MapperUtil::convertOwnerEntityToDto).toList();
	}

	@Override
	public List<OwnerDTO> findAllSortedOwners(String sortingParameter, boolean sortDescending) {
		Sort.Direction modifiedDirection = Sort.Direction.ASC;
		if (sortDescending) {
			modifiedDirection = Sort.Direction.DESC;
		}
		Sort sort = Sort.by(modifiedDirection, sortingParameter);
		return ownerRepository.findAll(sort).stream().map(MapperUtil::convertOwnerEntityToDto).toList();
	}

	@Override
	public List<OwnerDTO> findAllPaginatedOwners(int pageNumber, int numberOfRecordsPerPage) {
		PageRequest pageRequest = PageRequest.of(pageNumber, numberOfRecordsPerPage);
		return ownerRepository.findAll(pageRequest).stream().map(MapperUtil::convertOwnerEntityToDto).toList();
	}

	@Override
	public List<OwnerDTO> findAllPaginatedAndSortedOwners(int pageNumber, int numberOfRecordsPerPage,
			String sortingParameter, boolean sortDescending) {
		Sort.Direction modifiedDirection = Sort.Direction.ASC;
		if (sortDescending) {
			modifiedDirection = Sort.Direction.DESC;
		}
		Sort sort = Sort.by(modifiedDirection, sortingParameter);
		PageRequest pageRequest = PageRequest.of(pageNumber, numberOfRecordsPerPage, sort);
		return ownerRepository.findAll(pageRequest).stream().map(MapperUtil::convertOwnerEntityToDto).toList();
	}
}
