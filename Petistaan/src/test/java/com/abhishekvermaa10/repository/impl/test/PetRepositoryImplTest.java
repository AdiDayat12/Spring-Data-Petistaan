package com.abhishekvermaa10.repository.impl.test;

import static com.abhishekvermaa10.enums.Gender.F;
import static com.abhishekvermaa10.enums.Gender.M;
import static com.abhishekvermaa10.enums.PetType.BIRD;
import static com.abhishekvermaa10.enums.PetType.CAT;
import static com.abhishekvermaa10.enums.PetType.DOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.abhishekvermaa10.config.TestConfig;
import com.abhishekvermaa10.entity.DomesticPet;
import com.abhishekvermaa10.repository.PetRepository;
import com.abhishekvermaa10.util.TestDataUtil;

/**
 * @author abhishekvermaa10
 */
@EntityScan(basePackages = "com.abhishekvermaa10.entity")
@EnableJpaRepositories(basePackages = "com.abhishekvermaa10.repository")
@ContextConfiguration(classes = { TestConfig.class })
@DataJpaTest
class PetRepositoryImplTest {
	
	@Autowired
	private PetRepository petRepository;
	
	@Test
	void test_FindAverageAgeOfPet_WhenPetsExist_ShouldReturnCorrectAverageAge() {
		// Given
		DomesticPet domesticPet1 = TestDataUtil.createMockDomesticPet("PetName1", M, BIRD, LocalDate.of(2021, 1, 1));
		petRepository.save(domesticPet1);
		DomesticPet domesticPet2 = TestDataUtil.createMockDomesticPet("PetName2", F, CAT, LocalDate.of(2022, 2, 2));
		petRepository.save(domesticPet2);
		DomesticPet domesticPet3 = TestDataUtil.createMockDomesticPet("PetName3", F, DOG, LocalDate.of(2023, 3, 3));
		petRepository.save(domesticPet3);
		double expectedAverageAge = Stream
				.of(domesticPet1.getBirthDate(), domesticPet2.getBirthDate(), domesticPet3.getBirthDate())
				.mapToDouble(birthDate -> Period.between(birthDate, LocalDate.now()).getYears())
				.average()
				.orElse(0.0);
		// When
		Optional<Double> actualAverageAge = petRepository.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isPresent();
		assertThat(actualAverageAge.get()).isCloseTo(expectedAverageAge, withPrecision(0.1));
	}

	@Test
	void test_FindAverageAgeOfPet_WhenNoPetsExist_ShouldReturnEmptyOptional() {
		// When
		Optional<Double> actualAverageAge = petRepository.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isEmpty();
	}

}
