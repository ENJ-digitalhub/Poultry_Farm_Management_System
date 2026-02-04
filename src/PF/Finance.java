package PF;

import java.util.*;
import java.time.*;

public class Finance {
	static Scanner read = new Scanner(System.in);
	static SystemUtils tools = new SystemUtils();
	static LocalDateTime time = LocalDateTime.now();

	public static void financeMenu(Runnable homeCallBack) {
		System.out.println("\n" + "=".repeat(100) + "\n");
		System.out.println(tools.center("FINANCE MANAGEMENT MENU", 100));
		System.out.println("\n" + "=".repeat(100) + "\n");
		System.out.print("1. Record Sales\n2. Record new Expense\n3. View Current Stock\n0. Back\nOption: ");

		int option = -1;
		while (true) {
			try {
				option = read.nextInt();
				read.nextLine().trim();
				break;
			} catch (Exception e) {
				System.err.println("Invalid input");
				read.nextLine().trim();
				System.out.print("Option: ");
			}
		}

		tools.clearScreen();
		switch (option) {
			case 0 -> homeCallBack.run();
			case 1 -> newSales(homeCallBack);
			case 2 -> newExpense(homeCallBack);
			case 3 -> viewStock(homeCallBack);
			default -> {
				System.err.println("Invalid selection");
				financeMenu(homeCallBack);
			}
		}
	}
	public static void newExpense(Runnable homeCallBack) {
		boolean isConfirm = false, saved = false;
		String date = "", category= "", item="";
		double quantity = 0.00, unitPrice=0.00, total=0.00;
		
		System.out.println("--- Add New Expense ---");
		// date input 
		while (!isConfirm) {
			System.out.print("Input Date [Format: YYYY-MM-DD]: ");
			try {
				date = read.nextLine().trim();
				if (String.valueOf(Integer.parseInt(date.split("-")[0])).length() != 4 || String.valueOf(Integer.parseInt(date.split("-")[1])).length() != 2 || String.valueOf(Integer.parseInt(date.split("-")[2])).length() != 2) throw new Exception("Invalid date length");
				isConfirm = tools.confirm(date);
			} catch (Exception e) {
				System.err.println("Invalid format. Use date format.");
			}
		}
		isConfirm = false;
		// category input 
		while (!isConfirm) {
			System.out.print("Input Category [Dropdown: Feed | Birds | Drugs | Manure | Other]: ");
			try {
				category = read.nextLine().trim().toLowerCase();
				if(!category.equals("feed") &&
						!category.equals("birds") &&
						!category.equals("drugs") &&
						!category.equals("manure") &&
						!category.equals("other")) throw new Exception("Invalid category. Use dropdown.");
				isConfirm = tools.confirm(category);
			} catch (Exception e) {
				System.err.println("Invalid category. Use dropdown.");
			}
		}
		isConfirm = false;
		// item input 
		while (!isConfirm) {
			System.out.print("Input item name: ");
			try {
				item = read.nextLine().trim().toLowerCase();
				isConfirm = tools.confirm(item);
			} catch (Exception e) {
				System.err.println("Invalid Input.");
			}
		}
		// quantity input 
		isConfirm = false;
		while (!isConfirm) {
			try {
				quantity = tools.getPositiveDoubleInput("Input Quantity: ");
				isConfirm = tools.confirm(quantity);
			} catch (Exception e) {
				System.err.println("Invalid input. Numeric values ONLY.");
			}
		}
		isConfirm = false;
		// unitPrice input 
		while (!isConfirm) {
			try {
				unitPrice = tools.getPositiveDoubleInput("Input Unit Price: ");
				isConfirm = tools.confirm(unitPrice);
			} catch (Exception e) {
				System.err.println("Invalid input. Numeric values ONLY.");
			}
		}
		
		//total cal
		total=unitPrice*quantity;
		
		//Input Summary
		while(true){
			System.out.println("-".repeat(100)+
											"\n"+tools.center("NEW EXPENSE",100)+"\n"+
											"-".repeat(100)+
											"\nDATE\t\t:"+date+
											"\nCATEGORY\t:"+category+
											"\nITEM NAME\t:"+item+
											"\nQUANTITY\t:"+String.format("%,.1f",quantity)+
											"\nUNIT PRICE(N)\t:"+String.format("%,.2f",unitPrice)+
											"\nTOTAL(N)\t:"+String.format("%,.2f",total)+
											"\ns. Save\t|c. Cancel"
											);
			String option= read.nextLine().trim().toLowerCase();
			if (option.equals("s")){
				System.out.println("Saving...");
				//save new record
				Object[] values = {date,category, item, quantity, unitPrice, total};
				try{
					tools.addRecord("expense", values);
					saved=true;
				}
				catch (Exception e){
					System.err.println(e.getMessage());
					saved=false;
				}
				if (saved){
					System.out.println("Saved\nPress ENTER to conrinue...");
					read.nextLine();
					break;
				}
				else{
					System.err.println("Not saved\nPress ENTER to try again...");
					read.nextLine();
				}
			}
			else if (option.equals("c")){
				System.out.println("Canceling...\nCanceled\nPress ENTER to continue...");
				read.nextLine();
				break;
			}
			else{
				System.err.println("Invalid input");
			}
		}
		tools.clearScreen();
		financeMenu(homeCallBack);
	}	
	public static void newSales(Runnable homeCallBack) {
		boolean isConfirm = false, saved = false;
		String date = "", item="";
		double quantity = 0.00, unitPrice=0.00, total=0.00;
		
		System.out.println("--- Add New Sales ---");
		// date input 
		while (!isConfirm) {
			System.out.print("Input Date [Format: YYYY-MM-DD]: ");
			try {
				date = read.nextLine().trim();
				if (String.valueOf(Integer.parseInt(date.split("-")[0])).length() != 4 || String.valueOf(Integer.parseInt(date.split("-")[1])).length() != 2 || String.valueOf(Integer.parseInt(date.split("-")[2])).length() != 2) throw new Exception("Invalid date length");
				isConfirm = tools.confirm(date);
			} catch (Exception e) {
				System.err.println("Invalid format. Use date format.");
			}
		}
		isConfirm = false;
		// item input 
		while (!isConfirm) {
			System.out.print("Input item name: ");
			try {
				item = read.nextLine().trim().toLowerCase();
				isConfirm = tools.confirm(item);
			} catch (Exception e) {
				System.err.println("Invalid Input.");
			}
		}
		// quantity input 
		isConfirm = false;
		while (!isConfirm) {
			try {
				quantity = tools.getPositiveDoubleInput("Input Quantity: ");
				isConfirm = tools.confirm(quantity);
			} catch (Exception e) {
				System.err.println("Invalid input. Numeric values ONLY.");
			}
		}
		isConfirm = false;
		// unit price input 
		while (!isConfirm) {
			try {
				unitPrice = tools.getPositiveDoubleInput("Input Unit Price: ");
				isConfirm = tools.confirm(unitPrice);
			} catch (Exception e) {
				System.err.println("Invalid input. Numeric values ONLY.");
			}
		}
		
		//total cal
		total=unitPrice*quantity;
		
		//Input Summary
		System.out.print("-".repeat(100)+
										"\n"+tools.center("NEW SALES",100)+"\n"+
										"-".repeat(100)+
										"\nDATE\t\t:"+date+
										"\nITEM NAME\t:"+item+
										"\nQUANTITY\t:"+String.format("%,.1f",quantity)+
										"\nUNIT PRICE(N)\t:"+String.format("%,.2f",unitPrice)+
										"\nTOTAL(N)\t:"+String.format("%,.2f",total)+
										"\nS. Save | C. Cancel"+
										"\nOption: "
										);
		while(true){
			String option= read.nextLine().trim().toLowerCase();
			if (option.equals("s")){
				System.out.println("Saving...");
				//save new record
				Object[] values = {date,item, quantity, unitPrice, total};
				try{
					tools.addRecord("sales", values);
					saved=true;
				}
				catch (Exception e){
					System.err.println(e.getMessage());
					saved=false;
				}
				if(saved){
					System.out.println("Saved\nPress ENTER to continue...");
					read.nextLine();
					break;
				}
			}
			else if (option.equals("c")){
				System.out.println("Canceling...\nCanceled\nPress ENTER to continue...");
				read.nextLine();
				break;
			}
			else{
				System.err.println("Invalid input");
				tools.clearScreen();
			}
		}
		tools.clearScreen();
		financeMenu(homeCallBack);
	}
	public static void viewStock(Runnable homeCallBack) {
		Object[][] inventory = tools.getAllRecords("expense2");
		
		System.out.println("\n--- Current Stock ---");
		
		if (inventory.length == 0) {
			System.out.println("No inventory records found.");
		} else {
			for(int i = 0; i<inventory.length; i++){
				Object[] latest = inventory[i];
				String item = latest[0].toString();
				double quantity = Double.parseDouble(latest[1].toString());
				/*double currentFeed = Double.parseDouble(latest[4].toString());
				System.out.println("Current Birds\t: " + currentBirds);
				System.out.println("Current Eggs\t: " + currentEggs);
				System.out.println("Current Feed\t: " + currentFeed);*/
				System.out.println(item+": "+String.format("%,.2f",quantity));
			}
		}
		
		System.out.println("\n1. View Stock History");
		System.out.println("2. View Sales History");
		System.out.println("0. Back");

		int option = -1;
		while (true) {
			try {
				System.out.print("Option: ");
				option = read.nextInt();
				tools.clearScreen();
				read.nextLine();
			} catch (Exception e) {
				System.err.println("Invalid input("+e.getMessage()+")");
				read.nextLine();
			}
			if (option == 1) {
				viewStockHistory(homeCallBack);
				return;
			}
			if (option == 2) {
				viewSalesHistory(homeCallBack);
				return;
			}
			if (option == 0) {
				financeMenu(homeCallBack);
				return;
			}
		}
	} 
	public static void viewStockHistory(Runnable homeCallBack) {
		Object[][] inventory = tools.getAllRecords("expense");
		if (inventory.length == 0) {
			System.out.println("No inventory records found");
			System.out.println("Press ENTER to return...");
			read.nextLine().trim();
			tools.clearScreen();
			financeMenu(homeCallBack);
			return;
		}

		int pageSize = 10;
		int totalPages = (int) Math.ceil(inventory.length / (double) pageSize);
		int page = 1;

		while (true) {
			//tools.clearScreen();
			System.out.println("--- Inventory History ---");
			System.out.println("\nId\t|Date\t\t|Category\t|Item\t\t|Quantity\t|Unit Price\t|Total");
			System.out.println("-".repeat(100));

			int startIdx = (page - 1) * pageSize;
			int endIdx = Math.min(startIdx + pageSize, inventory.length);
			for (int i = startIdx; i < endIdx; i++) {
				Object[] record = inventory[i];
				String date = record[1].toString();
				System.out.println(record[0]+
												"\t|"+ date + 
												"\t|" + record[2] + 
												"\t\t|" + record[3] + 
												"\t\t|" + String.format("%,.2f",Double.parseDouble(record[4].toString())) + 
												"\t\t|" + String.format("%,.2f",Double.parseDouble(record[5].toString())) + 
												"\t|" + String.format("%,.2f",Double.parseDouble(record[6].toString()))
				);
			}

			System.out.println("-".repeat(100));
			System.out.println("\nPage " + page + " of " + totalPages + " (" + inventory.length + " records)");
			System.out.println("N. Next Page | P. Previous Page | 0. Back");
			System.out.print("Option: ");
			String option = read.nextLine().trim().toLowerCase();

			if (option.equals("n") && page < totalPages) {
				page++;
				tools.clearScreen();
			} 
			else if(option.equals("n") && !(page<totalPages)){
				System.out.println("No next page - this is the last page.");
				System.out.println("Press ENTER to continue...");
				read.nextLine();
				tools.clearScreen();
			} else if (option.equals("p") && page > 1) {
				page--;
				tools.clearScreen();
			} else if (option.equals("p") && !(page > 1)) {
				System.out.println("No previous page - this is the first page.");
				System.out.println("Press ENTER to continue...");
				read.nextLine();
				tools.clearScreen();
			} else if (option.equals("0")) { 
				tools.clearScreen(); 
				viewStock(homeCallBack);
				return; 
			} else { 
				System.err.println("Invalid option. Press Enter to continue..."); 
				read.nextLine().trim(); 
				tools.clearScreen(); 
			}
		}
	}	
	public static void viewSalesHistory(Runnable homeCallBack) {
		Object[][] inventory = tools.getAllRecords("sales");
		if (inventory.length == 0) {
			System.out.println("No inventory records found");
			System.out.println("Press ENTER to return...");
			read.nextLine().trim();
			tools.clearScreen();
			financeMenu(homeCallBack);
			return;
		}

		int pageSize = 10;
		int totalPages = (int) Math.ceil(inventory.length / (double) pageSize);
		int page = 1;

		while (true) {
			//tools.clearScreen();
			System.out.println("--- Inventory History ---");
			System.out.println("\nId\t|Date\t\t|Item\t\t|Quantity\t|Unit Price\t|Total");
			System.out.println("-".repeat(100));

			int startIdx = (page - 1) * pageSize;
			int endIdx = Math.min(startIdx + pageSize, inventory.length);
			for (int i = startIdx; i < endIdx; i++) {
				Object[] record = inventory[i];
				String date = record[1].toString();
				System.out.println(record[0]+
												"\t|"+ date + 
												"\t|" + record[2] + 
												"\t\t|" + String.format("%,.2f",Double.parseDouble(record[3].toString())) + 
												"\t\t|" + String.format("%,.2f",Double.parseDouble(record[4].toString())) + 
												"\t|" + String.format("%,.2f",Double.parseDouble(record[5].toString()))
				);
			}

			System.out.println("-".repeat(100));
			System.out.println("\nPage " + page + " of " + totalPages + " (" + inventory.length + " records)");
			System.out.println("N. Next Page | P. Previous Page | 0. Back");
			System.out.print("Option: ");
			String option = read.nextLine().trim().toLowerCase();

			if (option.equals("n") && page < totalPages) {
				page++;
				tools.clearScreen();
			} 
			else if(option.equals("n") && !(page<totalPages)){
				System.out.println("No next page - this is the last page.");
				System.out.println("Press ENTER to continue...");
				read.nextLine();
				tools.clearScreen();
			} else if (option.equals("p") && page > 1) {
				page--;
				tools.clearScreen();
				
			} else if (option.equals("p") && !(page > 1)) {
				System.out.println("No previous page - this is the first page.");
				System.out.println("Press ENTER to continue...");
				tools.clearScreen();
				read.nextLine();
			} else if (option.equals("0")) { 
				tools.clearScreen(); 
				viewStock(homeCallBack);
				return; 
			} else { 
				System.err.println("Invalid option. Press Enter to continue..."); 
				read.nextLine().trim(); 
				tools.clearScreen();
			}
		}
	}
	public static void checkLowStock() {
		// Get current inventory from database
		Object[][] inventory = tools.getAllRecords("inventory");
		
		if (inventory.length == 0) {
			System.out.println("[Notification] No inventory data found.");
			return;
		}
		
		// Get latest inventory record
		Object[] latest = inventory[inventory.length - 1];
		
		
		// Extract values based on your database structure
		int currentBirdStock = 0;
		int currentEggStock = 0;
		double currentFeedStock = 0;
		
		// Based on your data, indices seem to be:
		// [0]=inventory_id, [1]=bird_no, [2]=eggs_no, [3]=broken_eggs, [4]=feeds_no, [5]=created_by, [6]=created_at
		if (latest.length > 1) {
			try {
				currentBirdStock = Integer.parseInt(latest[1].toString());
			} catch (NumberFormatException e) {
				System.err.println("[Error] Invalid bird stock value: " + latest[1]);
			}
		}
		
		if (latest.length > 2) {
			try {
				currentEggStock = Integer.parseInt(latest[2].toString());
			} catch (NumberFormatException e) {
				System.err.println("[Error] Invalid egg stock value: " + latest[2]);
			}
		}
		
		if (latest.length > 4) {
			try {
				currentFeedStock = Double.parseDouble(latest[4].toString());
			} catch (NumberFormatException e) {
				System.err.println("[Error] Invalid feed stock value: " + latest[4]);
			}
		}
		
		// Define thresholds
		final int MIN_BIRD_STOCK = 100;	  // minimum birds
		final int MIN_EGG_STOCK = 30;	   // minimum eggs
		final double MIN_FEED_STOCK = 25.0; // minimum feed in units
		
		boolean lowStockFound = false;
		
		// Check bird stock
		if (currentBirdStock < MIN_BIRD_STOCK) {
			System.out.println("[Notification] LOW STOCK!");
			System.out.printf("Birds: %d/%d ", currentBirdStock, MIN_BIRD_STOCK);
			lowStockFound = true;
		}
		
		// Check egg stock
		if (currentEggStock < MIN_EGG_STOCK) {
			System.out.println("[Notification] LOW STOCK!");
			System.out.printf("Eggs: %d/%d ", currentEggStock, MIN_EGG_STOCK);
			lowStockFound = true;
		}
		
		// Check feed stock
		if (currentFeedStock < MIN_FEED_STOCK) {
			System.out.println("[Notification] LOW STOCK!");
			System.out.printf("Feed: %.1f/%.1f ", currentFeedStock, MIN_FEED_STOCK);
			lowStockFound = true;
		}
		
		// Show summary
		if (lowStockFound) {
			System.out.println("\n[Notification] ACTION REQUIRED: Some stock levels are below minimum!");
			System.out.println("[Notification] Please add stock in Inventory Management.");
		} else {
			System.out.println("\n[Notification] All stock levels are adequate.");
		}
	}
}