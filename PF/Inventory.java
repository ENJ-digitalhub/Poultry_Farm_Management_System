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
		System.out.print("1. Add New Birds to Stock\n2. Remove Birds from Stock\n3. Record Feed Stock\n4. Record Vaccine/Medicine Stock\n5. View Current Stock\n6. Edit Stock Record\n0. Back\nOptions:");
		int option=read.nextInt();
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
		double birdNo=0;
		isConfirm=false;
		while(isConfirm==false){
			birdNo = tools.getPositiveDoubleInput("Input number of Bird(s): ");
			isConfirm=tools.confirm(birdNo);
		}
		 if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" +", 0"+ "]";
        tools.writer(tools.INVENTORY, firstRecord);
		}
		inventoryRecord = tools.reader(tools.INVENTORY);
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		double previousBirdNo=Double.parseDouble(lastInRecord[1]);
		birdNo+=previousBirdNo;
		lastInRecord[1]=String.valueOf(birdNo);
		tools.writer(tools.INVENTORY,Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}
	public static void removeBird(Runnable homeCallBack){
		double birdNo=0;
		isConfirm=false;
		while(isConfirm==false){
			birdNo = tools.getPositiveDoubleInput("Input number of Bird(s): ");
			isConfirm=tools.confirm(birdNo);
		}
		 if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + ", 0"+"]";
        tools.writer(tools.INVENTORY, firstRecord);
		}
		inventoryRecord = tools.reader(tools.INVENTORY);
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		double currentBirdNo=Double.parseDouble(lastInRecord[1]);
		if(birdNo>currentBirdNo){
			System.out.println("Insufficient. Total Birds: "+currentBirdNo);
			stockMenu(homeCallBack);
		}
		currentBirdNo-=birdNo;
		lastInRecord[1]=String.valueOf(currentBirdNo);
		tools.writer(tools.INVENTORY,Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}
	public static void addFeed(Runnable homeCallBack){
		double feedNo=0;
		isConfirm=false;
		while(isConfirm==false){
			feedNo = tools.getPositiveDoubleInput("Input number of Feed(s): ");
			isConfirm=tools.confirm(feedNo);
		}
		 if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + ", 0"+"]";
        tools.writer(tools.INVENTORY, firstRecord);
		}
		inventoryRecord = tools.reader(tools.INVENTORY);
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		double previousFeedNo=Double.parseDouble(lastInRecord[2]);
		feedNo+=previousFeedNo;
		lastInRecord[2]=String.valueOf(feedNo);
		tools.writer(tools.INVENTORY,Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}	
	public static void addVaccine(Runnable homeCallBack){
		double vaccineNo=0;
		isConfirm=false;
		while(isConfirm==false){
			vaccineNo = tools.getPositiveDoubleInput("Input number of Vaccine(s): ");
			isConfirm=tools.confirm(vaccineNo);
		}
		 if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
		vaccineNo=0;
		isConfirm=false;
		while(isConfirm==false){
			System.out.print("Input number of Vaccine(s): ");
			vaccineNo = read.nextInt();
			isConfirm=tools.confirm(vaccineNo);
		}
		 if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + ", 0"+"]";
        tools.writer(tools.INVENTORY, firstRecord);
		}
		inventoryRecord = tools.reader(tools.INVENTORY);
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		int previousFeedNo=Integer.parseInt(lastInRecord[3]);
		vaccineNo+=previousFeedNo;
		lastInRecord[3]=String.valueOf(vaccineNo);
		tools.writer(tools.INVENTORY,Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
		}
		inventoryRecord = tools.reader(tools.INVENTORY);
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		double previousFeedNo=Double.parseDouble(lastInRecord[3]);
		vaccineNo+=previousFeedNo;
		lastInRecord[3]=String.valueOf(vaccineNo);
		tools.writer(tools.INVENTORY,Arrays.toString(lastInRecord));
		stockMenu(homeCallBack);
	}
	public static void viewStock(Runnable homeCallBack){
		 if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + ", 0"+"]";
        tools.writer(tools.INVENTORY, firstRecord);
		}
		inventoryRecord = tools.reader(tools.INVENTORY);
        double eggNo=0,feedNo=0,vaccineNo=0;
        int count=1;
        boolean done=true;
        while(done ){
            if (inventoryRecord.size()<count)break;
            String lastRecord = inventoryRecord.get(inventoryRecord.size()-count).replace("[","").replace("]","");
            String[] lastInRecord = lastRecord.split(",\\s");
			eggNo=Double.parseDouble(lastInRecord[1]);
			feedNo=Double.parseDouble(lastInRecord[2]);
			vaccineNo=Double.parseDouble(lastInRecord[3]);
			done=false;
        }
		
        System.out.println("--- Current Stock ---");
        System.out.println("\nEgg(s) \t: "+eggNo);
        System.out.println("Feed(s) \t: "+feedNo);
		System.out.println("Vaccine(s) \t: "+vaccineNo);
        System.out.println("\n1. Veiw Stock History");
		System.out.println("0. Back");

		System.out.print("Option: ");
        int option = read.nextInt();
        if (option==1){
            //reportMenu(homeCallBack);
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
	public static void editStock(Runnable homeCallBack){
		System.out.println("Under Construction");
		System.out.println("\n0. Back");

		System.out.print("Option: ");
        int option = read.nextInt();
        if (option==0){
            tools.clearScreen();
            stockMenu(homeCallBack);
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            editStock(homeCallBack);
        }
	}
}