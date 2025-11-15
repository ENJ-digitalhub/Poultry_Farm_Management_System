//for accepting input
import java.util.Scanner;
//for date and time
import java.util.Date;

public class PoultryFarmManagementSystem{
	//Object declared static so in can be accessable for all methods
	static Scanner read = new Scanner(System.in);
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
		boolean isConfirm=false;
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
	public static void todayData(){}
	public static void home(){}
	public static void recordEdit(){}
	public static void eggGraph(){}
	public static void register(){}
	public static void login(){}
	public static void startupPage(){
		System.out.print("1. Login\n2. Register\n3. Exit\nOption>");
		int option=read.nextInt();
		switch (option){
			case 1:
				clearScreen();
				login();
				break;
			case 2:
				clearScreen();
				register();
				break;
			case 3:
				clearScreen();
				exit();
				break;
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