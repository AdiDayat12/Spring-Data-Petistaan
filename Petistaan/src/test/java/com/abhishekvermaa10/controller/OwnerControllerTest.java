package com.abhishekvermaa10.controller;

import com.abhishekvermaa10.controller.advice.GlobalExceptionHandler;
import com.abhishekvermaa10.dto.*;
import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.service.OwnerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = OwnerController.class)
@Import({GlobalExceptionHandler.class})
@ActiveProfiles("test")
class OwnerControllerTest {

    @MockitoBean
    private OwnerService ownerService;
    @Autowired
    private MockMvc mockMvc;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @MockitoBean
    private MessageSource messageSource;

    @BeforeAll
    public static void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }
    String contextPath = "/petistaan/owners";

    // Post Method
    @Nested
    class CreateOwner {
        // Invalid request
        @Test
        void whenInvalid_thenBadRequest () throws Exception {
            Path path = Paths.get(getClass().getClassLoader().getResource("inputs/ownerDTO_invalid_input.json").toURI());
            String input = Files.readString(path);

            mockMvc.perform(post(contextPath)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(input))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").isNotEmpty());
        }

        // Valid request
        @Test
        void whenSuccess_thenReturnCreated() throws Exception {
            Path path = Paths.get(getClass().getClassLoader().getResource("inputs/ownerDTO_valid_input.json").toURI());
            String input = Files.readString(path);
            // Setup mock
            when(ownerService.saveOwner(any())).thenReturn(new OwnerIDDTO(3));

            // Perform request
            mockMvc.perform(post(contextPath)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(input))
                    .andExpect(status().isCreated())
                    .andDo(result -> {
                        WebResponse<OwnerIDDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                                new TypeReference<WebResponse<OwnerIDDTO>>() {
                                });
                        assertEquals(3, response.getData().getOwnerID());
                    });
        }
    }


    // Get Method
    @Nested
    class GetOwner {
        // Owner Found
        @Test
        void whenDataFound_thenReturnOwnerDTO () throws Exception {
            Path path = Paths.get(getClass().getClassLoader().getResource("inputs/ownerDTO_valid_input.json").toURI());
            String input = Files.readString(path);
            OwnerDTO ownerDTO = objectMapper.readValue(input, OwnerDTO.class);
            when(ownerService.findOwner(1)).thenReturn(ownerDTO);

            mockMvc.perform(
                            get(contextPath + "/{ownerId}", 1)
                    )
                    .andExpect(status().isOk())
                    .andDo(result -> {
                        WebResponse<OwnerDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                                new TypeReference<WebResponse<OwnerDTO>>() {
                                });
                        assertNotNull(response);
                        assertEquals("NewFirstName", response.getData().getFirstName());
                    });
        }

        // Owner is not found
        @Test
        void whenOwnerNotFound_thenThrow () throws Exception {

            when(ownerService.findOwner(1)).thenThrow(OwnerNotFoundException.class);

            mockMvc.perform(get(contextPath + "/{ownerId}", 1))
                    .andExpect(status().isNotFound());

        }

        @Test
        void whenIdIsNotPositive_thenThrow () throws Exception {
            mockMvc.perform(
                    get(contextPath + "/{ownerId}", -1)
            )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.message").isNotEmpty());
        }
    }

    // Patch Method
    @Nested
    class update {
        // Update success
        @Test
        void whenSuccess_thenReturnOK () throws Exception {
            UpdatePetDTO updatePetDTO = new UpdatePetDTO(1, "Siuuuu");
            doNothing().when(ownerService).updatePetDetails(updatePetDTO.getOwnerId(), updatePetDTO.getPetName());

            mockMvc.perform(
                    patch(contextPath)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatePetDTO))
            ).andExpect(
                    status().isOk()
            );
            verify(ownerService, times(1)).updatePetDetails(1, "Siuuuu");
        }

        // Update failed
        @Test
        void whenFailed_thenReturnOwnerNotFound () throws Exception {
            UpdatePetDTO updatePetDTO = new UpdatePetDTO(1, "siuuu");
            doThrow(OwnerNotFoundException.class).when(ownerService).updatePetDetails(1, "siuuu");
            mockMvc.perform(patch(contextPath)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatePetDTO)))
                            .andExpect(status().isNotFound());
        }

    }

    // Delete Method
    @Nested
    class Delete {
        // Owner is not found
        @Test
        void whenOwnerNotFound_thenThrow () throws Exception {
            doThrow(OwnerNotFoundException.class).when(ownerService).deleteOwner(1);

            mockMvc.perform(delete( contextPath + "/{ownerId}", 1))
                    .andExpect(status().isNotFound());
        }
        @Test
        void whenSuccess_thenReturnOK () throws Exception {
            doNothing().when(ownerService).deleteOwner(1);
            mockMvc.perform(delete( contextPath+ "/{ownerId}", 1))
                    .andExpect(status().isOk());
            verify(ownerService, times(1)).deleteOwner(1);
        }
    }

    @Nested
    class PageableTest{

        @Test
        void getAllOwner () throws Exception {
            List<OwnerDTO> ownerDTOS = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                OwnerDTO ownerDTO = new OwnerDTO();
                ownerDTO.setFirstName("NewFirstName" + i);
                ownerDTO.setLastName("NewLastName" + i);
                ownerDTO.setGender(Gender.M);
                ownerDTO.setCity("NewCity" + i);
                ownerDTO.setState("NewState" + i);
                ownerDTO.setMobileNumber("900900900" + i);
                ownerDTO.setEmailId("newfirstname.newlastname" + i + "@scaleupindia.com");

                PetDTO petDTO = new DomesticPetDTO();
                petDTO.setName("NewPetName" + i);
                petDTO.setGender(Gender.M);
                petDTO.setType(PetType.DOG);
                ((DomesticPetDTO) petDTO).setBirthDate(LocalDate.parse("2021-01-01"));

                ownerDTO.setPetDTO(petDTO);

                ownerDTOS.add(ownerDTO);
            }
            when(ownerService.findAllOwners()).thenReturn(ownerDTOS);

            mockMvc.perform(get(contextPath + "/all"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data.size()").value(5));
            verify(ownerService, times(1)).findAllOwners();
        }

        @Test
        void getDetails () throws Exception {
            List<OwnerPetInfoDTO> infoDTOS = new ArrayList<>();
            for (int i = 1; i < 6; i++) {
                OwnerPetInfoDTO ownerPetInfoDTO = new OwnerPetInfoDTO(i, "owner", " " + i, "pet " + i);
                infoDTOS.add(ownerPetInfoDTO);
            }
            Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());
            Page<OwnerPetInfoDTO> ownerPetInfoDTOS = new PageImpl<>(infoDTOS, pageable, 5);

            when(ownerService.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(any(Pageable.class)))
                    .thenReturn(ownerPetInfoDTOS);

            mockMvc.perform(get(contextPath + "/page")
                            .param("page", "0")
                            .param("size", "2")
                            .param("sortBy", "id") )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isNotEmpty());
            verify(ownerService, times(1))
                    .findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(pageable);
        }
    }

}
