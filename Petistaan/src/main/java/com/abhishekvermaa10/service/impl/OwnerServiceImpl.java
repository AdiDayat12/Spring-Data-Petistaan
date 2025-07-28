package com.abhishekvermaa10.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import com.abhishekvermaa10.dto.*;
import com.abhishekvermaa10.service.TransliterationService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	private static final String ownerNotFound = "owner.not.found";
	private final MessageSource messageSource;
	private final TransliterationService transliterationService;

	@Override
	public OwnerIDDTO saveOwner(OwnerDTO ownerDTO) {
		Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
		ownerRepository.save(owner);
		int id = (int) ownerRepository.count();
		return new OwnerIDDTO(id);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId)
				.map(ownerMapper::ownerToOwnerDTO)
				.map(this::formatDate)
				.map(this::transliteratedOwner)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(ownerNotFound), ownerId)));
	}



	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(ownerNotFound), ownerId)));
		owner.getPet().setName(petName);
		ownerRepository.save(owner);
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		boolean ownerExists = ownerRepository.existsById(ownerId);
		if (!ownerExists) {
			throw new OwnerNotFoundException(String.format(getMessage(ownerNotFound), ownerId));
		} else {
			ownerRepository.deleteById(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll()
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}
	
	@Override
	public Page<OwnerPetInfoDTO> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(Pageable pageable) {
		List<Object[]> result = ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
		List<OwnerPetInfoDTO> ownerPetInfoDTOS = result.stream()
				.map(obj -> new OwnerPetInfoDTO((int) obj[0], (String) obj[1], (String) obj[2], (String) obj[3]))
				.toList();
		return new PageImpl<>(ownerPetInfoDTOS, pageable, ownerPetInfoDTOS.size());
	}

	private String getMessage (String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

	private String formattedLocalDate (LocalDate localDate) {
		return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
				.withLocale(LocaleContextHolder.getLocale())
				.format(localDate);
	}
	private OwnerDTO formatDate(OwnerDTO ownerDTO) {
		PetDTO petDTO = ownerDTO.getPetDTO();
		if (petDTO instanceof DomesticPetDTO domesticPetDTO){
			String formattedDate = formattedLocalDate(domesticPetDTO.getBirthDate());
			domesticPetDTO.setFormattedBirthDate(formattedDate);
		}
		return ownerDTO;
	}

	private OwnerDTO transliteratedOwner (OwnerDTO ownerDTO) {
		String isoCode = LocaleContextHolder.getLocale().getLanguage();
		ownerDTO.setFirstName(transliterationService.transliterate(ownerDTO.getFirstName(), isoCode));
		ownerDTO.setLastName(transliterationService.transliterate(ownerDTO.getLastName(), isoCode));
		ownerDTO.setCity(transliterationService.transliterate(ownerDTO.getCity(), isoCode));
		ownerDTO.setState(transliterationService.transliterate(ownerDTO.getState(), isoCode));

		//Transliterate pet name
		PetDTO petDTO = ownerDTO.getPetDTO();
		String transliteratedName = transliterationService.transliterate(petDTO.getName(), isoCode);
		petDTO.setName(transliteratedName);

		// Transliterate pet birthplace
		if (petDTO instanceof WildPetDTO wildPetDTO){
			String transliteratedPlaceBirth = transliterationService.transliterate(wildPetDTO.getBirthPlace(), isoCode);
			wildPetDTO.setBirthPlace(transliteratedPlaceBirth);
		}
		return ownerDTO;
	}
	
}
