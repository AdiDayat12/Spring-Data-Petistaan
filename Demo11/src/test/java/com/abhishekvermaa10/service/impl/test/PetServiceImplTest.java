package com.abhishekvermaa10.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abhishekvermaa10.repository.PetRepository;
import com.abhishekvermaa10.service.PetService;
import com.abhishekvermaa10.service.impl.PetServiceImpl;

/**
 * @author abhishekvermaa10
 */
@SpringBootTest(classes = PetServiceImpl.class)
class PetServiceImplTest {

	@Autowired
	private PetService petService;
	@MockBean
	private PetRepository petRepository;

	@Test
	void test_FindAverageAgeOfPet_WhenPetsExists_ShouldReturnAverageAge() {
		// Given
		Double expectedAverageAge = 5.0;
		when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.of(expectedAverageAge));
		// When
		Double actualAverageAge = petService.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isCloseTo(expectedAverageAge, withPrecision(0.1));
		verify(petRepository, times(1)).findAverageAgeOfPet();
	}
	
	@Test
	void test_FindAverageAgeOfPet_WhenPetsDoesNotExist_ShouldReturnZero() {
		// Given
		Double expectedAverageAge = 0.0;
		when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.empty());
		// When
		Double actualAverageAge = petService.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isCloseTo(expectedAverageAge, withPrecision(0.1));
		verify(petRepository, times(1)).findAverageAgeOfPet();
	}

}
