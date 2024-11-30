package com.abhishekvermaa10.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputUtil {

	public static int acceptMenuOption(Scanner scanner) {
		System.out.println("Press 1 to fetch owner details.");
		System.out.println("Press 2 to fetch pet details.");
		System.out.println("Press 3 to fetch owner by initials of first name of owner.");
		System.out.println("Press 4 to fetch owner details by pet id.");
		System.out.println("Press 5 to fetch owner details whose pets born within a time period.");
		System.out.println("Press 6 to find average age of pet.");
		System.out.println("Press 7 to find specific details using pagination.");
		int menuOption = scanner.nextInt();
		if (menuOption >= 1 && menuOption <= 7) {
			return menuOption;
		} else {
			System.out.println("Invalid option entered.");
			return acceptMenuOption(scanner);
		}
	}

	public static boolean wantToContinue(Scanner scanner) {
		System.out.println("Press Y to continue and N to exit.");
		char choice = scanner.next().toUpperCase().charAt(0);
		return 'Y' == choice;
	}

	public static int acceptOwnerIdToOperate(Scanner scanner) {
		System.out.println("Enter id of owner:");
		return scanner.nextInt();
	}

	public static int acceptPetIdToOperate(Scanner scanner) {
		System.out.println("Enter id of pet:");
		return scanner.nextInt();
	}

	public static String acceptOwnerInitialsToOperate(Scanner scanner) {
		System.out.println("Enter initials of first name of owner:");
		return scanner.next();
	}
	
	public static LocalDate acceptFromPetBirthDateToOperate(Scanner scanner) {
		System.out.println("Enter start date of birth of pet (dd-MM-yyyy):");
		return convertStringToDate(scanner.next());
	}

	public static LocalDate acceptToPetBirthDateToOperate(Scanner scanner) {
		System.out.println("Enter end date of birth of pet (dd-MM-yyyy):");
		return convertStringToDate(scanner.next());
	}

	public static int acceptPageSizeToOperate(Scanner scanner) {
		System.out.println("Enter page size:");
		int pageSize = scanner.nextInt();
		if (pageSize > 0) {
			return pageSize;
		} else {
			System.out.println("Page size must be greater than 0.");
			return acceptPageSizeToOperate(scanner);
		}
	}

	public static int acceptPageNumberToOperate(Scanner scanner) {
		System.out.println("Enter page number:");
		int pageNumber = scanner.nextInt();
		if (pageNumber > 0) {
			return pageNumber;
		} else {
			System.out.println("Page number must be greater than 0.");
			return acceptPageNumberToOperate(scanner);
		}
	}

	private static LocalDate convertStringToDate(String stringDate) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(stringDate, format);
	}

}
