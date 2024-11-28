package com.abhishekvermaa10.util;

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
		int menuOption = scanner.nextInt();
		if (menuOption >= 1 && menuOption <= 4) {
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

}
