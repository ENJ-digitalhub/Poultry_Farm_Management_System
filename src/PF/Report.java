package PF;
import PF.SystemUtils;
import java.time.*;
import java.util.*;

public class Report{
	static LocalDateTime time = LocalDateTime.now();
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner(System.in);
	static ArrayList<String> inventoryRecord = new ArrayList<>();
	static ArrayList<String> farmRecords = new ArrayList<>();

	public static void reportMenu(Runnable homeCallBack){
		System.out.println("\n"+"=".repeat(100)+"\n");
		System.out.println(tools.center("REPORT MENU",100));
		System.out.println("\n"+"=".repeat(100)+"\n");
		System.out.print("1. Daily Report ("+time.toLocalDate()+")\n2. Weekly Report (Last 7 Days)\n3. Monthly Report ("+time.getMonth()+" "+time.getYear()+")\n4. Summary Report\n5. Statistics Summary\n0. Back\nOption: ");
		int option=-1;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine().trim();
				break;
			}catch(Exception e){
				System.out.println("Invalid input. Enter a number.");
				read.nextLine().trim();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			homeCallBack.run();
		}
		else{
			switch (option){
				case 1:
					tools.clearScreen();
					dailyReport(homeCallBack);
					break;
				case 2:
					tools.clearScreen();
					weeklyReport(homeCallBack);
					break;
				case 3:
					tools.clearScreen();
					monthlyReport(homeCallBack);
					break;
				case 4:
					tools.clearScreen();
					reportSummary(homeCallBack);
					break;
				case 5:
					tools.clearScreen();
					statsSummary(homeCallBack);
					break;
				/*case 6:
					tools.clearScreen();
					eggGraph(homeCallBack);
					break;*/
				default:
					tools.clearScreen();
					System.out.println("Invalid selection");
					reportMenu(homeCallBack);
					break;
			}
		}
	}
	public static void dailyReport(Runnable homeCallBack){
		// FIXED: Use correct table name (farm_records) and column name (created_at)
		Object[] lastRecord = tools.getRecordByDate("farm_records", "created_at", time.toLocalDate().toString());

		if (lastRecord != null){
			System.out.println("--- Daily Report ("+time.toLocalDate()+") ---");
			// FIXED: Correct indices for farm_records table
			// 0=record_id, 1=eggs_collected, 2=feeds_used, 3=death, 4=comment, 5=created_at
			int eggs = Integer.parseInt(lastRecord[1].toString());
			System.out.println("\nCrates(Eggs)\t: "+(eggs/30) + "(" +(eggs%30)+ ")" );
			System.out.println("Feed \t: "+lastRecord[2]);
			System.out.println("Death\t: "+lastRecord[3]);
			System.out.println("Comment\t: "+lastRecord[4]);
		} else {
			System.out.println("No record found.");
		}

		System.out.println("\n0. Back");
		System.out.print("Option: ");
		int option = tools.getPositiveIntInput("");
		if(option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		} else {
			tools.clearScreen();
			System.out.print("Invalid Input");
			dailyReport(homeCallBack);
		}
	}
	public static void weeklyReport(Runnable homeCallBack){
		ArrayList<ArrayList<Object>> allRecords = tools.readTable("farm_records");
		ArrayList<ArrayList<Object>> weeklyRecords = new ArrayList<>();
		
		for(int i=0;i<7;i++){
			String dateToCheck = time.toLocalDate().minusDays(i).toString();
			
			for(ArrayList<Object> rec : allRecords){
				if(rec.size() > 5){ // Make sure record has created_at at index 5
					String recordDate = rec.get(5).toString().split(" ")[0];
					if(recordDate.equals(dateToCheck)){
						weeklyRecords.add(rec);
						break;
					}
				}
			}
		}

		int totalEggs=0,totalDeaths=0,averageEgg;
		double totalFeeds=0;

		for(ArrayList<Object> r : weeklyRecords){
			if(r.size() > 3){ // Check if record has enough data
				totalEggs+=Integer.parseInt(r.get(2).toString());
				totalFeeds+=Double.parseDouble(r.get(4).toString());
				totalDeaths+=Integer.parseInt(r.get(5).toString());
			}
		}

		averageEgg = (weeklyRecords.size() > 0) ? totalEggs / weeklyRecords.size() : 0;

		System.out.println("--- Weekly Report (Last "+weeklyRecords.size()+" Days) ---");
		System.out.println("\nTotal Crates(Eggs)\t: "+totalEggs/30 + "(" +totalEggs%30+ ")" );
		System.out.println("Total Feeds\t: "+totalFeeds);
		System.out.println("Total Deaths\t: "+totalDeaths);
		System.out.println("Average Crates(Eggs)/Days\t: "+ (averageEgg/30) + "(" +(averageEgg%30)+ ")" );

		System.out.print("\n0. Back\nOption: ");
		int option = tools.getPositiveIntInput("");
		if(option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		} else {
			tools.clearScreen();
			System.out.print("Invalid Input");
			weeklyReport(homeCallBack);
		}
	}	
	public static void monthlyReport(Runnable homeCallBack){
		ArrayList<ArrayList<Object>> allRecords = tools.readTable("farm_records");
		ArrayList<ArrayList<Object>> monthlyRecords = new ArrayList<>();
		
		for(ArrayList<Object> r : allRecords){
			if(r.size() > 5){ // Check if record has created_at
				String recordDate = r.get(1).toString();
				//System.out.println(r);
				if(tools.monthsOfTheYear(recordDate).equals(time.getMonth().toString())){
					monthlyRecords.add(r);
				}
			}
		}

		if(monthlyRecords.isEmpty()){
			System.out.println("No record found.");
		} else {
			int totalEggs=0,totalDeaths=0,highestEgg=0;
			int lowestEgg = monthlyRecords.size() > 0 ? Integer.parseInt(monthlyRecords.get(0).get(2).toString()) : 0;
			double totalFeeds=0;
			String highestEggDate="",lowestEggDate="";
			
			if(monthlyRecords.size() > 0){
				lowestEggDate = monthlyRecords.get(0).get(5).toString().split(" ")[0];
			}

			for(ArrayList<Object> r : monthlyRecords){
				if(r.size() > 3){
					int eggs = Integer.parseInt(r.get(2).toString());
					totalEggs+=eggs;
					totalFeeds+=Double.parseDouble(r.get(4).toString());
					totalDeaths+=Integer.parseInt(r.get(5).toString());
					
					String currentDate = r.get(1).toString().split(" ")[0];
					
					if(eggs>highestEgg){ 
						highestEgg=eggs; 
						highestEggDate=currentDate;
					}
					if(eggs<lowestEgg){ 
						lowestEgg=eggs; 
						lowestEggDate=currentDate;
					}
				}
			}

			System.out.println("--- Monthly Report ("+time.getMonth()+" "+time.getYear()+") ---");
			System.out.println("\nTotal Crates(Eggs)\t: "+totalEggs/30 + "(" +totalEggs%30+ ")" );
			System.out.println("Total Feeds\t\t: "+totalFeeds);
			System.out.println("Total Deaths\t\t: "+totalDeaths);
			System.out.println("\nBest Production Day\t: "+highestEggDate+" ("+highestEgg/30+" crate(s) "+highestEgg%30+" egg(s))");
			System.out.println("Worst Production Day\t: "+lowestEggDate+" ("+lowestEgg/30+" crate(s) "+lowestEgg%30+" egg(s))");
		}

		System.out.print("\n0. Back\nOption: ");
		int option = tools.getPositiveIntInput("");
		if(option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		} else {
			tools.clearScreen();
			System.out.print("Invalid Input");
			monthlyReport(homeCallBack);
		}
	}
	public static void reportSummary(Runnable homeCallBack){
		ArrayList<ArrayList<Object>> records = tools.readTable("farm_records");

		int todayEgg=0,thisWeekEgg=0,thisMonthEgg=0;

		for(ArrayList<Object> r : records){
			if(r.size() > 5){
				String recordDate = r.get(1).toString();
				
				if(recordDate.equals(time.toLocalDate().toString())) {
					todayEgg = Integer.parseInt(r.get(2).toString());
				}

				for(int i=0;i<7;i++){
					if(recordDate.equals(time.toLocalDate().minusDays(i).toString())){
						thisWeekEgg+=Integer.parseInt(r.get(2).toString());
					}
				}

				if(tools.monthsOfTheYear(recordDate).equals(time.getMonth().toString())){
					thisMonthEgg+=Integer.parseInt(r.get(2).toString());
				}
			}
		}

		System.out.println("--- Summary Report ---");
		System.out.println("\nToday\t: "+todayEgg/30+" crate(s) "+todayEgg%30+" egg(s)");
		System.out.println("This Week\t: "+thisWeekEgg/30+" crate(s) "+thisWeekEgg%30+" egg(s)");
		System.out.println("This Month\t: "+thisMonthEgg/30+" crate(s) "+thisMonthEgg%30+" egg(s)");
		System.out.println("\n0. Back");

		System.out.print("Option: ");
		int option = tools.getPositiveIntInput("");
		if(option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		} else {
			tools.clearScreen();
			System.out.print("Invalid Input");
			reportSummary(homeCallBack);
		}
	}
	public static void statsSummary(Runnable homeCallBack){
		ArrayList<ArrayList<Object>> allRecords = tools.readTable("farm_records");
		ArrayList<ArrayList<Object>> monthlyRecords = new ArrayList<>();
		
		for(ArrayList<Object> r : allRecords){
			if(r.size() > 5){
				String recordDate = r.get(1).toString();
				if(tools.monthsOfTheYear(recordDate).equals(time.getMonth().toString())){
					monthlyRecords.add(r);
				}
			}
		}

		if(monthlyRecords.isEmpty()){
			System.out.println("No record found.");
		} else {
			int highestEgg=0;
			int lowestEgg = monthlyRecords.size() > 0 ? Integer.parseInt(monthlyRecords.get(0).get(2).toString()) : 0;
			String highestEggDate="",lowestEggDate="";
			
			if(monthlyRecords.size() > 0){
				lowestEggDate = monthlyRecords.get(0).get(1).toString();
			}

			for(ArrayList<Object> r : monthlyRecords){
				if(r.size() > 1){
					int eggs = Integer.parseInt(r.get(2).toString());
					String currentDate = r.get(1).toString();
					
					if(eggs>highestEgg){ 
						highestEgg=eggs; 
						highestEggDate=currentDate;
					}
					if(eggs<lowestEgg){ 
						lowestEgg=eggs; 
						lowestEggDate=currentDate;
					}
				}
			}

			System.out.println("--- Statistics Summary ---");
			System.out.println("\nHighest Production: \t: "+highestEgg/30 + " crate(s) "+ highestEgg%30+" egg(s) on "+highestEggDate);
			System.out.println("Lowest Production: \t: "+lowestEgg/30 + " crate(s) "+lowestEgg%30 +" egg(s) on "+lowestEggDate);
		}

		System.out.print("\n0. Back\nOption: ");
		int option = tools.getPositiveIntInput("");
		if(option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		} else {
			tools.clearScreen();
			System.out.print("Invalid Input");
			statsSummary(homeCallBack);
		}
	}
	public static void eggGraph(Runnable homeCallBack) {
		// Fetch all farm records from the database
		Object[][] records = tools.getAllRecords("farm_records");
		
		if (records.length == 0) {
			System.out.println("No records found.");
			System.out.println("-".repeat(100));
			return;
		}
		System.out.println("Egg Production Graph (Last 7 Days)");
		System.out.println("\n" + "-".repeat(100));
		
		// Get today's date
		LocalDate today = LocalDate.now();
		
		// Create a map to store eggs per day for last 7 days
		Map<String, Integer> dailyEggs = new LinkedHashMap<>();
		for (int i = 6; i >= 0; i--) {
			LocalDate date = today.minusDays(i);
			dailyEggs.put(date.toString(), 0);
		}
		
		// Calculate eggs for each of the last 7 days
		for (Object[] record : records) {
			// Assuming record structure: 
			// [0]=record_id, [1]=record_date, [2]=eggs_collected, [3]=feeds_used, [4]=death, [5]=comment, [6]=created_by, [7]=created_at
			
			String recordDate;
			if (record.length > 7 && record[7] != null) {
				// Use created_at if available
				recordDate = record[7].toString().split(" ")[0];
			} else if (record.length > 1 && record[1] != null) {
				// Use record_date
				recordDate = record[1].toString();
			} else {
				continue;
			}
			
			// Only process if date is within last 7 days
			if (dailyEggs.containsKey(recordDate)) {
				int eggs = 0;
				if (record.length > 2 && record[2] != null) {
					try {
						eggs = Integer.parseInt(record[2].toString());
					} catch (NumberFormatException e) {
						eggs = 0;
					}
				}
				// Add to existing count for that day
				dailyEggs.put(recordDate, dailyEggs.get(recordDate) + eggs);
			}
		}
		
		// Find max eggs for scaling
		int maxEggs = 0;
		for (int eggs : dailyEggs.values()) {
			if (eggs > maxEggs) maxEggs = eggs;
		}
		
		// Print graph
		System.out.println("Date\t\t| Eggs | Graph");
		System.out.println("-".repeat(100));
		
		for (Map.Entry<String, Integer> entry : dailyEggs.entrySet()) {
			String date = entry.getKey();
			int eggs = entry.getValue();
			
			// Calculate bar length (scale to 100 characters max)
			int barLength = maxEggs > 0 ? (int) ((double) eggs / maxEggs * 100) : 0;
			
			// Format: Date | Eggs | Bar Graph
			System.out.printf("%-10s \t| %5d | %s%n", 
				date, 
				eggs,
				"*".repeat(barLength));
		}
		
		System.out.println("-".repeat(100));
		System.out.println("Scale: 1 '*' = " + Math.max(1, maxEggs / 100) + " eggs");
		System.out.println("\n" + "-".repeat(100) + "\n");
	}
}