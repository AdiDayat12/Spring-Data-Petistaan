package com.abhishekvermaa10.util;

import com.abhishekvermaa10.dto.DomesticPetDTO;
import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.dto.WildPetDTO;
import com.abhishekvermaa10.entity.DomesticPet;
import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.entity.WildPet;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@Deprecated
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OwnerMapperUtil {

	private static final String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";

	public static Owner ownerDTOToOwner(OwnerDTO ownerDTO) {
		Owner owner = new Owner();
		owner.setFirstName(ownerDTO.getFirstName());
		owner.setLastName(ownerDTO.getLastName());
		owner.setGender(ownerDTO.getGender());
		owner.setCity(ownerDTO.getCity());
		owner.setState(ownerDTO.getState());
		owner.setMobileNumber(ownerDTO.getMobileNumber());
		owner.setEmailId(ownerDTO.getEmailId());
		Pet pet = petDTOToPet(ownerDTO.getPetDTO());
		owner.setPet(pet);
		return owner;
	}

	private static Pet petDTOToPet(PetDTO petDTO) {
		return switch (petDTO) {
		case DomesticPetDTO domesticPetDTO -> domesticPetDTOToDomesticPet(domesticPetDTO);
		case WildPetDTO wildPetDTO -> wildPetDTOToWildPet(wildPetDTO);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, petDTO.getClass()));
		};
	}
	
	private static DomesticPet domesticPetDTOToDomesticPet(DomesticPetDTO domesticPetDTO) {
		DomesticPet domesticPet = new DomesticPet();
		domesticPet.setName(domesticPetDTO.getName());
		domesticPet.setGender(domesticPetDTO.getGender());
		domesticPet.setType(domesticPetDTO.getType());
		domesticPet.setBirthDate(domesticPetDTO.getBirthDate());
		return domesticPet;
	}
	
	private static WildPet wildPetDTOToWildPet(WildPetDTO wildPetDTO) {
		WildPet wildPet = new WildPet();
		wildPet.setName(wildPetDTO.getName());
		wildPet.setGender(wildPetDTO.getGender());
		wildPet.setType(wildPetDTO.getType());
		wildPet.setBirthPlace(wildPetDTO.getBirthPlace());
		return wildPet;
	}

	public static OwnerDTO ownerToOwnerDTO(Owner owner) {
		PetDTO petDTO = petToPetDTOWithoutOwner(owner.getPet());
		return OwnerDTO.builder()
				.id(owner.getId())
				.firstName(owner.getFirstName())
				.lastName(owner.getLastName())
				.gender(owner.getGender())
				.city(owner.getCity())
				.state(owner.getState())
				.mobileNumber(owner.getMobileNumber())
				.emailId(owner.getEmailId())
				.petDTO(petDTO)
				.build();
	}

	private static PetDTO petToPetDTOWithoutOwner(Pet pet) {
		return switch (pet) {
		case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}
	
	private static DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet) {
		return DomesticPetDTO.builder()
				.id(domesticPet.getId())
				.name(domesticPet.getName())
				.gender(domesticPet.getGender())
				.type(domesticPet.getType())
				.birthDate(domesticPet.getBirthDate())
				.build();
	}
	
	private static WildPetDTO wildPetToWildPetDTO(WildPet wildPet) {
		return WildPetDTO.builder()
				.id(wildPet.getId())
				.name(wildPet.getName())
				.gender(wildPet.getGender())
				.type(wildPet.getType())
				.birthPlace(wildPet.getBirthPlace())
				.build();
	}

}
