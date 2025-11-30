package PF;
import PF.SystemUtils;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Report{
	static LocalDateTime time = LocalDateTime.now();
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner (System.in);
	
	public static void reportMenu(Runnable homeCallBack){
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(tools.center("REPORT MENU",50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.print("1. Daily Report ("+time.toLocalDate()+")\n2. Weekly Report (Last 7 Days)\n3. Monthly Report ("+time.getMonth()+" "+time.getYear()+")\n4. Summary Report\n5. Egg Production Graph\n6. Statistics Summary\n7. Back\nOption: ");
		int option=read.nextInt();
		switch (option){
			//Daily Report
			case 1:
				tools.clearScreen();
				dailyReport();
				break;
			//Weekly Report
			case 2:
				tools.clearScreen();
				weeklyReport();
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
			//BACK
			case 7:
				tools.clearScreen();
				homeCallBack.run();
				break;
			//For wrong input
			default:
				tools.clearScreen();
				System.out.println("Invalid selection");
				reportMenu(homeCallBack);
				break;
		}
	}
	public static void dailyReport(){
		System.out.println("Under Construction");
	}
	public static void weeklyReport(){
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