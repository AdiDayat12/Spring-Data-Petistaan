package com.abhishekvermaa10.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.repository.OwnerRepository;
import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.util.OwnerMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;
	@Value("${owner.not.found}")
	private String ownerNotFound;
	@Value("${owner.pet.not.found}")
	private String ownerPetNotFound;

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findOwnerById(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		} else {
			return ownerMapper.ownerToOwnerDTO(owner);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwnersByFirstNameInitials(String firstName) {
		return ownerRepository.findByFirstNameStartsWith(firstName)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public OwnerDTO findOwnerByPetId(int petId) throws OwnerNotFoundException {
		return ownerRepository.findOwnerByPetId(petId)
				.map(ownerMapper::ownerToOwnerDTO)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerPetNotFound, petId)));
	}
	
	@Override
	public List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
		return ownerRepository.findAllOwnersByPetDateOfBirthRange(startDate, endDate)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int pageNumber,
			int numberOfRecordsPerPage) {
		Pageable pageable = PageRequest.of(pageNumber, numberOfRecordsPerPage);
		return ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
	}

}
