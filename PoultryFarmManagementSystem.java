//for accepting input
import java.util.Scanner;
//for date and time
import java.time.LocalDateTime;
//for writing into files
import java.io.FileWriter;
//for handling file writing error
import java.io.IOException;

public class PoultryFarmManagementSystem{
	//Object declared static so in can be accessable for all methods
	static Scanner read = new Scanner(System.in);
	//This object is for the present time
	static LocalDateTime now = LocalDateTime.now();
	static boolean isConfirm=false;
	static int sn;
	static String firstName;
	static String lastName;
	static String fullName;
	static String userName;
	static int pin;
	
	private static int encrypt(int pin){
		int valid=0;
		int d4=0;
		int d3=0;
		int d2=0;
		int d1=0;
		
		while (valid==0){
			int num = pin;
			int length = String.valueOf(num).length();
			
			if (length!=4){
				System.out.println("Invalid Input");
			}
			else{
				d4=num%10;
				d3=(num/10)%10;
				d2=(num/100)%10;
				d1=(num/1000)%10;
				
				d1=(d1+7)%10;
				d2=(d2+7)%10;
				d3=(d3+7)%10;
				d4=(d4+7)%10;
				
				d1+=d3;
				d3=d1-d3;
				d1-=d3;
				
				d2+=d4;
				d4=d2-d4;
				d2-=d4;
			}
		}
		int encryptedPin = (d1*1000)+(d2*100)+(d3*10)+d4;
		return encryptedPin;
	}
	private static int decrypt(int ePin){
		int valid=0;
		int d4=0;
		int d3=0;
		int d2=0;
		int d1=0;
		
		while (valid==0){
			int eNum = ePin;
			
			int length = String.valueOf(eNum).length();
			
			if (length!=4){
				System.out.println("Invalid Input");
			}
			else{
				d4=eNum%10;
				d3=(eNum/10)%10;
				d2=(eNum/100)%10;
				d1=(eNum/1000)%10;
				
				d1+=d3;
				d3=d1-d3;
				d1-=d3;
				
				d2+=d4;
				d4=d2-d4;
				d2-=d4;
				
				d1=(d1+3)%10;
				d2=(d2+3)%10;
				d3=(d3+3)%10;
				d4=(d4+3)%10;
			}
		}
		int decryptedPin = (d1*1000)+(d2*100)+(d3*10)+d4;
		return decryptedPin;
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
			System.out.println("Saving . . .\nSaved.");
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
	public static boolean confirm(String x){
		clearScreen();
		System.out.print("Confirm \""+x+" \"(Y,N)?");
		char confirm=read.next().charAt(0);
		//change character to lowercase.
		confirm=Character.toLowerCase(confirm);
		//confirming input
		if (confirm=='y'){
			clearScreen();
			System.out.println("Saving . . .\nSaved.");
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
	public static boolean confirm(int x){
		clearScreen();
		System.out.print("Confirm \""+x+" \"(Y,N)?");
		char confirm=read.next().charAt(0);
		//change character to lowercase.
		confirm=Character.toLowerCase(confirm);
		//confirming input
		if (confirm=='y'){
			clearScreen();
			System.out.println("Saving . . .\nSaved.");
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
		/*String addedBy=fullName;*/
		String addedAt= now.toString();
		isConfirm=false;
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
	public static void register(){	}
	public static void login(){
		while (isConfirm==false){
			read.nextLine();   // IMPORTANT! Fixes blank input problem, CLEARS INPUT BUFFER
			System.out.print("First Name:");
			firstName=read.nextLine();
			confirm(firstName); 
		}
		isConfirm=false;
		while (isConfirm==false){
			read.nextLine();
			System.out.print("Last Name:");
			lastName=read.nextLine();
			confirm(lastName);
		}
		isConfirm=false;
		while(isConfirm==false){
			read.nextLine();
			System.out.print("User Name:");
			userName=read.nextLine();
			confirm(userName);
		}
		isConfirm=false;
		while (isConfirm==false){
			System.out.print("Pin (4 digits):");
			pin=read.nextInt();
			confirm(pin);
		}
		isConfirm=false;
	}
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