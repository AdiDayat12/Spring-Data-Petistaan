package com.abhishekvermaa10;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.service.PetService;
import com.abhishekvermaa10.util.InputUtil;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class Demo implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);
	private final OwnerService ownerService;
	private final PetService petService;

	public static void main(String[] args) {
		SpringApplication.run(Demo.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				System.out.println("Welcome to Petistaan");
				int menuOption = InputUtil.acceptMenuOption(scanner);
				switch (menuOption) {
				case 1:
					int ownerId = InputUtil.acceptOwnerIdToOperate(scanner);
					OwnerDTO ownerDTO = ownerService.findOwner(ownerId);
					System.out.println(String.format("Found owner with ownerId %s.", ownerId));
					System.out.println(ownerDTO);
					break;
				case 2:
					int petId = InputUtil.acceptPetIdToOperate(scanner);
					PetDTO petDTO = petService.findPet(petId);
					System.out.println(String.format("Found pet with petId %s.", petId));
					System.out.println(petDTO);
					break;
				case 3:
					String firstName = InputUtil.acceptOwnerInitialsToOperate(scanner);
					List<OwnerDTO> ownerDTOList = ownerService.findAllOwnersByFirstNameInitials(firstName);
					System.out.println(String.format("There are %s owners whose firstName starts with %s.",
							ownerDTOList.size(), firstName));
					ownerDTOList.forEach(System.out::println);
					break;
				case 4:
					petId = InputUtil.acceptPetIdToOperate(scanner);
					ownerDTO = ownerService.findOwnerByPetId(petId);
					System.out.println(String.format("Found owner with petId %s.", petId));
					System.out.println(ownerDTO);
					break;
				default:
					System.out.println("Invalid option entered.");
				}
			} while (InputUtil.wantToContinue(scanner));
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage(), exception);
		}
	}

}
