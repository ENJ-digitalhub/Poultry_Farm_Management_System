//for accepting input
import java.util.Scanner;
//for date and time
import java.time.LocalDateTime;

public class PoultryFarmManagementSystem{
	//Object declared static so in can be accessable for all methods
	static Scanner read = new Scanner(System.in);
	//This object is for the present time
	static LocalDateTime now = LocalDateTime.now();
	static boolean isConfirm=false;
	private static void credentials(){
		//Arrays that would store user's login data
		
	}
	public static String center(String s,double x){
		double padding = (x-s.length())/2;
		return " ".repeat((int)padding)+s+" ".repeat((int)padding);
	}
	public static void clearScreen() {
		/*\033 = ESC character
			[2J = clear entire screen
			[H = move cursor to top-left*/
        System.out.print("\033[H\033[2J");
		//flush() ensures the output happens immediately
        System.out.flush();
    }
	public static void exit(){
		clearScreen();
		System.out.println("Closing program . . . . .");
	}
	public static boolean confirm(double x){
		clearScreen();
		System.out.print("Confirm \""+x+" \"(Y,N)?");
		char confirm=read.next().charAt(0);
		//change character to lowercase.
		confirm=Character.toLowerCase(confirm);
		//confirming input
		if (confirm=='y'){
			clearScreen();
			System.out.println("Saving. . . . .");
			clearScreen();
			isConfirm=true	;	
		}
		else if(confirm=='n'){
			clearScreen();
			System.out.print(". . .");
			clearScreen();
			isConfirm=false;
		}
		return isConfirm;
	}
	public static void todayData(){
		/*int date=stamp.getDate();
		String addedBy=fullName;
		String addedAt= now;
		isConfirm=false;*/
		while(isConfirm==false){
			System.out.print("Input number of Eggs: "); 
			double eggNo = read.nextInt();
			confirm(eggNo);
		}
		while(isConfirm==false){
			System.out.print("Input number of Feeds used: ");
			double feedNo = read.nextInt();
			confirm(feedNo);
		}
		while(isConfirm==false){
			System.out.print("Input number of Deaths: ");
			double deathNo = read.nextInt();
			confirm(deathNo);
		}
		System.out.print("Comment(press Enter to continue . . .): ");
		read.nextLine();
	}
	public static void home(){}
	public static void recordEdit(){}
	public static void eggGraph(){}
	public static void register(){}
	public static void login(){}
	public static void startupPage(){
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(center("Poultry Farm Management System",50)+"\n");
		System.out.println(center("Date: "+now.toLocalDate()+"\tTime: "+now.toLocalTime(),50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.print("1. Login\n2. Register\n3. Exit\nOption: ");
		int option=read.nextInt();
		switch (option){
			//Login
			case 1:
				clearScreen();
				login();
				break;
			//Register
			case 2:
				clearScreen();
				register();
				break;
			//Exit
			case 3:
				clearScreen();
				exit();
				break;
			//For wrong input
			default:
				clearScreen();
				System.out.println("Invalid selection");
				startupPage();
				break;
		}
	}
	public static void main(String[] args){
		startupPage();
	}
}	