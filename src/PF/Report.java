package PF;
import PF.SystemUtils;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;

public class Report{
	static LocalDateTime time = LocalDateTime.now();
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner(System.in);
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
		Object[] lastRecord = tools.getRecordByDate("FarmRecord","date",time.toLocalDate().toString());

		if (lastRecord != null){
			System.out.println("--- Daily Report ("+lastRecord[0]+") ---");
			System.out.println("\nCrates(Eggs)\t: "+Integer.parseInt(lastRecord[1].toString())/30 + "(" +Integer.parseInt(lastRecord[1].toString())%30+ ")" );
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
		ArrayList<Object[]> records = new ArrayList<>();
		for(int i=0;i<7;i++){
			Object[] rec = tools.getRecordByDate("FarmRecord","date",time.toLocalDate().minusDays(i).toString());
			if(rec != null) records.add(rec);
		}

		int totalEggs=0,totalDeaths=0,averageEgg;
		double totalFeeds=0;

		for(Object[] r : records){
			totalEggs+=Integer.parseInt(r[1].toString());
			totalFeeds+=Double.parseDouble(r[2].toString());
			totalDeaths+=Integer.parseInt(r[3].toString());
		}

		averageEgg = (records.size() > 0) ? totalEggs / records.size() : 0;

		System.out.println("--- Weekly Report (Last "+records.size()+" Days) ---");
		System.out.println("\nTotal Crates(Eggs)\t: "+totalEggs/30 + "(" +totalEggs%30+ ")" );
		System.out.println("Total Feeds\t: "+totalFeeds);
		System.out.println("Total Deaths\t: "+totalDeaths);
		System.out.println("Average Crates(Eggs)/Days\t: "+String.format("%.2f",averageEgg/30 + "(" +averageEgg%30+ ")" ));

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
		ArrayList<Object[]> records = new ArrayList<>();
		for(Object[] r : tools.readTable("FarmRecord")){
			if(tools.monthsOfTheYear(r[0].toString()).equals(time.getMonth().toString())){
				records.add(r);
			}
		}

		if(records.isEmpty()){
			System.out.println("No record found.");
		} else {
			int totalEggs=0,totalDeaths=0,highestEgg=0,lowestEgg=Integer.parseInt(records.get(0)[1].toString());
			double totalFeeds=0;
			String highestEggDate="",lowestEggDate=records.get(0)[0].toString();

			for(Object[] r : records){
				int eggs = Integer.parseInt(r[1].toString());
				totalEggs+=eggs;
				totalFeeds+=Double.parseDouble(r[2].toString());
				totalDeaths+=Integer.parseInt(r[3].toString());

				if(eggs>highestEgg){ highestEgg=eggs; highestEggDate=r[0].toString();}
				if(eggs<lowestEgg){ lowestEgg=eggs; lowestEggDate=r[0].toString();}
			}

			System.out.println("--- Monthly Report ("+time.getMonth()+" "+time.getYear()+") ---");
			System.out.println("\nTotal Crates(Eggs)\t: "+totalEggs/30 + "(" +totalEggs%30+ ")" );
			System.out.println("Total Feeds\t: "+totalFeeds);
			System.out.println("Total Deaths\t: "+totalDeaths);
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
		ArrayList<Object[]> records = tools.readTable("FarmRecord");

		int todayEgg=0,thisWeekEgg=0,thisMonthEgg=0;

		for(Object[] r : records){
			if(r[0].toString().equals(time.toLocalDate().toString())) todayEgg=Integer.parseInt(r[1].toString());

			for(int i=0;i<7;i++){
				if(r[0].toString().equals(time.toLocalDate().minusDays(i).toString())){
					thisWeekEgg+=Integer.parseInt(r[1].toString());
				}
			}

			if(tools.monthsOfTheYear(r[0].toString()).equals(time.getMonth().toString())){
				thisMonthEgg+=Integer.parseInt(r[1].toString());
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
		ArrayList<Object[]> records = new ArrayList<>();
		for(Object[] r : tools.readTable("FarmRecord")){
			if(tools.monthsOfTheYear(r[0].toString()).equals(time.getMonth().toString())){
				records.add(r);
			}
		}

		if(records.isEmpty()){
			System.out.println("No record found.");
		} else {
			int highestEgg=0,lowestEgg=Integer.parseInt(records.get(0)[1].toString());
			String highestEggDate="",lowestEggDate=records.get(0)[0].toString();

			for(Object[] r : records){
				int eggs = Integer.parseInt(r[1].toString());
				if(eggs>highestEgg){ highestEgg=eggs; highestEggDate=r[0].toString();}
				if(eggs<lowestEgg){ lowestEgg=eggs; lowestEggDate=r[0].toString();}
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
}
