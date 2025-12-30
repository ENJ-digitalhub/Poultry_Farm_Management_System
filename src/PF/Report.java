package PF;
import PF.SystemUtils;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Report{
	static LocalDateTime time = LocalDateTime.now();
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner (System.in);
	static ArrayList<String> inventoryRecord = new ArrayList<>();
	static ArrayList<String> farmRecords = new ArrayList<>();

	public static void reportMenu(Runnable homeCallBack){
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(tools.center("REPORT MENU",50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.print("1. Daily Report ("+time.toLocalDate()+")\n2. Weekly Report (Last 7 Days)\n3. Monthly Report ("+time.getMonth()+" "+time.getYear()+")\n4. Summary Report\n5. Statistics Summary\n0. Back\nOption: ");
		int option=-1;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input. Enter a number.");
				read.nextLine();
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
				default:
					tools.clearScreen();
					System.out.println("Invalid selection");
					reportMenu(homeCallBack);
					break;
			}
		}
	}
	public static void dailyReport(Runnable homeCallBack){
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());

		String lastRecord = farmRecords.get(farmRecords.size()-1).replace("[","").replace("]","");
		String[] lastInRecord = lastRecord.split(",\\s");
		if (lastInRecord[0].equals(time.toLocalDate().toString())){
			System.out.println("--- Daily Report ("+lastInRecord[0]+") ---");
			System.out.println("\nCrates(Eggs)\t: "+Integer.parseInt(lastInRecord[1])/30 + "(" +Integer.parseInt(lastInRecord[1])%30+ ")" );
			System.out.println("Feed \t: "+lastInRecord[2]);
			System.out.println("Death\t: "+lastInRecord[3]);
			System.out.println("Comment\t: "+lastInRecord[4]);
		}
		else{
			System.out.println("No record found.");
		}

		System.out.println("\n0. Back");
		System.out.print("Option: ");
		int option=-1;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input. Enter a number.");
				read.nextLine();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		}
		else{
			tools.clearScreen();
			System.out.print("Invalid Input");
			dailyReport(homeCallBack);
		}
	}
	public static void weeklyReport(Runnable homeCallBack){
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());
		
		int totalEggs=0,totalDeaths=0,averageEgg;
		double totalFeeds=0;
		int count;

		for(count=1;count<=7;){
			if (farmRecords.size()<count){
				break;
			}
			String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
			String[] lastInRecord = lastRecord.split(",\\s");

			if (lastInRecord[0].equals(time.toLocalDate().minusDays(count-1).toString())){
				totalEggs+=Integer.parseInt(lastInRecord[1]);
				totalFeeds+=Double.parseDouble(lastInRecord[2]);
				totalDeaths+=Integer.parseInt(lastInRecord[3]);
			}
			count++;
		}
		averageEgg=totalEggs/(count-1);

		System.out.println("--- Weekly Report (Last "+(count-1)+" Days) ---");
		System.out.println("\nTotal Crates(Eggs)\t: "+totalEggs/30 + "(" +totalEggs%30+ ")" );
		System.out.println("Total Feeds\t: "+totalFeeds);
		System.out.println("Total Deaths\t: "+totalDeaths);
		System.out.println("Average Crates(Eggs)/Days\t: "+String.format("%.2f",averageEgg/30 + "(" +averageEgg%30+ ")" ));
		System.out.print("\n0. Back\nOption: ");
		int option=-1;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input. Enter a number.");
				read.nextLine();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		}
		else{
			tools.clearScreen();
			System.out.print("Invalid Input");
			weeklyReport(homeCallBack);
		}
	}
	public static void monthlyReport(Runnable homeCallBack){
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());

		String lowestEggDate="",highestEggDate="";
		int totalEggs=0,highestEgg=0,lowestEgg=0,totalDeaths=0;
		double totalFeeds=0;
		int count=1;
		boolean done=true;

		while(done ){
			if (farmRecords.size()<count){
				break;
			}
			String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
			String[] lastInRecord = lastRecord.split(",\\s");

			if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){
				if (Integer.parseInt(lastInRecord[1])>highestEgg){
					highestEgg=Integer.parseInt(lastInRecord[1]);
					highestEggDate=lastInRecord[0];
				}
				if (count==1){
					lowestEgg=Integer.parseInt(lastInRecord[1]);
					lowestEggDate=lastInRecord[0];
				}
				if(Integer.parseInt(lastInRecord[1])<lowestEgg){
					lowestEgg=Integer.parseInt(lastInRecord[1]);
					lowestEggDate=lastInRecord[0];
				}

				totalEggs+=Integer.parseInt(lastInRecord[1]);
				totalFeeds+=Double.parseDouble(lastInRecord[2]);
				totalDeaths+=Integer.parseInt(lastInRecord[3]);
			}
			else{
				done = false;
			}
			count++;
		}

		System.out.println("--- Monthly Report ("+time.getMonth()+" "+time.getYear()+") ---");
		System.out.println("\nTotal Crates(Eggs)\t: "+totalEggs/30 + "(" +totalEggs%30+ ")" );
		System.out.println("Total Feeds\t: "+totalFeeds);
		System.out.println("Total Deaths\t: "+totalDeaths);
		System.out.println("\nBest Production Day\t: "+highestEggDate+" ("+highestEgg/30+" crate(s) "+highestEgg%30+" egg(s))");
		System.out.println("Worst Production Day\t: "+lowestEggDate+" ("+lowestEgg/30+" crate(s) "+lowestEgg%30+" egg(s))");
		System.out.println("\n0. Back");

		System.out.print("Option: ");
		int option=-1;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input. Enter a number.");
				read.nextLine();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		}
		else{
			tools.clearScreen();
			System.out.print("Invalid Input");
			monthlyReport(homeCallBack);
		}
	}
	public static void reportSummary(Runnable homeCallBack){
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());

		String today="";
		int todayEgg=0,thisWeekEgg=0,thisMonthEgg=0;
		int count=1;
		boolean done=true;

		while(done ){
			if (farmRecords.size()<count){
				break;
			}

			String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
			String[] lastInRecord = lastRecord.split(",\\s");

			if (lastInRecord[0].equals(time.toLocalDate().toString())){
				todayEgg=Integer.parseInt(lastInRecord[1]);
			}

			thisWeekEgg = 0;
			int i = 0;
			int counter = 1;
			while (i <7 && counter <= farmRecords.size()) {
				String lastRecordWeek = farmRecords.get(farmRecords.size() - counter).replace("[", "").replace("]", "");
				String[] lastInRecordWeek = lastRecordWeek.split(",\\s");

				if (lastInRecordWeek[0].equals(time.toLocalDate().minusDays(i).toString())) {
					thisWeekEgg += Integer.parseInt(lastInRecordWeek[1]);
				}
				counter++;
				i++;
			}

			if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){
				thisMonthEgg+=Integer.parseInt(lastInRecord[1]);
			}
			else{
				done = false;
			}
			count++;
		}

		System.out.println("--- Summary Report ---");
		System.out.println("\nToday\t: "+todayEgg/30+" crate(s) "+todayEgg%30+" egg(s)");
		System.out.println("This Week\t: "+thisWeekEgg/30+" crate(s) "+thisWeekEgg%30+" egg(s)");
		System.out.println("This Month\t: "+thisMonthEgg/30+" crate(s) "+thisMonthEgg%30+" egg(s)");
		System.out.println("\n0. Back");

		System.out.print("Option: ");
		int option=-1;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input. Enter a number.");
				read.nextLine();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		}
		else{
			tools.clearScreen();
			System.out.print("Invalid Input");
			reportSummary(homeCallBack);
		}
	}
	public static void eggGraph(){
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());
		int count=1;

		System.out.println("Egg Production Graph(Last 7 Days)");
		System.out.println("\n"+"-".repeat(50)+"\n");

		while(count<=7){
			if (farmRecords.size()<count) break;

			String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
			String[] lastInRecord = lastRecord.split(",\\s");

			System.out.println(lastInRecord[0]+"\t|\t"+"*".repeat(Integer.parseInt(lastInRecord[1]))+" ("+Integer.parseInt(lastInRecord[1])/30+")");
			count++;
		}
		System.out.println("\n"+"-".repeat(50)+"\n");
	}
	public static void statsSummary(Runnable homeCallBack){
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());

		String lowestEggDate="XXXX-XX-XX",highestEggDate="XXXX-XX-XX";
		int highestEgg=0,lowestEgg=0;
		int count=1;
		boolean done=true;

		while(done ){
			if (farmRecords.size()<count)break;
			String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
			String[] lastInRecord = lastRecord.split(",\\s");

			if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){
				int egg = Integer.parseInt(lastInRecord[1]);

				if (egg>highestEgg){
					highestEgg=egg;
					highestEggDate=lastInRecord[0];
				}
				if (count==1){
					lowestEgg=egg;
					lowestEggDate=lastInRecord[0];
				}
				if(egg<lowestEgg){
					lowestEgg=egg;
					lowestEggDate=lastInRecord[0];
				}
			}
			else{
				done = false;
			}
			count++;
		}

		System.out.println("--- Statistics Summary ---");
		System.out.println("\nHighest Production: \t: "+highestEgg/30 + "crate(s)"+ highestEgg%30+" egg(s) on "+highestEggDate);
		System.out.println("Lowest Production: \t: "+lowestEgg/30 + "crate(s)"+lowestEgg%30 +" egg(s) on "+lowestEggDate);
		System.out.println("\n0. Back");

		System.out.print("Option: ");
		int option=-1;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input. Enter a number.");
				read.nextLine();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			reportMenu(homeCallBack);
		}
		else{
			tools.clearScreen();
			System.out.print("Invalid Input");
			monthlyReport(homeCallBack);
		}
	}
}
