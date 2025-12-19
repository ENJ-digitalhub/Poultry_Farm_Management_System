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
	static ArrayList <String> inventoryRecord = new ArrayList<String>();
	static LocalDateTime time = LocalDateTime.now();
	
	public static void stockMenu(Runnable homeCallBack){
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(tools.center("STOCK MANAGEMENT MENU",50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.print("1. Add New Birds to Stock\n2. Remove Birds from Stock\n3. Record Feed Stock\n4. View Current Stock\n0. Back\nOptions:");
		int option;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input");
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
				//view Stock data
				case 4:
					tools.clearScreen();
					viewStock(homeCallBack);
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
		double birdNo=0;
		isConfirm=false;
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		while(isConfirm==false){
			birdNo = tools.getPositiveDoubleInput("Input number of Bird(s): ");
			isConfirm=tools.confirm(birdNo);
		}
		if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
        tools.writer(FileNames.INVENTORY.getPath(), firstRecord);
		}
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		double previousBirdNo=Double.parseDouble(lastInRecord[1]);
		birdNo+=previousBirdNo;
		lastInRecord[1]=String.valueOf(birdNo);
		tools.writer(FileNames.INVENTORY.getPath(),Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}
	public static void removeBird(Runnable homeCallBack){
		double birdNo=0;
		isConfirm=false;
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		while(isConfirm==false){
			birdNo = tools.getPositiveDoubleInput("Input number of Bird(s): ");
			isConfirm=tools.confirm(birdNo);
		}
		if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
        tools.writer(FileNames.INVENTORY.getPath(), firstRecord);
		}
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		double currentBirdNo=Double.parseDouble(lastInRecord[1]);
		if(birdNo>currentBirdNo){
			System.out.println("Insufficient. Total Birds: "+currentBirdNo);
			stockMenu(homeCallBack);
		}
		currentBirdNo-=birdNo;
		lastInRecord[1]=String.valueOf(currentBirdNo);
		tools.writer(FileNames.INVENTORY.getPath(),Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}
	public static void addFeed(Runnable homeCallBack){
		double feedNo=0;
		isConfirm=false;
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		while(isConfirm==false){
			feedNo = tools.getPositiveDoubleInput("Input number of Feed(s): ");
			isConfirm=tools.confirm(feedNo);
		}
		if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
        tools.writer(FileNames.INVENTORY.getPath(), firstRecord);
		}
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		double previousFeedNo=Double.parseDouble(lastInRecord[2]);
		feedNo+=previousFeedNo;
		lastInRecord[2]=String.valueOf(feedNo);
		tools.writer(FileNames.INVENTORY.getPath(),Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}	
	public static void viewStock(Runnable homeCallBack){
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
        tools.writer(FileNames.INVENTORY.getPath(), firstRecord);
		}
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
        double eggNo=0,feedNo=0;
        int count=1;
        boolean done=true;
        while(done){
            if (inventoryRecord.size()<count)break;
            String lastRecord = inventoryRecord.get(inventoryRecord.size()-count).replace("[","").replace("]","");
            String[] lastInRecord = lastRecord.split(",\\s");
			eggNo=Double.parseDouble(lastInRecord[1]);
			feedNo=Double.parseDouble(lastInRecord[2]);
			done=false;
        }
		
        System.out.println("--- Current Stock ---");
        System.out.println("\nEgg(s) \t\t: "+eggNo);
        System.out.println("Feed(s) \t: "+feedNo);
        System.out.println("\n1. Veiw Stock History");
		System.out.println("0. Back");

		System.out.print("Option: ");
        int option;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input");
				read.nextLine();
				System.out.print("Option: ");
			}
		}
        if (option==1){
			tools.clearScreen();
            viewStockHistory(homeCallBack);
        }       
		else if (option==0){
            tools.clearScreen();
            stockMenu(homeCallBack);
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            viewStock(homeCallBack);
        }
    }
	public static void viewStockHistory(Runnable homeCallBack){
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		if(inventoryRecord.size() == 0 || (inventoryRecord.size() == 1 && inventoryRecord.get(0).isEmpty())){
			System.out.println("No farm records found\n");
		}
		else{
			System.out.println("--- Farm Record History ---");
			System.out.println("\nDate\t\t|Eggs\t\t|Feeds");
			System.out.println("-".repeat(50));
			for(int i = inventoryRecord.size() - 1; i >= 0; i--){
				String record = inventoryRecord.get(i).replace("[","").replace("]","");
				if(record.trim().isEmpty()) continue;
				String[] recordData = record.split(",\\s*");
				String date = recordData[0];
				double eggNo = Double.parseDouble(recordData[1]);
				double feedNo = Double.parseDouble(recordData[2]);
				System.out.println(date + "\t|" + eggNo + "\t\t|" + feedNo);
			}
		}
		System.out.println("\n0. Back");
		System.out.print("Option: ");
		int option;
		while(true){
			try{
				option=read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input");
				read.nextLine();
				System.out.print("Option: ");
			}
		}
		if (option==0){
			tools.clearScreen();
			stockMenu(homeCallBack);
		}
		else{
			tools.clearScreen();
			System.out.print("Invalid Input");
			viewStockHistory(homeCallBack);
		}
	}
}
