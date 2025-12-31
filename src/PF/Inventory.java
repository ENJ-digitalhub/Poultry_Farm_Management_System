package PF;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Inventory {
	static Scanner read = new Scanner(System.in);
	static SystemUtils tools = new SystemUtils();
	static LocalDateTime time = LocalDateTime.now();

	public static void stockMenu(Runnable homeCallBack) {
		System.out.println("\n" + "=".repeat(50) + "\n");
		System.out.println(tools.center("STOCK MANAGEMENT MENU", 50));
		System.out.println("\n" + "=".repeat(50) + "\n");
		System.out.print("1. Add New Birds to Stock\n2. Remove Birds from Stock\n3. Record Feed Stock\n4. View Current Stock\n0. Back\nOption: ");

		int option = -1;
		while (true) {
			try {
				option = read.nextInt();
				read.nextLine().trim();
				break;
			} catch (Exception e) {
				System.out.println("Invalid input");
				read.nextLine().trim();
				System.out.print("Option: ");
			}
		}

		tools.clearScreen();
		switch (option) {
			case 0 -> homeCallBack.run();
			case 1 -> addBird(homeCallBack);
			case 2 -> removeBird(homeCallBack);
			case 3 -> addFeed(homeCallBack);
			case 4 -> viewStock(homeCallBack);
			default -> {
				System.out.println("Invalid selection");
				stockMenu(homeCallBack);
			}
		}
	}
	public static void addBird(Runnable homeCallBack) {
		System.out.println("--- Add Birds ---");
		
		int birdNo = tools.getPositiveIntInput("Input number of Bird to add: ");
		if (!tools.confirm(birdNo)) { 
			tools.clearScreen();
			stockMenu(homeCallBack); 
			return; 
		}

		// Get current inventory
		Object[][] inventory = tools.getAllRecords("inventory");
		
		if (inventory.length == 0) {
			// First time - create initial inventory
			// Let's assume columns: inventory_id(auto), bird_no, eggs_no, broken_eggs, feeds_no, created_at(auto)
			// So we need to provide 3 values
			Object[] values = {birdNo, 0, 0, 0.0}; // bird_no, broken_eggs, eggs_no, feeds_no
			tools.addRecord("inventory", values);
			System.out.println("\nInitial inventory created with " + birdNo + " birds.");
		} else {
			// Update existing inventory
			Object[] latest = inventory[inventory.length - 1];
			int currentBirds = Integer.parseInt(latest[1].toString());
			int currentEggs = Integer.parseInt(latest[2].toString());
			int currentBrokenEggs  = Integer.parseInt(latest[3].toString());
			double currentFeed = Double.parseDouble(latest[4].toString());
			
			int newBirdCount = currentBirds + birdNo;
			Object[] values = {newBirdCount, currentEggs,currentBrokenEggs, currentFeed};
			String[] columns = {"bird_no", "eggs_no","broken_eggs", "feeds_no"};
			
			tools.addRecord("inventory", values);
			System.out.println("\nAdded " + birdNo + " birds. Total birds: " + newBirdCount);
		}
		
		System.out.println("Press ENTER to return...");
		read.nextLine().trim();
		tools.clearScreen();
		stockMenu(homeCallBack);
	} 
	public static void removeBird(Runnable homeCallBack) {
		int newBirdCount=0;
		System.out.println("--- Remove Birds ---");
		
		int birdNo = tools.getPositiveIntInput("Input number of Bird to remove: ");
		if (!tools.confirm(birdNo)) { 
			tools.clearScreen();
			stockMenu(homeCallBack); 
			return; 
		}

		Object[][] inventory = tools.getAllRecords("inventory");
		if (inventory.length == 0) {
			// First time - create initial inventory
			// Let's assume columns: inventory_id(auto), bird_no, eggs_no, broken_eggs, feeds_no, created_at(auto)
			// So we need to provide 3 values
			Object[] values = {0, 0, 0, 0.0}; // bird_no, broken_eggs, eggs_no, feeds_no
			tools.addRecord("inventory", values);
			System.out.println("\nInitial inventory created with " + 0 + " birds.");
		} else {
			// Update existing inventory
			Object[] latest = inventory[inventory.length - 1];
			int currentBirds = Integer.parseInt(latest[1].toString());
			int currentEggs = Integer.parseInt(latest[2].toString());
			int currentBrokenEggs  = Integer.parseInt(latest[3].toString());
			double currentFeed = Double.parseDouble(latest[4].toString());
		
			if (birdNo > currentBirds) {
				System.out.println("Not enough birds to remove. Total: " + currentBirds);
				System.out.println("Press ENTER to return...");
				read.nextLine().trim();
				tools.clearScreen();
				stockMenu(homeCallBack);
				return;
			}

			// Update inventory
			newBirdCount = currentBirds - birdNo;
			Object[] values = {newBirdCount, currentEggs,currentBrokenEggs, currentFeed};
			String[] columns = {"record_date","bird_no", "eggs_no","broken_eggs", "feeds_no"};
			
			tools.addRecord("inventory", values);
		}
		System.out.println("\nRemoved " + birdNo + " birds. Remaining birds: " + newBirdCount);
		System.out.println("Press ENTER to return...");
		read.nextLine().trim();
		tools.clearScreen();
		stockMenu(homeCallBack);
	}
	public static void addFeed (Runnable homeCallBack) {
		double newFeedCount=0;
		System.out.println("--- Add Feed ---");
		
		double feedNo = tools.getPositiveDoubleInput("Input amount of Feed to add: ");
		if (!tools.confirm(feedNo)) { 
			tools.clearScreen();
			stockMenu(homeCallBack); 
			return; 
		}

		Object[][] inventory = tools.getAllRecords("inventory");
		
		if (inventory.length == 0) {
			// First time - create initial inventory
			// Let's assume columns: inventory_id(auto), bird_no, eggs_no, broken_eggs, feeds_no, created_at(auto)
			// So we need to provide 3 values
			Object[] values = {0, 0, feedNo, 0.0}; // bird_no, broken_eggs, eggs_no, feeds_no
			tools.addRecord("inventory", values);
			System.out.println("\nInitial inventory created with " + feedNo + " feeds.");
		} else {
			// Update existing inventory
			Object[] latest = inventory[inventory.length - 1];
			int currentBirds = Integer.parseInt(latest[1].toString());
			int currentEggs = Integer.parseInt(latest[2].toString());
			int currentBrokenEggs  = Integer.parseInt(latest[3].toString());
			double currentFeed = Double.parseDouble(latest[4].toString());
		
			// Update inventory
			newFeedCount = currentFeed + feedNo;
			Object[] values = {currentBirds, currentEggs, currentBrokenEggs, newFeedCount};
			String[] columns = {"record_date","bird_no", "eggs_no", "broken_eggs", "feeds_no"};
			
			tools.addRecord("inventory", values);
		}
		System.out.println("\nAdded " + feedNo + "feed. Total feed: " + newFeedCount + "bags");
		System.out.println("Press ENTER to return...");
		read.nextLine().trim();
		tools.clearScreen();
		stockMenu(homeCallBack);
	}
	public static void viewStock(Runnable homeCallBack) {
		System.out.println("--- Current Stock ---");
		
		Object[][] inventory = tools.getAllRecords("inventory");
		
		if (inventory.length == 0) {
			System.out.println("No inventory records found.");
		} else {
			Object[] latest = inventory[inventory.length - 1];
			int currentBirds = Integer.parseInt(latest[1].toString());
			int currentEggs = Integer.parseInt(latest[2].toString());
			double currentFeed = Double.parseDouble(latest[4].toString());
			
			System.out.println("Current Birds\t: " + currentBirds);
			System.out.println("Current Eggs\t: " + currentEggs);
			System.out.println("Current Feed\t: " + currentFeed);
		}
		
		System.out.println("\n1. View Stock History");
		System.out.println("0. Back");
		System.out.print("Option: ");

		int option = -1;
		while (true) {
			try {
				option = read.nextInt();
				read.nextLine().trim();
				break;
			} catch (Exception e) {
				System.out.println("Invalid input");
				read.nextLine().trim();
				System.out.print("Option: ");
			}
		}

		tools.clearScreen();
		if (option == 1) viewStockHistory(homeCallBack);
		else stockMenu(homeCallBack);
	} 
	public static void viewStockHistory(Runnable homeCallBack) {
		Object[][] inventory = tools.getAllRecords("inventory");
		if (inventory.length == 0) {
			System.out.println("No inventory records found");
			System.out.println("Press ENTER to return...");
			read.nextLine().trim();
			tools.clearScreen();
			stockMenu(homeCallBack);
			return;
		}

		int pageSize = 10;
		int totalPages = (int) Math.ceil(inventory.length / (double) pageSize);
		int page = 1;

		while (true) {
			tools.clearScreen();
			System.out.println("--- Inventory History ---");
			System.out.println("\nDate\t\t|Birds\t|Eggs\t|Feeds");
			System.out.println("-".repeat(50));

			int startIdx = (page - 1) * pageSize;
			int endIdx = Math.min(startIdx + pageSize, inventory.length);
			
			for (int i = startIdx; i < endIdx; i++) {
				Object[] record = inventory[i];
				String date = record[5].toString().split(" ")[0];
				System.out.println(date + "\t\t|" + record[1] + "\t|" + 
								  record[2] + "\t|" + record[4]);
			}

			System.out.println("-".repeat(50));
			System.out.println("\nPage " + page + " of " + totalPages + " (" + inventory.length + " records)");
			System.out.println("N. Next Page | P. Previous Page | 0. Back");
			System.out.print("Option: ");
			String option = read.nextLine().trim().toUpperCase();

			if (option.equals("(?i)N") && page < totalPages) {
				page++;
			} 
			else if(option.equals("(?i)N") && !(page<totalPages)){
				System.out.println("No next page - this is the last page.");
				System.out.println("Press ENTER to continue...");
				read.nextLine().trim();
			} else if (option.equals("(?i)P") && page > 1) {
				page--;
			} else if (option.equals("(?i)P}") && !(page > 1)) {
				System.out.println("No previous page - this is the first page.");
				System.out.println("Press ENTER to continue...");
				read.nextLine().trim();
			} else if (option.equals("0")) { 
				tools.clearScreen(); 
				stockMenu(homeCallBack); 
				return; 
			} else { 
				System.out.println("Invalid option. Press Enter to continue..."); 
				read.nextLine().trim(); 
			}
		}
	}
}