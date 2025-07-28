package com.abhishekvermaa10.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AverageAgeDTO {
    @Schema(description = "The average age of all pets")
    private double average;
}
