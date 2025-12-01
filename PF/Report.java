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
	static ArrayList <String> records = new ArrayList<>();
	
	public static void reportMenu(Runnable homeCallBack){
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(tools.center("REPORT MENU",50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.print("1. Daily Report ("+time.toLocalDate()+")\n2. Weekly Report (Last 7 Days)\n3. Monthly Report ("+time.getMonth()+" "+time.getYear()+")\n4. Summary Report\n5. Egg Production Graph\n6. Statistics Summary\n99. Back\nOption: ");
		int option=read.nextInt();
		if (option==99){
			tools.clearScreen();
			homeCallBack.run();
		}
		else{
			switch (option){
				//Daily Report
				case 1:
					tools.clearScreen();
					dailyReport(homeCallBack);
					break;
				//Weekly Report
				case 2:
					tools.clearScreen();
					weeklyReport(homeCallBack);
					break;
				//Monthly 
				case 3:
					tools.clearScreen();
					monthlyReport();
					break;
				//Report Summary
				case 4:
					tools.clearScreen();
					reportSummary();
					break;
				//Egg graph
				case 5:
					tools.clearScreen();
					eggGraph();
					break;
				//Stats
				case 6:
					tools.clearScreen();
					statsSummary();
					break;
				//For wrong input
				default:
					tools.clearScreen();
					System.out.println("Invalid selection");
					reportMenu(homeCallBack);
					break;
			}
		}
	}
	public static void dailyReport(Runnable homeCallBack){
		records = tools.reader(tools.farmRecord);
		String lastRecord = records.get(records.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		if (lastInRecord[0].equals(time.toLocalDate().toString())){
			System.out.println("Date: "+lastInRecord[0]);
			System.out.println("Number of Egg: "+lastInRecord[1]);
			System.out.println("Feed Used: "+lastInRecord[2]);
			System.out.println("Death: "+lastInRecord[3]);
			System.out.println("Comment: "+lastInRecord[4]);
		}
		else{
			System.out.println("No record found.");
		}
		System.out.println("\n99. Back");
		int option = read.nextInt();
		if (option==99){
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
		records = tools.reader(tools.farmRecord);
		double totalEggs=0,totalFeeds=0,totalDeaths=0,avarageEggs;
		int count;
		
		for(count=1;count<=7;count++){
			if (records.size()<count){
				break;
			}
			String lastRecord = records.get(records.size()-count).replace("[","").replace("]",""); 
			String [] lastInRecord = lastRecord.split(",\\s");
			if (lastInRecord[0].equals(time.toLocalDate().minusDays(count-1).toString())){
				totalEggs+=Double.parseDouble(lastInRecord[1]);
				totalFeeds+=Double.parseDouble(lastInRecord[2]);
				totalDeaths+=Double.parseDouble(lastInRecord[3]);
			}
		}
			avarageEggs=totalEggs/(count-1);
			
			System.out.println(time.toLocalDate().toString()+" -> "+time.toLocalDate().minusDays(count-2).toString());
			System.out.println("\nTotal Eggs: "+totalEggs);
			System.out.println("Total Feeds: "+totalFeeds);
			System.out.println("Total Deaths: "+totalDeaths);
			System.out.println("Average Eggs/Days: "+avarageEggs);
			System.out.println("\n99. Back");
			int option = read.nextInt();
			if (option==99){
				tools.clearScreen();
				reportMenu(homeCallBack);
			}
			else{
				tools.clearScreen();
				System.out.print("Invalid Input");
				weeklyReport(homeCallBack);
			}
		System.out.println("Under Construction");
	}
	public static void monthlyReport(){
		System.out.println("Under Construction");
	}
	public static void reportSummary(){
		System.out.println("Under Construction");
	}
	public static void eggGraph(){
		System.out.println("Under Construction");
	}
	public static void statsSummary(){
		System.out.println("Under Construction");
	}
}