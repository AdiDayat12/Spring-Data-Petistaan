package com.abhishekvermaa10.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePetDTO {
    @Min(value = 1, message = "{owner.id.positive}")
    @Schema(description = "Owner unique ID")
    private int ownerId;
    @NotBlank(message = "{owner.first.name.required")
    @Size(max = 255, message = "{owner.first.name.length}")
    @Schema(description = "The new pet's name")
    private String petName;
}
