package com.abhishekvermaa10.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.abhishekvermaa10.dto.DomesticPetDTO;
import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.entity.DomesticPet;
import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.entity.WildPet;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.repository.OwnerRepository;
import com.abhishekvermaa10.service.impl.OwnerServiceImpl;
import com.abhishekvermaa10.util.OwnerMapperImpl;

/**
 * @author abhishekvermaa10
 */
@TestPropertySource("classpath:messages.properties")
@SpringBootTest(classes = { OwnerServiceImpl.class, OwnerMapperImpl.class })
class OwnerServiceImplTest {

	@Autowired
	private OwnerServiceImpl ownerServiceImpl;
	@MockitoBean
	private OwnerRepository ownerRepository;

	@Test
	void test_SaveOwner_WhenOwnerDTOIsValid_ShouldSaveOwner() {
		// Given
		OwnerDTO inputOwnerDTO = new OwnerDTO();
		PetDTO inputDomesticPetDTO = new DomesticPetDTO();
		inputOwnerDTO.setPetDTO(inputDomesticPetDTO);
		// When
		ownerServiceImpl.saveOwner(inputOwnerDTO);
		// Then
		verify(ownerRepository, times(1)).save(any(Owner.class));
	}

	@Test
	void test_FindOwner_WhenOwnerExists_ShouldReturnOwnerDTO() throws OwnerNotFoundException {
		// Given
		int inputOwnerId = 1;
		Owner expectedOwner = new Owner();
		Pet expectedDomesticPet = new DomesticPet();
		expectedOwner.setPet(expectedDomesticPet);
		when(ownerRepository.findById(inputOwnerId)).thenReturn(Optional.of(expectedOwner));
		// When
		OwnerDTO actualOwnerDTO = ownerServiceImpl.findOwner(inputOwnerId);
		// Then
		assertThat(actualOwnerDTO).isNotNull();
		verify(ownerRepository, times(1)).findById(inputOwnerId);
	}

	@Test
	void test_FindOwner_WhenOwnerDoesNotExist_ShouldThrowOwnerNotFoundException() {
		// Given
		int inputOwnerId = 1;
		when(ownerRepository.findById(inputOwnerId)).thenReturn(Optional.empty());
		String expectedMessage = String.format("Can't find owner with ownerId %s", inputOwnerId);
		// When & Then
		assertThatThrownBy(() -> ownerServiceImpl.findOwner(inputOwnerId)).isInstanceOf(OwnerNotFoundException.class)
				.hasMessage(expectedMessage);
		verify(ownerRepository, times(1)).findById(inputOwnerId);
	}

	@Test
	void test_UpdatePetDetails_WhenOwnerExists_ShouldUpdatePetDetails() throws OwnerNotFoundException {
		// Given
		int inputOwnerId = 1;
		String inputPetName = "NewPetName";
		Owner expectedOwner = new Owner();
		Pet expectedDomesticPet = new DomesticPet();
		expectedOwner.setPet(expectedDomesticPet);
		String expectedUpdatedPetName = inputPetName;
		when(ownerRepository.findById(inputOwnerId)).thenReturn(Optional.of(expectedOwner));
		// When
		ownerServiceImpl.updatePetDetails(inputOwnerId, inputPetName);
		// Then
		assertThat(expectedOwner.getPet().getName()).isEqualTo(expectedUpdatedPetName);
		verify(ownerRepository, times(1)).findById(inputOwnerId);
		verify(ownerRepository, times(1)).save(expectedOwner);
	}

	@Test
	void test_UpdatePetDetails_WhenOwnerDoesNotExist_ShouldThrowOwnerNotFoundException() {
		// Given
		int inputOwnerId = 1;
		String inputPetName = "NewPetName";
		when(ownerRepository.findById(inputOwnerId)).thenReturn(Optional.empty());
		String expectedMessage = String.format("Can't find owner with ownerId %s", inputOwnerId);
		// When & Then
		assertThatThrownBy(() -> ownerServiceImpl.updatePetDetails(inputOwnerId, inputPetName))
				.isInstanceOf(OwnerNotFoundException.class).hasMessage(expectedMessage);
		verify(ownerRepository, times(1)).findById(inputOwnerId);
	}

	@Test
	void test_DeleteOwner_WhenOwnerExists_ShouldDeleteOwner() throws OwnerNotFoundException {
		// Given
		int inputOwnerId = 1;
		when(ownerRepository.existsById(inputOwnerId)).thenReturn(true);
		// When
		ownerServiceImpl.deleteOwner(inputOwnerId);
		// Then
		verify(ownerRepository, times(1)).existsById(inputOwnerId);
		verify(ownerRepository, times(1)).deleteById(inputOwnerId);
	}

	@Test
	void test_DeleteOwner_WhenOwnerDoesNotExist_ShouldThrowOwnerNotFoundException() {
		// Given
		int inputOwnerId = 1;
		when(ownerRepository.existsById(inputOwnerId)).thenReturn(false);
		String expectedMessage = String.format("Can't find owner with ownerId %s", inputOwnerId);
		// When & Then
		assertThatThrownBy(() -> ownerServiceImpl.deleteOwner(inputOwnerId)).isInstanceOf(OwnerNotFoundException.class)
				.hasMessage(expectedMessage);
		verify(ownerRepository, times(1)).existsById(inputOwnerId);
	}

	@Test
	void test_FindAllOwners_ShouldReturnOwnerDTOList() {
		// Given
		Owner expectedOwner1 = new Owner();
		Pet expectedDomesticPet = new DomesticPet();
		expectedOwner1.setPet(expectedDomesticPet);
		Owner expectedOwner2 = new Owner();
		Pet expectedWildPet = new WildPet();
		expectedOwner2.setPet(expectedWildPet);
		List<Owner> expectedOwnerList = List.of(expectedOwner1, expectedOwner2);
		when(ownerRepository.findAll()).thenReturn(expectedOwnerList);
		// When
		List<OwnerDTO> actualOwnerDTOList = ownerServiceImpl.findAllOwners();
		// Then
		assertThat(actualOwnerDTOList).hasSize(expectedOwnerList.size());
		verify(ownerRepository, times(1)).findAll();
	}

	@Test
	void test_FindIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners_ShouldReturnOwnerDetails() {
		// Given
		int inputPageNumber = 1;
		int inputNumberOfRecordsPerPage = 2;
		Pageable pageable = PageRequest.of(inputPageNumber, inputNumberOfRecordsPerPage);
		Object[] expectedRow = { 3, "FirstName3", "LastName3", "PetName3" };
		List<Object[]> expectedDetailsList = List.<Object[]>of(expectedRow);
		when(ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable)).thenReturn(expectedDetailsList);
		// When
		List<Object[]> actualDetailsList = ownerServiceImpl
				.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(inputPageNumber, inputNumberOfRecordsPerPage);
		// Then
		assertThat(actualDetailsList).isEqualTo(expectedDetailsList);
		verify(ownerRepository, times(1)).findIdAndFirstNameAndLastNameAndPetName(pageable);
	}

}
