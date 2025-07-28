package com.abhishekvermaa10.controller;

import com.abhishekvermaa10.controller.advice.GlobalExceptionHandler;
import com.abhishekvermaa10.dto.AverageAgeDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = {PetController.class})
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class PetControllerTest {
    @MockitoBean
    private PetService petService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        when(messageSource.getMessage(eq("api.response.success"), any(), any()))
                .thenReturn("success");
    }

    @Test
    void whenPetFound_thenReturnOwnerPetInfo () throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("inputs/petDTO.json").toURI());
        String input = Files.readString(path);
        PetDTO petDTO = objectMapper.readValue(input, PetDTO.class);

        when(petService.findPet(1)).thenReturn(petDTO);

        mockMvc.perform(
                get("/petistaan/pets/{petId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.message").value("success")
        ).andExpect(jsonPath("$.data.name").value("Tiger"));
        verify(petService, times(1)).findPet(1);
    }

    @Test
    void whenPetNotFound_thenReturnPetNotFound() throws Exception {
        doThrow(PetNotFoundException.class).when(petService).findPet(100);

        mockMvc.perform(
                get("/petistaan/pets/{petId}", 100)
        ).andExpect(
                status().isNotFound()
        );
        verify(petService, times(1)).findPet(100);
    }

    @Test
    void findAverage () throws Exception {
        when(petService.findAverageAgeOfPet()).thenReturn(new AverageAgeDTO(2.2));

        mockMvc.perform(
                get("/petistaan/pets/avg")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.average").value(2.2));
    }
}