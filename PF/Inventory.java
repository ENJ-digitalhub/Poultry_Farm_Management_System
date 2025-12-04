package PF;
import PF.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Inventory{
	static boolean isConfirm=false;
	static Scanner read = new Scanner(System.in);
	static SystemUtils tools = new SystemUtils();
	static ArrayList <String> records = new ArrayList<>();
	static LocalDateTime time = LocalDateTime.now();
	
	public static void stockMenu(Runnable homeCallBack){
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(tools.center("STOCK MANAGEMENT MENU",50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.print("1. Add New Birds to Stock\n2. Remove Birds from Stock\n3. Record Feed Stock\n4. Record Vaccine/Medicine Stock\n5. View Current Stock\n6. Edit Stock Record\n99. Back to Main Menu\nOptions:");
		int option=read.nextInt();
		if (option==99){
			tools.clearScreen();
			homeCallBack.run();
		}
		else{
			switch (option){
				//add birds
				case 1:
					tools.clearScreen();
					addBird(homeCallBack);
					break;
				//remove birds
				case 2:
					tools.clearScreen();
					removeBird(homeCallBack);
					break;
				//add Feed
				case 3:
					tools.clearScreen();
					addFeed(homeCallBack);
					break;
				//add Vaccine
				case 4:
					tools.clearScreen();
					addVaccine(homeCallBack);
					break;
				//view Stock data
				case 5:
					tools.clearScreen();
					viewStock(homeCallBack);
					break;
				//Edit Stock Record
				case 6:
					tools.clearScreen();
					editStock(homeCallBack);
					break;
				//For wrong input
				default:
					tools.clearScreen();
					System.out.println("Invalid selection");
					stockMenu(homeCallBack);
					break;
			}
		}
	}
	public static void addBird(Runnable homeCallBack){
		int birdNo=0;
		isConfirm=false;
		while(isConfirm==false){
			System.out.print("Input number of Bird(s): ");
			birdNo = read.nextInt();
			isConfirm=tools.confirm(birdNo);
		}
		 if(records.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
        tools.writer(tools.inventory, firstRecord);
		}
		records = tools.reader(tools.inventory);
		String lastRecord = records.get(records.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		int previousBirdNo=Integer.parseInt(lastInRecord[1]);
		birdNo+=previousBirdNo;
		lastInRecord[1]=String.valueOf(birdNo);
		tools.writer(tools.inventory,Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}
	public static void removeBird(Runnable homeCallBack){
		int birdNo=0;
		isConfirm=false;
		while(isConfirm==false){
			System.out.print("Input number of Bird(s): ");
			birdNo = read.nextInt();
			isConfirm=tools.confirm(birdNo);
		}
		 if(records.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
        tools.writer(tools.inventory, firstRecord);
		}
		records = tools.reader(tools.inventory);
		String lastRecord = records.get(records.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		int currentBirdNo=Integer.parseInt(lastInRecord[1]);
		if(birdNo>currentBirdNo){
			System.out.println("Insufficient. Total Birds: "+currentBirdNo);
			stockMenu(homeCallBack);
		}
		currentBirdNo-=birdNo;
		lastInRecord[1]=String.valueOf(currentBirdNo);
		tools.writer(tools.inventory,Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}
	public static void addFeed(Runnable homeCallBack){
		int feedNo=0;
		isConfirm=false;
		while(isConfirm==false){
			System.out.print("Input number of Feed(s): ");
			feedNo = read.nextInt();
			isConfirm=tools.confirm(feedNo);
		}
		 if(records.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
        tools.writer(tools.inventory, firstRecord);
		}
		records = tools.reader(tools.inventory);
		String lastRecord = records.get(records.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		int previousFeedNo=Integer.parseInt(lastInRecord[2]);
		feedNo+=previousFeedNo;
		lastInRecord[2]=String.valueOf(feedNo);
		tools.writer(tools.inventory,Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}	
	public static void addVaccine(Runnable homeCallBack){}
	public static void viewStock(Runnable homeCallBack){}
	public static void editStock(Runnable homeCallBack){}
}