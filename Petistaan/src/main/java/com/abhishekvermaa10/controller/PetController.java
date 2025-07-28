package com.abhishekvermaa10.controller;

import com.abhishekvermaa10.dto.ErrorDTO;
import com.abhishekvermaa10.dto.WebResponse;
import com.abhishekvermaa10.dto.AverageAgeDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/petistaan/pets")
@Validated
public class PetController extends BaseController{
    @Autowired
    private PetService petService;

    @Operation(summary = "Find pet", description = "Find an pet by its ID")
    @ApiResponse(responseCode = "200", description = "Pet found")
    @ApiResponse(responseCode = "404", description = "Pet is not found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(path = "/{petId}")
    public ResponseEntity<WebResponse<PetDTO>> getPet (@PathVariable @Min(value = 1, message = "{pet.id.positive}") int petId) throws PetNotFoundException {
        PetDTO petDTO = petService.findPet(petId);
        return success(petDTO, HttpStatus.OK);
    }

    @Operation(summary = "Find average", description = "Find average age of all pets")
    @ApiResponse(responseCode = "200", description = "Returns the average pet age of all registered pets")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(path = "/avg")
    public ResponseEntity<WebResponse<AverageAgeDTO>> getAverageAge () {
        AverageAgeDTO averageAgeDTO = petService.findAverageAgeOfPet();
        return success(averageAgeDTO, HttpStatus.OK);
    }
}
