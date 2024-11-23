package com.abhishekvermaa10;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.util.InputUtil;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@SpringBootApplication
public class Demo implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);
	private final OwnerService ownerService;

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
					List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
					System.out.println(String.format("There are %s owners.", ownerDTOList.size()));
					ownerDTOList.forEach(System.out::println);
					break;
				case 2:
					String sortingParameter = InputUtil.acceptSortingParameter(scanner);
					boolean sortDescending = InputUtil.wantToSortByDescending(scanner);
					ownerDTOList = ownerService.findAllSortedOwners(sortingParameter, sortDescending);
					System.out.println(
							String.format("There are %s owners sorted by %s.", ownerDTOList.size(), sortingParameter));
					ownerDTOList.forEach(System.out::println);
					break;
				case 3:
					int pageNumber = InputUtil.acceptPageNumberToOperate(scanner);
					int pageSize = InputUtil.acceptPageSizeToOperate(scanner);
					ownerDTOList = ownerService.findAllPaginatedOwners(pageNumber-1, pageSize);
					System.out.println(
							String.format("Showing %s records on page number %s.", ownerDTOList.size(), pageNumber));
					ownerDTOList.forEach(System.out::println);
					break;
				case 4:
					sortingParameter = InputUtil.acceptSortingParameter(scanner);
					sortDescending = InputUtil.wantToSortByDescending(scanner);
					pageNumber = InputUtil.acceptPageNumberToOperate(scanner);
					pageSize = InputUtil.acceptPageSizeToOperate(scanner);
					ownerDTOList = ownerService.findAllPaginatedAndSortedOwners(pageNumber-1, pageSize, sortingParameter,
							sortDescending);
					System.out.println(String.format("Showing %s records on page number %s sorted by %s.",
							ownerDTOList.size(), pageNumber, sortingParameter));
					ownerDTOList.forEach(System.out::println);
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
