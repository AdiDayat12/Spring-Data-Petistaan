package com.abhishekvermaa10.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.entity.DomesticPet;
import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.repository.PetRepository;
import com.abhishekvermaa10.service.impl.PetServiceImpl;
import com.abhishekvermaa10.util.PetMapperImpl;

/**
 * @author abhishekvermaa10
 */
@TestPropertySource("classpath:messages.properties")
@SpringBootTest(classes = { PetServiceImpl.class, PetMapperImpl.class })
class PetServiceImplTest {

	@Autowired
	private PetServiceImpl petServiceImpl;
	@MockitoBean
	private PetRepository petRepository;

	@Test
	void test_FindPet_WhenPetExists_ShouldReturnPetDTO() throws PetNotFoundException {
		// Given
		int inputPetId = 1;
		Pet expectedDomesticPet = new DomesticPet();
		Owner expectedOwner = new Owner();
		expectedDomesticPet.setOwner(expectedOwner);
		when(petRepository.findById(inputPetId)).thenReturn(Optional.of(expectedDomesticPet));
		// When
		PetDTO actualPetDTO = petServiceImpl.findPet(inputPetId);
		// Then
		assertThat(actualPetDTO).isNotNull();
		verify(petRepository, times(1)).findById(inputPetId);
	}

	@Test
	void test_FindPet_WhenOwnerDoesNotExist_ShouldThrowPetNotFoundException() {
		// Given
		int inputPetId = 1;
		when(petRepository.findById(inputPetId)).thenReturn(Optional.empty());
		String expectedMessage = String.format("Can't find pet with petId %s", inputPetId);
		// When & Then
		assertThatThrownBy(() -> petServiceImpl.findPet(inputPetId)).isInstanceOf(PetNotFoundException.class)
				.hasMessage(expectedMessage);
		verify(petRepository, times(1)).findById(inputPetId);
	}

	@Test
	void test_FindAverageAgeOfPet_WhenPetsExists_ShouldReturnAverageAge() {
		// Given
		Double expectedAverageAge = 5.0;
		when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.of(expectedAverageAge));
		// When
		Double actualAverageAge = petServiceImpl.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isEqualTo(expectedAverageAge);
		verify(petRepository, times(1)).findAverageAgeOfPet();
	}

	@Test
	void test_FindAverageAgeOfPet_WhenPetsDoesNotExist_ShouldReturnZero() {
		// Given
		Double expectedAverageAge = 0.0;
		when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.empty());
		// When
		Double actualAverageAge = petServiceImpl.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isEqualTo(expectedAverageAge);
		verify(petRepository, times(1)).findAverageAgeOfPet();
	}

}
