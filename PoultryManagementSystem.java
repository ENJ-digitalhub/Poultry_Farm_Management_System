//custom packages
import PF.*;
//for accepting input
import java.util.Scanner;
//for date and time
import java.time.LocalDateTime;

public class PoultryManagementSystem{
	static LocalDateTime time = LocalDateTime.now();
	static Scanner read = new Scanner(System.in);
	static SystemUtils tools = new SystemUtils();
	static FarmRecord record = new FarmRecord();
	static User user = new User();
	static Report report = new Report();
	
	public void startupPage(){
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(tools.center("Poultry Farm Management System",50)+"\n");
		System.out.println(tools.center("Date: "+time.toLocalDate()+"\tTime: "+time.toLocalTime(),50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.print("1. Login\n2. Register\n0. Exit\nOption: ");
		int option=read.nextInt();
		if (option==0){
			tools.clearScreen();
			tools.exit();
		}
		else{
			switch (option){
				//Login
				case 1:
					tools.clearScreen();
					user.login(this::startupPage);
					break;
				//Register
				case 2:
					tools.clearScreen();
					user.register();
					break;
				//For wrong input
				default:
					tools.clearScreen();
					System.out.println("Invalid selection");
					startupPage();
					break;
			}
		}
	}
	public void home(){
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(tools.center("MAIN MENU",50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		/*report.eggGraph();*/
		System.out.print("1. Farm Management\n2. Stock Management\n3. Report\n0. Logout\nOption: ");
		int option=read.nextInt();
		if (option==0){
			tools.clearScreen();
			startupPage();
		}
		else{
			switch (option){
				//Record farm data
				case 1:
					tools.clearScreen();
					record.farmMenu(this::home);
					break;
				//Stock
				case 2:
					tools.clearScreen();
					Inventory.stockMenu(this::home);
					break;
				//Report
				case 3:
					tools.clearScreen();
					report.reportMenu(this::home);
					break;
				//For wrong input
				default:
					tools.clearScreen();
					System.out.println("Invalid selection");
					startupPage();
					break;
			}
		}
	}
	public static void main(String[] args){
		PoultryManagementSystem PSM = new PoultryManagementSystem();
		//startupPage();
		PSM.home();
	}
}