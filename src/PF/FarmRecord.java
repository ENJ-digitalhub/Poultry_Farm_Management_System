package PF;

import java.util.Scanner;
import java.time.LocalDateTime;

public class FarmRecord {
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner(System.in);
	static LocalDateTime time = LocalDateTime.now();

	public static void farmMenu(Runnable homeCallBack) {
		System.out.println("\n" + "=".repeat(60) + "\n");
		System.out.println(tools.center("FARM MANAGEMENT MENU", 50));
		System.out.println("\n" + "=".repeat(60) + "\n");
		System.out.print("1. Record Today's Farm Data\n2. View Farm Record History\n3. Update Existing Farm Record\n0. Back\nOption: ");

		int option = -1;
		while (true) {
			try {
				option = read.nextInt();
				read.nextLine().trim();
				break;
			} catch (Exception e) {
				System.out.println("\nInvalid input! Please enter numbers only.");
				read.nextLine().trim();
				System.out.print("Option: ");
			}
		}

		tools.clearScreen();
		switch (option) {
			case 0:
				tools.clearScreen();
				homeCallBack.run();
				break;
			case 1:
				tools.clearScreen();
				todayData(homeCallBack);
				break;
			case 2:
				tools.clearScreen();
				viewRecord(homeCallBack);
				break;
			case 3:
				tools.clearScreen();
				editRecord(homeCallBack);
				break;
			default:
				tools.clearScreen();
				System.out.println("Invalid selection");
				farmMenu(homeCallBack);
				break;
		}
	}
	public static void todayData(Runnable homeCallBack) {
		boolean isConfirm = false;
		int eggNo = 0, brokenEggNo = 0, deathNo = 0;
		double feedNo = 0;
		String comment = "", eggInput = "";
		
		String today = time.toLocalDate().toString();
		Object[][] allRecords = tools.getAllRecords("farm_records");
		boolean recordExists = false;
		
		for (Object[] record : allRecords) {
			// created_at is at index 5 (0=record_id, 1=eggs, 2=broken_eggs, 3=feed, 4=death, 5=comment, 6=created_at)
			String recordDate = record[1].toString();
			if (recordDate.equals(today)) {
				recordExists = true;
				break;
			}
		}
		
		if (recordExists) {
			System.out.println("Record Already Exists");
			while (!isConfirm) {
				System.out.print("\nDo you want to edit past record (Y/N)? ");
				char confirm = Character.toLowerCase(read.next().charAt(0));
				read.nextLine().trim();
				if (confirm == 'y') {
					tools.clearScreen();
					editRecord(homeCallBack);
					return;
				} else if (confirm == 'n') {
					tools.clearScreen();
					farmMenu(homeCallBack);
					return;
				} else {
					tools.clearScreen();
					System.out.println("Invalid Input");
				}
			}
		}

		// Eggs input - Your original crate format logic
		while (!isConfirm) {
			System.out.print("Input number of Crate(s) [Eg. 5_3-> 5 crates,3 eggs]: ");
			eggInput = read.nextLine().trim();
			try {
				String[] parts = eggInput.split("_");
				int crates = Integer.parseInt(parts[0]);
				int pieces = Integer.parseInt(parts[1]);

				if (crates < 0 || pieces < 0) System.out.println("Invalid Input. Positive values only");
				else {
					if (pieces >= 30) {
						crates += pieces / 30;
						pieces %= 30;
					}
					eggNo = crates * 30 + pieces;
					isConfirm = tools.confirm(eggInput);
				}
			} catch (Exception e) {
				System.out.println("Invalid format. Use crate_eggs format.");
			}
		}
		
		// Broken egg input
		isConfirm = false;
		while (!isConfirm) {
			brokenEggNo = tools.getPositiveIntInput("Input number of broken Egg(s): ");
			isConfirm = tools.confirm(brokenEggNo);
		}
		
		// Feed input
		isConfirm = false;
		while (!isConfirm) {
			feedNo = tools.getPositiveDoubleInput("Input number of Feed(s) used: ");
			isConfirm = tools.confirm(feedNo);
		}

		// Deaths input
		isConfirm = false;
		while (!isConfirm) {
			deathNo = tools.getPositiveIntInput("Input number of Death(s): ");
			isConfirm = tools.confirm(deathNo);
		}

		// Comment input
		isConfirm = false;
		while (!isConfirm) {
			System.out.print("Comment: ");
			comment = read.nextLine().trim();
			if (comment.isEmpty()) comment = "null";
			isConfirm = tools.confirm(comment);
		}

		// Get inventory - need to check if exists first
		Object[][] inventoryData = tools.getAllRecords("inventory");
		if (inventoryData.length == 0) {
			System.out.println("No inventory found. Please add inventory first.");
			System.out.println("Press ENTER to return...");
			read.nextLine().trim();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}
		
		// Get latest inventory - assuming last one is latest
		Object[] latestInventory = inventoryData[inventoryData.length - 1];
		int currentBirds = Integer.parseInt(latestInventory[1].toString());  // bird_no
		double currentFeed = Double.parseDouble(latestInventory[4].toString());  // feeds_no

		// Check inventory - your original logic
		if (deathNo > currentBirds) {
			System.out.println("Insufficient. Total Birds: " + currentBirds);
			read.nextLine().trim();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}

		if (feedNo > currentFeed) {
			System.out.println("Insufficient. Total Feeds: " + currentFeed);
			read.nextLine().trim();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}

		// Update Inventory - calculate new values
		int newBirdCount = currentBirds - deathNo;
		double newFeedCount = currentFeed - feedNo;
		int currentEggs = Integer.parseInt(latestInventory[2].toString());
		int currentBrokenEggs = Integer.parseInt(latestInventory[3].toString());
		int newEggCount = currentEggs + eggNo; // Add collected eggs to inventory
		int newBrokenEggCount = currentBrokenEggs + brokenEggNo;
		
		
		// Update inventory in database
		Object[] inventoryValues = {newBirdCount, newEggCount, newBrokenEggCount, newFeedCount};
		tools.addRecord("inventory",  inventoryValues);

		// Add farm record to DB - your database schema
		// farm_records: eggs_collected, feeds_used, death, comment (created_at is auto)
		Object[] values = {eggNo,brokenEggNo, feedNo, deathNo, comment};
		tools.addRecord("farm_records", values);

		System.out.println("\nToday's data saved successfully\nPress ENTER to return...");
		read.nextLine().trim();
		tools.clearScreen();
		farmMenu(homeCallBack);
	}
	public static void viewRecord(Runnable homeCallBack) {
		Object[][] records = tools.getAllRecords("farm_records");
		if (records.length == 0) {
			System.out.println("No record found"+
										"Press ENTER to return...");
			read.nextLine().trim();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}

		int pageSize = 10;
		int totalPages = (int) Math.ceil(records.length / (double) pageSize);
		int page = 1;

		while (true) {
			tools.clearScreen();
			System.out.println("--- Farm Record History ---");
			System.out.println("\nDate\t|Crates(Eggs)\t|Feed\t|Death\t|Comment");
			System.out.println("-".repeat(60));

			// Get current page - your original display format
			int startIdx = (page - 1) * pageSize;
			int endIdx = Math.min(startIdx + pageSize, records.length);
			
			for (int i = startIdx; i < endIdx; i++) {
				Object[] record = records[i];
				// Indices: 0=record_id, 1=eggs_collected,2=broken_eggs, 3=feeds_used, 4=death, 5=comment, 6=created_at
				String date = record[6].toString().split(" ")[0];
				int totalEggs = Integer.parseInt(record[1].toString());
				int crates = totalEggs / 30;
				int pieces = totalEggs % 30;
				
				// Your original display format
				System.out.println(date + "\t|" + crates + "(" + pieces + ")\t|" +
						record[2] + "\t|" + record[3] + "\t|" + 
						(record[4] != null ? record[4].toString() : ""));
			}

			System.out.println("-".repeat(60));
			System.out.println("\nPage " + page + "/" + totalPages);
			System.out.println("N. Next | P. Previous | 0. Back");
			System.out.print("Option: ");
			String option = read.nextLine().trim().toUpperCase();

			if (option.equals("N") && page < totalPages) page++;
			else if (option.equals("P") && page > 1) page--;
			else if (option.equals("0")) { tools.clearScreen(); farmMenu(homeCallBack); return; }
			else { System.out.println("Invalid Input\nPress Enter to continue..."); read.nextLine().trim(); }
		}
	}
	public static void editRecord(Runnable homeCallBack) {
		System.out.println("--- Farm Record Editor ---");
		boolean isConfirm = false;
		
		// Show recent records first
		Object[][] records = tools.getAllRecords("farm_records");
		if (records.length == 0) {
			System.out.println("No farm records found.");
			System.out.println("Press ENTER to return...");
			read.nextLine().trim();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}
		
		// Get record ID to edit
		System.out.println("\nRecent Records:");
		System.out.println("ID\tDate\t\tCrates(Eggs)\tBroken\tFeed\tDeaths");
		System.out.println("-".repeat(60));
		int showCount = Math.min(5, records.length);
		for (int i = 0; i < showCount; i++) {
			Object[] record = records[i];
			String date = record[1].toString();
			System.out.println(record[0] + "\t" + record[1] + "\t" + 
				   (Integer.parseInt(record[2].toString()) / 30) + "(" + 
				   (Integer.parseInt(record[2].toString()) % 30) + ")\t\t" +
				   record[3] + "\t" +
				   record[4] + "\t" +
				   record[5]);

		}
		
		System.out.print("\nEnter Record ID to edit (or 0 to cancel): ");
		int recordId = -1;
		while (true) {
			try {
				recordId = read.nextInt();
				read.nextLine().trim();
				if (recordId == 0) {
					tools.clearScreen();
					farmMenu(homeCallBack);
					return;
				}
				break;
			} catch (Exception e) {
				System.out.println("Please enter a valid record ID number:");
				read.nextLine().trim();
			}
		}
		
		// Find the record
		Object[] recordToEdit = null;
		for (Object[] record : records) {
			if (Integer.parseInt(record[0].toString()) == recordId) {
				recordToEdit = record;
				break;
			}
		}
		
		if (recordToEdit == null) {
			System.out.println("Record ID " + recordId + " not found.");
			System.out.println("Press ENTER to return...");
			read.nextLine().trim();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}
		
		// Get previous values for inventory adjustment
		int previousEggs = Integer.parseInt(recordToEdit[2].toString());
		int previousBrokenEggs = Integer.parseInt(recordToEdit[3].toString());
		double previousFeed = Double.parseDouble(recordToEdit[4].toString());
		int previousDeath = Integer.parseInt(recordToEdit[5].toString());
		
		// Get updated values - simpler approach
		System.out.println("\nEnter new values:");
		
		// Eggs input - Your original crate format logic
		isConfirm = false;
		int eggNo = 0;
		while (!isConfirm) {
			System.out.print("Input number of Crate(s) [Eg. 5_3-> 5 crates,3 eggs]: ");
			String eggInput = read.nextLine().trim();
			try {
				String[] parts = eggInput.split("_");
				int crates = Integer.parseInt(parts[0]);
				int pieces = Integer.parseInt(parts[1]);

				if (crates < 0 || pieces < 0) System.out.println("Invalid Input. Positive values only");
				else {
					if (pieces >= 30) {
						crates += pieces / 30;
						pieces %= 30;
					}
					eggNo = crates * 30 + pieces;
					isConfirm = tools.confirm(eggInput);
				}
			} catch (Exception e) {
				System.out.println("Invalid format. Use crate_eggs format.");
			}
		}
		// Broken Eggs
		isConfirm = false;
		int brokenEggNo = 0;
		while (!isConfirm) {
			brokenEggNo = tools.getPositiveIntInput("Input number of Cracks collected: ");
			isConfirm = tools.confirm(brokenEggNo);
		}
		
		// Feed
		isConfirm = false;
		double feedNo = 0;
		while (!isConfirm) {
			feedNo = tools.getPositiveDoubleInput("Input amount of Feed used: ");
			isConfirm = tools.confirm(feedNo);
		}
		
		// Deaths
		isConfirm = false;
		int deathNo = 0;
		while (!isConfirm) {
			deathNo = tools.getPositiveIntInput("Input number of Deaths: ");
			isConfirm = tools.confirm(deathNo);
		}
		
		// Comment
		isConfirm = false;
		String comment = "";
		while (!isConfirm){
			System.out.print("Comment: ");
			comment = read.nextLine().trim();
			if (comment.isEmpty()) comment = "null";
			isConfirm = tools.confirm(comment);
		}
		
		// Update farm record
		Object[] values = {eggNo, feedNo, deathNo, comment};
		String[] columns = {"eggs_collected","broken_eggs", "feeds_used", "death", "comment"};
		String condition = "record_id = " + recordId;
		
		tools.updateRecord("farm_records", columns, values, condition);
		
		// Update inventory based on differences
		int eggDiff = eggNo - previousEggs;
		int brokenDiff = brokenEggNo - previousBrokenEggs;
		double feedDiff = feedNo - previousFeed;
		int deathDiff = deathNo - previousDeath;
		
		// Get and update inventory
		Object[][] inventoryData = tools.getAllRecords("inventory");
		if (inventoryData.length > 0) {
			Object[] latestInventory = inventoryData[inventoryData.length - 1];
			int currentBirds = Integer.parseInt(latestInventory[1].toString()) - deathDiff;
			int currentEggs = Integer.parseInt(latestInventory[2].toString()) + eggDiff; // Add egg difference
			int currentBrokenEggs = Integer.parseInt(latestInventory[3].toString()) + brokenDiff;
			double currentFeed = Double.parseDouble(latestInventory[4].toString()) - feedDiff;
			
			Object[] inventoryValues = {currentBirds, currentEggs,currentBrokenEggs, currentFeed};
			String[] inventoryColumns = {"bird_no", "eggs_no","broken_eggs", "feeds_no"};
			tools.updateRecord("inventory", inventoryColumns, inventoryValues, 
							   "inventory_id = " + latestInventory[0]);
		}
		
		System.out.println("\nRecord updated successfully!\nPress ENTER to return...");
		read.nextLine().trim();
		tools.clearScreen();
		farmMenu(homeCallBack);
	}
}