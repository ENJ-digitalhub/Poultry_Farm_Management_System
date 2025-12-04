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
		System.out.print("1. Daily Report ("+time.toLocalDate()+")\n2. Weekly Report (Last 7 Days)\n3. Monthly Report ("+time.getMonth()+" "+time.getYear()+")\n4. Summary Report\n5. Statistics Summary\n99. Back\nOption: ");
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
					monthlyReport(homeCallBack);
					break;
				//Report Summary
				case 4:
					tools.clearScreen();
					reportSummary(homeCallBack);
					break;
				//stats Summary
				case 5:
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
			System.out.println("--- Daily Report ("+lastInRecord[0]+") ---");
			System.out.println("\nEggs\t: "+lastInRecord[1]);
			System.out.println("Feed \t: "+lastInRecord[2]);
			System.out.println("Death\t: "+lastInRecord[3]);
			System.out.println("Comment\t: "+lastInRecord[4]);
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
		
		for(count=1;count<=7;){
			//stops the loop if a week record is not complete
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
			count++;
		}
			avarageEggs=totalEggs/(count-1);
			
			System.out.println("--- Weekly Report (Last "+(count-1)+" Days) ---");
			System.out.println("\nTotal Eggs\t: "+totalEggs);
			System.out.println("Total Feeds\t: "+totalFeeds);
			System.out.println("Total Deaths\t: "+totalDeaths);
			System.out.println("Average Eggs/Days\t: "+String.format("%.2f",avarageEggs));
			System.out.print("\n99. Back\nOption: ");
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
	}
	public static void monthlyReport(Runnable homeCallBack){
		records = tools.reader(tools.farmRecord);
		String lowestEggDate="",highestEggDate="";
		double totalEggs=0,highestEgg=0,lowestEgg=0,totalFeeds=0,totalDeaths=0;
		int count=1;
		boolean done=true;
		while(done ){
				if (records.size()<count){
				break;
			}		
			String lastRecord = records.get(records.size()-count).replace("[","").replace("]",""); 
			String [] lastInRecord = lastRecord.split(",\\s");
			if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){ 
				if (Double.parseDouble(lastInRecord[1])>highestEgg){
					highestEgg=Double.parseDouble(lastInRecord[1]);
					highestEggDate=lastInRecord[0];
				}
				if (count==1){
					lowestEgg=Double.parseDouble(lastInRecord[1]);
					lowestEggDate=lastInRecord[0];
				}
				if(Double.parseDouble(lastInRecord[1])<lowestEgg){
					lowestEgg=Double.parseDouble(lastInRecord[1]);
					lowestEggDate=lastInRecord[0];
				}
				
				totalEggs+=Double.parseDouble(lastInRecord[1]);
				totalFeeds+=Double.parseDouble(lastInRecord[2]);
				totalDeaths+=Double.parseDouble(lastInRecord[3]);
			}
			else{
				done = false;
			}
			count++;
		}
			System.out.println("--- Monthly Report ("+time.getMonth()+" "+time.getYear()+") ---");
			System.out.println("\nTotal Eggs\t: "+totalEggs);
			System.out.println("Total Feeds\t: "+totalFeeds);
			System.out.println("Total Deaths\t: "+totalDeaths);
			System.out.println("\nBest Production Day\t: "+highestEggDate+" ("+highestEgg+" eggs)");
			System.out.println("Worst Production Day\t: "+lowestEggDate+" ("+lowestEgg+" eggs)");
			System.out.println("\n99. Back");
			int option = read.nextInt();
			if (option==99){
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
		records = tools.reader(tools.farmRecord);
		String today="";
		double todayEggs=0,thisWeekEgg=0,thisMonthEgg=0;
		int count=1;
		boolean done=true;
		while(done ){
			if (records.size()<count){
				break;
			}		
			String lastRecord = records.get(records.size()-count).replace("[","").replace("]",""); 
			String [] lastInRecord = lastRecord.split(",\\s");
			//today
			if (lastInRecord[0].equals(time.toLocalDate().toString())){
				todayEggs=Double.parseDouble(lastInRecord[1]);
			}
			// this week
			thisWeekEgg = 0.0;
			int i = 0;
			int counter = 1;
			while (i <7 && counter <= records.size()) {
				String lastRecordWeek = records.get(records.size() - counter).replace("[", "").replace("]", "");
				String[] lastInRecordWeek = lastRecordWeek.split(",\\s");
				int recordYear = Integer.parseInt(lastInRecordWeek[0].substring(0, 4));
				int recordMonth = Integer.parseInt(lastInRecordWeek[0].substring(5, 7));
				int recordDay = Integer.parseInt(lastInRecordWeek[0].substring(8, 10));
				String target = time.toLocalDate().minusDays(i).toString();
				int targetYear = Integer.parseInt(target.substring(0, 4));
				int targetMonth = Integer.parseInt(target.substring(5, 7));
				int targetDay = Integer.parseInt(target.substring(8, 10));
				
				if (recordYear == targetYear &&
					recordMonth == targetMonth &&
					recordDay == targetDay) {
					thisWeekEgg += Double.parseDouble(lastInRecordWeek[1]);
					counter++;
					i++;
				}
				else if (recordYear < targetYear || 
						   (recordYear == targetYear && recordMonth < targetMonth) ||
						   (recordYear == targetYear && recordMonth == targetMonth && recordDay < targetDay)) {
					i++; 
				}
				else{
					counter++;
				}
			}
			//this month
			if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){ 
				thisMonthEgg+=Double.parseDouble(lastInRecord[1]);
			}
			else{
				done = false;
			}
			count++;
			}
			System.out.println("--- Summary Report ---");
			System.out.println("\nToday Egg\t: "+todayEggs);
			System.out.println("This Week\t: "+thisWeekEgg);
			System.out.println("This Month\t: "+thisMonthEgg);
			System.out.println("\n99. Back");
			int option = read.nextInt();
			if (option==99){
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
		records = tools.reader(tools.farmRecord);
		String today="";
		double todayEggs=0,thisWeekEgg=0,thisMonthEgg=0;
		int count=1;
		boolean done=true;
		System.out.println("Egg Production Graph(Last 7 Days)");
		System.out.println("\n"+"-".repeat(50)+"\n");
		while(done ){
			if (7<count){
				break;
			}		
			String lastRecord = records.get(records.size()-count).replace("[","").replace("]",""); 
			String [] lastInRecord = lastRecord.split(",\\s");
			thisWeekEgg = 0.0;
			System.out.println(lastInRecord[0]+"\t|\t"+"*".repeat((int)Double.parseDouble(lastInRecord[1]))+" ("+(int)Double.parseDouble(lastInRecord[1])+")");
			count++;
			}
		System.out.println("\n"+"-".repeat(50)+"\n");
	}
	public static void statsSummary(){
		/*records = tools.reader(tools.farmRecord);
		String lowestEggDate="",highestEggDate="";
		double totalEggs=0,highestEgg=0,lowestEgg=0,totalFeeds=0,totalDeaths=0;
		int count=1;
		boolean done=true;
		while(done ){
				if (records.size()<count){
				break;
			}		
			String lastRecord = records.get(records.size()-count).replace("[","").replace("]",""); 
			String [] lastInRecord = lastRecord.split(",\\s");
			if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){ 
				if (Double.parseDouble(lastInRecord[1])>highestEgg){
					highestEgg=Double.parseDouble(lastInRecord[1]);
					highestEggDate=lastInRecord[0];
				}
				if (count==1){
					lowestEgg=Double.parseDouble(lastInRecord[1]);
					lowestEggDate=lastInRecord[0];
				}
				if(Double.parseDouble(lastInRecord[1])<lowestEgg){
					lowestEgg=Double.parseDouble(lastInRecord[1]);
					lowestEggDate=lastInRecord[0];
				}
				
				totalEggs+=Double.parseDouble(lastInRecord[1]);
				totalFeeds+=Double.parseDouble(lastInRecord[2]);
				totalDeaths+=Double.parseDouble(lastInRecord[3]);
			}
			else{
				done = false;
			}
			count++;
		}
			System.out.println("--- Monthly Report ("+time.getMonth()+" "+time.getYear()+") ---");
			System.out.println("\nTotal Eggs\t: "+totalEggs);
			System.out.println("Total Feeds\t: "+totalFeeds);
			System.out.println("Total Deaths\t: "+totalDeaths);
			System.out.println("\nBest Production Day\t: "+highestEggDate+" ("+highestEgg+" eggs)");
			System.out.println("Worst Production Day\t: "+lowestEggDate+" ("+lowestEgg+" eggs)");
			System.out.println("\n99. Back");
			int option = read.nextInt();
			if (option==99){
				tools.clearScreen();
				reportMenu(homeCallBack);
			}
			else{
				tools.clearScreen();
				System.out.print("Invalid Input");
				monthlyReport(homeCallBack);
			}
		System.out.println("Under Construction");*/
	}
} 