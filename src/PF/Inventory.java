package PF;

import java.util.Scanner;
import java.time.LocalDateTime;

public class Inventory {
    static boolean isConfirm = false;
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
                read.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input");
                read.nextLine();
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
        isConfirm = false;
        int birdNo = 0;
        while (!isConfirm) {
            birdNo = tools.getPositiveIntInput("Input number of Bird(s) to add: ");
            isConfirm = tools.confirm(birdNo);
        }

        int currentBirds = tools.getInventory("birds");
        tools.updateInventory(currentBirds + birdNo, tools.getInventory("feed"));

        System.out.println("\nBirds added successfully.\nPress ENTER to return...");
        read.nextLine();
        tools.clearScreen();
        stockMenu(homeCallBack);
    }
    public static void removeBird(Runnable homeCallBack) {
        isConfirm = false;
        int birdNo = 0;
        while (!isConfirm) {
            birdNo = tools.getPositiveIntInput("Input number of Bird(s) to remove: ");
            isConfirm = tools.confirm(birdNo);
        }

        int currentBirds = tools.getInventory("birds");
        if (birdNo > currentBirds) {
            System.out.println("Insufficient Birds. Total Birds: " + currentBirds + "\nPress ENTER to return...");
            read.nextLine();
            tools.clearScreen();
            stockMenu(homeCallBack);
            return;
        }

        tools.updateInventory(currentBirds - birdNo, tools.getInventory("feed"));
        System.out.println("\nBirds removed successfully.\nPress ENTER to return...");
        read.nextLine();
        tools.clearScreen();
        stockMenu(homeCallBack);
    }
    public static void addFeed(Runnable homeCallBack) {
        isConfirm = false;
        double feedNo = 0;
        while (!isConfirm) {
            feedNo = tools.getPositiveDoubleInput("Input number of Feed(s) to add: ");
            isConfirm = tools.confirm(feedNo);
        }

        double currentFeed = tools.getInventory("feed");
        tools.updateInventory(tools.getInventory("birds"), currentFeed + feedNo);

        System.out.println("\nFeed added successfully.\nPress ENTER to return...");
        read.nextLine();
        tools.clearScreen();
        stockMenu(homeCallBack);
    }
    public static void viewStock(Runnable homeCallBack) {
        int currentBirds = tools.getInventory("birds");
        double currentFeed = tools.getInventory("feed");

        System.out.println("--- Current Stock ---");
        System.out.println("\nCrates(Eggs) \t\t: " + currentBirds / 30 + "(" + currentBirds % 30 + ")");
        System.out.println("Feed(s) \t: " + currentFeed);
        System.out.println("\n1. View Stock History");
        System.out.println("0. Back");
        System.out.print("Option: ");

        int option = -1;
        while (true) {
            try {
                option = read.nextInt();
                read.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input");
                read.nextLine();
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
			System.out.println("No inventory records found\n");
			System.out.println("0. Back");
			System.out.print("Option: ");
			read.nextLine();
			tools.clearScreen();
			stockMenu(homeCallBack);
			return;
		}

		int pageSize = 10;
		int start = inventory.length - 1; // start from the latest record

		while (true) {
			tools.clearScreen();
			System.out.println("--- Inventory History ---");
			System.out.println("\nDate\t\t|Birds\t|Feeds");
			System.out.println("-".repeat(50));

			int count = 0;
			int i = start;
			while (i >= 0 && count < pageSize) {
				Object[] record = inventory[i];
				String date = record[0].toString();
				int birds = (int) record[1];
				double feeds = (double) record[2];
				System.out.println(date + "\t|" + birds + "\t|" + feeds);
				i--;
				count++;
			}

			System.out.println("-".repeat(50));
			System.out.println("\nN. Next Page\tP. Previous Page\t0. Back");
			System.out.print("Option: ");
			String option = read.nextLine().toUpperCase();

			if (option.equals("N")) {
				if (start - pageSize >= 0) start -= pageSize;
				else {
					System.out.println("No more records.\nPress ENTER to continue...");
					read.nextLine();
				}
			} else if (option.equals("P")) {
				if (start + pageSize < inventory.length) start += pageSize;
				else {
					System.out.println("You are on the first page.\nPress ENTER to continue...");
					read.nextLine();
				}
			} else if (option.equals("0")) {
				tools.clearScreen();
				stockMenu(homeCallBack);
				return;
			} else {
				System.out.println("Invalid input.\nPress ENTER to continue...");
				read.nextLine();
			}
		}
	}
}
