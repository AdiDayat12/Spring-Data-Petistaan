package com.abhishekvermaa10.controller;

import com.abhishekvermaa10.dto.*;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/petistaan/owners")
@Validated
@Tag(name = "Owner", description = "Endpoints related to owner management")
public class OwnerController extends BaseController{
    @Autowired
    private OwnerService ownerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new owner", description = "Create owner along with their pet")
    @ApiResponse(responseCode = "201", description = "Owner created successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<WebResponse<OwnerIDDTO>> save (@Valid @RequestBody OwnerDTO ownerDTO) {
        OwnerIDDTO ownerIDDTO = ownerService.saveOwner(ownerDTO);
        return success(ownerIDDTO, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find owner", description = "Find an owner by its ID")
    @ApiResponse(responseCode = "200", description = "Owner found")
    @ApiResponse(responseCode = "404", description = "Owner is not found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<WebResponse<OwnerDTO>> findOwner (@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId) throws OwnerNotFoundException {
        OwnerDTO ownerDTO = ownerService.findOwner(ownerId);
        return success(ownerDTO, HttpStatus.OK);
    }


    @Operation(summary = "Update pet", description = "Update pet's name")
    @ApiResponse(responseCode = "200", description = "Pet updated successfully")
    @ApiResponse(responseCode = "404", description = "Pet is not found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> update (@RequestBody UpdatePetDTO updatePetDTO) throws OwnerNotFoundException {
        ownerService.updatePetDetails(updatePetDTO.getOwnerId(), updatePetDTO.getPetName());
        return this.<Void>success(null, HttpStatus.OK);
    }

    @Operation(summary = "Delete owner", description = "Delete owner by its ID")
    @ApiResponse(responseCode = "200", description = "Owner deleted successfully")
    @ApiResponse(responseCode = "404", description = "Owner is not found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @DeleteMapping(path = "/{ownerId}")
    public ResponseEntity<WebResponse<Void>> deleteOwner (@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId) throws OwnerNotFoundException {
        ownerService.deleteOwner(ownerId);
        return this.<Void>success(null, HttpStatus.OK);
    }

    @Operation(summary = "Get all owner", description = "Get all registered owner")
    @ApiResponse(responseCode = "200", description = "Return list of all owner")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(path = "/all")
    public ResponseEntity<WebResponse<List<OwnerDTO>>> getAllOwner () {
        List<OwnerDTO> allOwner = ownerService.findAllOwners();
        return success(allOwner, HttpStatus.OK);
    }

    @Operation(summary = "Get paginated owner", description = "Returns a paginated and sortable list of all registered owners.")
    @ApiResponse(responseCode = "200", description = "Returns paginated owners")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(path = "/page")
    public ResponseEntity<WebResponse<Page<OwnerPetInfoDTO>>> findDetails (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OwnerPetInfoDTO> petInfoDTOS = ownerService.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(pageable);

        return success(petInfoDTOS, HttpStatus.OK);

    }

}
