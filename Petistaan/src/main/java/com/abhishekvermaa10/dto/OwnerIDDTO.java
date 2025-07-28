package com.abhishekvermaa10.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerIDDTO {
    @Schema(description = "The unique ID that owner gets once they are registered")
    private int ownerID;
}
