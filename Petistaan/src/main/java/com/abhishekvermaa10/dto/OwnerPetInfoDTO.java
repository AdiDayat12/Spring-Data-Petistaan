package com.abhishekvermaa10.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerPetInfoDTO {
    @Schema(description = "Unique ID of owner")
    private int ownerId;
    @Schema(description = "Firstname of Owner")
    private String firstName;
    @Schema(description = "Lastname of Owner")
    private String lastName;
    @Schema(description = "Pet name")
    private String petName;
}
