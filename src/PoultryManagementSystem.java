
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
	static Inventory stock = new Inventory();
	static Report report = new Report();
	static Help help = new Help();
	
	public void startupPage(){
		 // Load letters from file
		tools.loadLetters("./data/letters.txt");
		System.out.println("\n"+"=".repeat(60)+"\n");
		// Print a word horizontally
		tools.printWordHorizontal("GRANTINO");
		//System.out.println("\n");
		System.out.println(tools.center("FARMS",60)+"\n");
		System.out.println("=".repeat(60)+"\n");
		System.out.println("Press ENTER...");
		read.nextLine();
		tools.clearScreen();
		
		System.out.println("\n"+"=".repeat(60)+"\n");
		System.out.println(tools.center("Poultry Farm Management System",50)+"\n");
		System.out.println(tools.center("Date: "+time.toLocalDate()+"\tTime: "+time.toLocalTime(),50));
		System.out.println("\n"+"=".repeat(60)+"\n");
		System.out.print("1. Login\n2. Register\n0. Exit\nOption: ");
		int option = -1;
		while(true){
			try{
				option = read.nextInt();
				read.nextLine().trim();
				break;
			}catch(Exception e){
				System.out.println("\nInvalid input! Please enter numbers only.");
				read.nextLine().trim();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			tools.exit();
		}
		else{
			switch (option){
				//Login
				case 1:
					tools.clearScreen();
					user.login(this::startupPage,this::home);
					break;
				//Register
				case 2:
					tools.clearScreen();
					user.register(this::startupPage,this::home);
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
		stock.checkLowStock();
		System.out.println("\n"+"=".repeat(60)+"\n");
		System.out.println(tools.center("POULTRY DASHBOARD", 50));
		System.out.println(tools.center("HOME MENU",50));
		System.out.println("\n"+"=".repeat(60)+"\n");
		System.out.print(
			"1. Farm Management\n"+
			"2. Stock Management\n"+
			"3. Report\n"+
			"4. Help\n"+
			"0. Logout\n"+
			"Option: "
		);
		int option = -1;
		while(true){
			try{
				option = read.nextInt();
				read.nextLine().trim();
				break;
			}catch(Exception e){
				System.out.println("\nInvalid input! Please enter numbers only.");
				read.nextLine().trim();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			System.out.println("Good-Bye "+
			User.userFirstName + 
			" " + 
			User.userLastName + 
			"!"+
			"Press ENTER to continue...");
			read.nextLine().trim();
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
				//Help
				case 4:
					tools.clearScreen();
					help.helpMenu(this::home);
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
		PSM.startupPage();
	}
}
