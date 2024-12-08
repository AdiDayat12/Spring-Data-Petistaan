package com.abhishekvermaa10.util;

import java.util.ArrayList;
import java.util.List;
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
		System.out.println("Press 2 to update pet details of owner.");
		System.out.println("Press 3 to update pet details of owner with new repository method.");
		System.out.println("Press 4 to delete owner details.");
		System.out.println("Press 5 to delete multiple owner details with new repository method.");
		System.out.println("Press 6 to delete multiple owner details.");
		int menuOption = scanner.nextInt();
		if (menuOption >= 1 && menuOption <= 6) {
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

	public static String acceptPetDetailsToUpdate(Scanner scanner) {
		System.out.println("Enter updated name of pet:");
		return scanner.next();
	}
	
	public static List<Integer> acceptOwnerIdsToOperate(Scanner scanner) {
		List<Integer> ownerIds = new ArrayList<>();
		do {
			System.out.println("Enter id of owner:");
			ownerIds.add(scanner.nextInt());
		} while (wantToContinue(scanner));
		return ownerIds;
	}

}
