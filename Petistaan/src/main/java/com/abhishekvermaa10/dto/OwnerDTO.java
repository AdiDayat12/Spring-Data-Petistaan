package com.abhishekvermaa10.dto;

import com.abhishekvermaa10.enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.PropertySource;

/**
 * @author abhishekvermaa10
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OwnerDTO {

	@EqualsAndHashCode.Include
	@Schema(description = "Unique ID of the owner")
	private int id;
	@Size(max = 255, message = "{owner.first.name.length}")
	@NotBlank(message = "{owner.first.name.required}")
	@Schema(description = "First name of the owner")
	private String firstName;
	@Size(max = 255, message = "{owner.last.name.length}")
	@NotBlank(message = "{owner.last.name.required}")
	@Schema(description = "Last name of the owner")
	private String lastName;
	@NotNull(message = "{owner.gender.required}")
	@Schema(description = "Gender of the owner")
	private Gender gender;
	@Size(max = 255, message = "{owner.city.length}")
	@NotBlank(message = "{owner.city.required}")
	@Schema(description = "City of the owner")
	private String city;
	@Size(max = 255, message = "{owner.state.length}")
	@NotBlank(message = "{owner.state.required}")
	@Schema(description = "State of the owner")
	private String state;
	@EqualsAndHashCode.Include
	@Size(min = 10, max = 10, message = "{owner.mobile.number.length}")
	@NotBlank(message = "{owner.mobile.number.required}")
	@Schema(description = "Mobile number of the owner")
	private String mobileNumber;
	@EqualsAndHashCode.Include
	@Email(message = "{owner.email.invalid}")
	@NotBlank(message = "{owner.email.required}")
	@Schema(description = "Email id of the owner")
	private String emailId;
	@Valid
	@NotNull(message = "{owner.pet.required}")
	@Schema(description = "Pet details of the owner")
	private PetDTO petDTO;

}
