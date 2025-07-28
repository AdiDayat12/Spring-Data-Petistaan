package com.abhishekvermaa10.dto;

import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author abhishekvermaa10
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "category")
@JsonSubTypes({ @JsonSubTypes.Type(value = DomesticPetDTO.class, name = "Domestic"),
		@JsonSubTypes.Type(value = WildPetDTO.class, name = "Wild") })
public abstract class PetDTO {

	@EqualsAndHashCode.Include
	@Schema(description = "Unique ID of the pet")
	private int id;
	@Schema(description = "Name of pet")
	@NotBlank(message = "{pet.name.required}")
	@Size(max = 255, message = "{pet.name.length}")
	private String name;
	@Schema(description = "Gender of pet")
	@NotNull(message = "{pet.gender.required}")
	private Gender gender;
	@NotNull(message = "{pet.type.required}")
	@Schema(description = "Type of pet")
	private PetType type;
	@Schema(description = "Owner of the pet")
	private OwnerDTO ownerDTO;

}
