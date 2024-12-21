package com.abhishekvermaa10.repository.impl.test;

import static com.abhishekvermaa10.enums.Gender.M;
import static com.abhishekvermaa10.enums.PetType.BIRD;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.abhishekvermaa10.config.TestConfig;
import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.repository.OwnerRepository;
import com.abhishekvermaa10.util.TestDataUtil;

/**
 * @author abhishekvermaa10
 */
@EntityScan(basePackages = "com.abhishekvermaa10.entity")
@EnableJpaRepositories(basePackageClasses = OwnerRepository.class)
@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
class OwnerRepositoryImplTest {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void test_FindIdAndFirstNameAndLastNameAndPetName_ShouldReturnOwnerDetails_WhenOwnersExist() {
		// Given
		Owner owner1 = TestDataUtil.createMockOwnerWithMockDomesticPet("FirstName1", "LastName1", M, "City1", "State1",
				"9876543211", "firstname1.lastname1@scaleupindia.com", "PetName1", M, BIRD, LocalDate.of(2021, 1, 1));
		ownerRepository.save(owner1);
		Owner owner2 = TestDataUtil.createMockOwnerWithMockDomesticPet("FirstName2", "LastName2", M, "City2", "State2",
				"9876543212", "firstname2.lastname2@scaleupindia.com", "PetName2", M, BIRD, LocalDate.of(2022, 2, 2));
		ownerRepository.save(owner2);
		Owner owner3 = TestDataUtil.createMockOwnerWithMockDomesticPet("FirstName3", "LastName3", M, "City3", "State3",
				"9876543213", "firstname3.lastname3@scaleupindia.com", "PetName3", M, BIRD, LocalDate.of(2023, 3, 3));
		ownerRepository.save(owner3);
		Pageable pageable = PageRequest.of(1, 2);
		// When
		List<Object[]> actualDetailsList = ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
		// Then
		assertThat(actualDetailsList).isNotEmpty();
		assertThat(actualDetailsList.get(0)).isNotNull().hasSize(4).containsExactly(owner3.getId(), "FirstName3",
				"LastName3", "PetName3");
	}

	@Test
	void test_FindIdAndFirstNameAndLastNameAndPetName_WhenNoOwnersExist_ShouldReturnEmptyList() {
		// Given
		Pageable pageable = PageRequest.of(1, 2);
		// When
		List<Object[]> actualDetailsList = ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
		// Then
		assertThat(actualDetailsList).isEmpty();
	}

}
