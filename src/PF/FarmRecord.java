package PF;

import java.util.Scanner;
import java.time.LocalDateTime;

public class FarmRecord {
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner(System.in);
	static LocalDateTime time = LocalDateTime.now();

	public static void farmMenu(Runnable homeCallBack) {
		System.out.println("\n" + "=".repeat(50) + "\n");
		System.out.println(tools.center("FARM MANAGEMENT MENU", 50));
		System.out.println("\n" + "=".repeat(50) + "\n");
		System.out.print("1. Record Today's Farm Data\n2. View Farm Record History\n3. Update Existing Farm Record\n0. Back\nOption: ");

		int option = -1;
		while (true) {
			try {
				option = read.nextInt();
				read.nextLine();
				break;
			} catch (Exception e) {
				System.out.println("\nInvalid input! Please enter numbers only.");
				read.nextLine();
				System.out.print("Option: ");
			}
		}

		tools.clearScreen();
		switch (option) {
			case 0 -> homeCallBack.run();
			case 1 -> todayData(homeCallBack);
			case 2 -> viewRecord(homeCallBack);
			case 3 -> editRecord(homeCallBack);
			default -> {
				System.out.println("Invalid selection");
				farmMenu(homeCallBack);
			}
		}
	}
	public static void todayData(Runnable homeCallBack) {
		boolean isConfirm = false;
		int eggNo = 0, deathNo = 0, brokenEggs = 0;
		double feedNo = 0;
		String comment = "", eggInput = "";

		String today = time.toLocalDate().toString();
		if (tools.recordExists("farm_records", "record_date", today)) {
			System.out.println("Record Already Exists");
			while (!isConfirm) {
				System.out.print("\nDo you want to edit past record (Y/N)? ");
				char confirm = Character.toLowerCase(read.next().charAt(0));
				read.nextLine();
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

		// Eggs input
		while (!isConfirm) {
			System.out.print("Input number of Crate(s) [Eg. 5_3]: ");
			eggInput = read.nextLine();
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

		// Broken eggs input
		isConfirm = false;
		while (!isConfirm) {
			brokenEggs = tools.getPositiveIntInput("Input number of Broken Egg(s): ");
			isConfirm = tools.confirm(brokenEggs);
		}

		// Comment input
		isConfirm = false;
		while (!isConfirm) {
			System.out.print("Comment: ");
			comment = read.nextLine();
			if (comment.isEmpty()) comment = "null";
			isConfirm = tools.confirm(comment);
		}

		// Update Inventory
		int currentBirds = tools.getInventory("birds");
		double currentFeed = tools.getInventory("feed");

		if (deathNo > currentBirds) {
			System.out.println("Insufficient. Total Birds: " + currentBirds);
			read.nextLine();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}

		if (feedNo > currentFeed) {
			System.out.println("Insufficient. Total Feeds: " + currentFeed);
			read.nextLine();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}

		tools.updateInventory(currentBirds - deathNo, currentFeed - feedNo);

		// Add farm record to DB
		Object[] values = {today, eggNo, feedNo, deathNo, brokenEggs, comment};
		tools.addRecord("farm_records", values);

		System.out.println("\nToday's data saved successfully\nPress ENTER to return...");
		read.nextLine();
		tools.clearScreen();
		farmMenu(homeCallBack);
	}
	public static void viewRecord(Runnable homeCallBack) {
		Object[][] records = tools.getAllRecords("farm_records");
		if (records.length == 0) {
			System.out.println("No farm records found\n0. Back");
			read.nextLine();
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
			System.out.println("\nDate\t|Crates(Eggs)\t|Feed\t|Death\t|Broken\t|Comment");
			System.out.println("-".repeat(60));

			Object[][] pageRecords = tools.getPage(records, page, pageSize);
			for (Object[] record : pageRecords) {
				System.out.println(record[1] + "\t|" + ((int) record[2] / 30) + "(" + ((int) record[2] % 30) + ")\t|" +
						record[3] + "\t|" + record[4] + "\t|" + record[5] + "\t|" + record[6]);
			}

			System.out.println("-".repeat(60));
			System.out.println("\nPage " + page + "/" + totalPages);
			System.out.println("N. Next | P. Previous | 0. Back");
			System.out.print("Option: ");
			String option = read.nextLine().trim().toUpperCase();

			if (option.equals("N") && page < totalPages) page++;
			else if (option.equals("P") && page > 1) page--;
			else if (option.equals("0")) { tools.clearScreen(); farmMenu(homeCallBack); return; }
			else { System.out.println("Invalid Input\nPress Enter to continue..."); read.nextLine(); }
		}
	}
	public static void editRecord(Runnable homeCallBack) {
		System.out.println("--- Farm Record Editor ---");
		boolean isConfirm = false;
		String date = "";

		while (!isConfirm) {
			System.out.print("Input Date(\"YYYY-MM-DD\"): ");
			date = read.nextLine();
			isConfirm = tools.confirm(date);
		}

		Object[] record = tools.getRecordByDate("farm_records", "record_date", date);
		if (record == null) {
			System.out.println("No record found for date: " + date + "\nPress ENTER to return...");
			read.nextLine();
			tools.clearScreen();
			farmMenu(homeCallBack);
			return;
		}

		int previousDeath = (int) record[3];
		double previousFeed = (double) record[2];

		int eggNo = tools.getUpdatedEggs();
		double feedNo = tools.getUpdatedFeed();
		int deathNo = tools.getUpdatedDeaths();
		int brokenEggs = tools.getUpdatedBrokenEggs();
		String comment = tools.getUpdatedComment();

		int currentBirds = tools.getInventory("birds") + previousDeath - deathNo;
		double currentFeed = tools.getInventory("feed") + previousFeed - feedNo;
		tools.updateInventory(currentBirds, currentFeed);

		Object[] values = {eggNo, feedNo, deathNo, brokenEggs, comment};
		tools.updateRecord("farm_records", new String[]{"eggs", "feed", "deaths", "broken_eggs", "comment"}, values, "record_date = '" + date + "'");

		System.out.println("\nRecord updated successfully!\nPress ENTER to return...");
		read.nextLine();
		tools.clearScreen();
		farmMenu(homeCallBack);
	}
}
