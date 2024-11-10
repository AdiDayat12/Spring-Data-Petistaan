package com.abhishekvermaa10.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.abhishekvermaa10.dto.DomesticPetDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.dto.WildPetDTO;
import com.abhishekvermaa10.entity.DomesticPet;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.entity.WildPet;

/**
 * @author abhishekvermaa10
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {

	String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";

	default PetDTO petToPetDTO(Pet pet) {
		return switch (pet) {
		case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}

	@Mapping(target = "ownerDTO.petDTO", ignore = true)
	@Mapping(source = "owner", target = "ownerDTO")
	DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet);

	@Mapping(target = "ownerDTO.petDTO", ignore = true)
	@Mapping(source = "owner", target = "ownerDTO")
	WildPetDTO wildPetToWildPetDTO(WildPet wildPet);

}
