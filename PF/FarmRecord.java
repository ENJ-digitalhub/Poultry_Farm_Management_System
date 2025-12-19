package PF;
import PF.SystemUtils;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class FarmRecord{
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner(System.in);
	static LocalDateTime time = LocalDateTime.now();
	static ArrayList <String> farmRecords = new ArrayList<String>();
	static ArrayList <String> inventoryRecord = new ArrayList<String>();
	
	public static void farmMenu(Runnable homeCallBack){
		System.out.println("\n"+"=".repeat(50)+"\n");
        System.out.println(tools.center("FARM MANAGEMENT MENU",50));
        System.out.println("\n"+"=".repeat(50)+"\n");
        System.out.print("1. Record Today's Farm Data\n2. View Farm Record History\n3. Update Existing Farm Record\n0. Back\nOption: ");
        int option=-1;
        while(true){
            try{
                option=read.nextInt();
                read.nextLine();
                break;
            }catch(java.util.InputMismatchException e){
                System.out.println("\nInvalid input! Please enter numbers only.");
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
                case 1:
                    tools.clearScreen();
                    todayData(homeCallBack);
                    break;
                case 2:
                    tools.clearScreen();
                    viewRecord(homeCallBack);
                    break;
                case 3:
                    tools.clearScreen();
                    editRecord(homeCallBack);
                    break;
                default:
                    tools.clearScreen();
                    System.out.println("Invalid selection");
                    farmMenu(homeCallBack);
                    break;
            }
        }
    }
	public static void todayData(Runnable homeCallBack){
        boolean isConfirm=false;
		String [] data = new String [5];
        double eggNo=0, feedNo=0, deathNo=0;
		String comment="";
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());
		if(farmRecords.size() == 0){
        System.out.println("No previous record found. Creating first farm record...\n");
		}
		else{
		String lastRecord = farmRecords.get(farmRecords.size()-1).replace("[","").replace("]","");
		String[] lastInRecord = lastRecord.split(",\\s");
		
			if (lastInRecord[0].equals(time.toLocalDate().toString())){
				System.out.println("Record Already Exists");
				isConfirm=false;
				while(isConfirm==false){
					System.out.println("\nDo you want to edit past record(Y/N)?");
					char confirm=read.next().charAt(0);
					confirm=Character.toLowerCase(confirm);
					if (confirm=='y'){
						tools.clearScreen();
						System.out.println("Loading . . .");
						isConfirm=true;
						editRecord(homeCallBack);
						return;
					}
					else if(confirm=='n'){
						tools.clearScreen();
						isConfirm=true;
						farmMenu(homeCallBack);
						return;
					}
					else{
						tools.clearScreen();
						System.out.println("Invalid Input");
					}
				}
			}
		}
        while(isConfirm==false){
            eggNo = tools.getPositiveDoubleInput("Input number of Egg(s): "); 
            isConfirm=tools.confirm(eggNo);
        }
        isConfirm=false;
        while(isConfirm==false){
            feedNo = tools.getPositiveDoubleInput("Input number of Feed(s) used: ");
            isConfirm=tools.confirm(feedNo);
        }
        isConfirm=false;
        while(isConfirm==false){
            deathNo = tools.getPositiveDoubleInput("Input number of Death(s): ");
            isConfirm=tools.confirm(deathNo);
        }
		isConfirm=false;
		while(isConfirm==false){
			System.out.print("Comment: ");
			comment=read.nextLine();
			isConfirm=tools.confirm(comment);
			if (comment==""){
				comment="null";
			}
		}
		double birdNo=deathNo;
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		if(inventoryRecord.size() == 0){
        System.out.println("No previous record found. Creating first stock record...\n");
        String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
        tools.writer(FileNames.INVENTORY.getPath(), firstRecord);
		}
		inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
		String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
		double currentBirdNo=Double.parseDouble(lastInRecord[1]);
		double currentFeedNo=Double.parseDouble(lastInRecord[2]);
		if(birdNo>currentBirdNo){
			System.out.println(
				"Insufficient. Total Birds: "+currentBirdNo+
				"\nPress ENTER to return to Farm Menu..."
				);
			read.nextLine();
			tools.clearScreen();
			farmMenu(homeCallBack);
		}
		if(feedNo>currentFeedNo){
			System.out.println(
				"Insufficient. Total Feeds: "+currentFeedNo+
				"\nPress ENTER to return to Farm Menu..."
				);
			read.nextLine();
			tools.clearScreen();
			farmMenu(homeCallBack);
		}
		currentBirdNo-=birdNo;
		currentFeedNo-=feedNo;
		lastInRecord[2]=String.valueOf(currentFeedNo);
		lastInRecord[1]=String.valueOf(currentBirdNo);
		tools.writer(FileNames.INVENTORY.getPath(),Arrays.toString(lastInRecord));
		data = new String[] {time.toLocalDate().toString(),Double.toString(eggNo),Double.toString(feedNo),Double.toString(deathNo),comment};
		System.out.println(Arrays.toString(data));
		tools.writer(FileNames.FARMRECORD.getPath(),(Arrays.toString(data)));
        System.out.println(
			"\nToday's data saved successfully"+
			"\nPress ENTER to return to Farm Management Menu..."
			);
		read.nextLine();
		tools.clearScreen();
		farmMenu(homeCallBack);
    }
	public static void viewRecord(Runnable homeCallBack){
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());
		if(farmRecords.size() == 0 || (farmRecords.size() == 1 && farmRecords.get(0).isEmpty())){
			System.out.println("No farm records found\n");
		}
		else{
			System.out.println("--- Farm Record History ---");
			System.out.println("\nDate\t\t|Eggs\t|Feed\t|Death\t|Comment");
			System.out.println("\n"+"-".repeat(60)+"\n");
			for(int i = farmRecords.size() - 1; i >= 0; i--){
				String record = farmRecords.get(i).replace("[","").replace("]","");
				if(record.trim().isEmpty()) continue;
				String[] recordData = record.split(",\\s*");
				if(recordData.length >= 5){
					String date = recordData[0];
					double eggNo = Double.parseDouble(recordData[1]);
					double feedNo = Double.parseDouble(recordData[2]);
					double deathNo = Double.parseDouble(recordData[3]);
					String comment = recordData[4];
					System.out.println(date + "\t|" + eggNo + "\t|" + feedNo + "\t|" + deathNo + "\t|" + comment);
				}
			}
		}
		System.out.println("\n"+"-".repeat(60)+"\n");
		System.out.println("\n0. Back");
		System.out.print("Option: ");
        int option=-1;
        while(true){
            try{
                option=read.nextInt();
                read.nextLine();
                break;
            }catch(java.util.InputMismatchException e){
                System.out.println("\nInvalid input! Please enter numbers only.");
                read.nextLine();
                System.out.print("Option: ");
            }
        }
		if (option==0){
			tools.clearScreen();
			farmMenu(homeCallBack);
		}
		else{
			tools.clearScreen();
			System.out.print("Invalid Input");
			viewRecord(homeCallBack);
		}
	}
	public static void editRecord(Runnable homeCallBack){
		farmRecords = tools.reader(FileNames.FARMRECORD.getPath());
		boolean isConfirm=false;
		String [] data = new String [5];
		double eggNo=0, feedNo=0, deathNo=0,previousDeathNo=0,previousFeedNo=0;
		String comment="",date="";
		boolean recordFound = false;
		int recordIndex = -1;
		if(farmRecords.size() == 0){
			System.out.println("No farm records found\n");
		}
		else{
			System.out.println("--- Farm Record Editor ---");
			isConfirm=false;
			read.nextLine(); 
			while(isConfirm==false){
				System.out.print("Input Date(\"YYYY-MM-DD\"): ");
				date = read.nextLine();
				isConfirm=tools.confirm(date);
			}
			String record = "";
			String[] recordData = new String[5];
			for(int i = 0; i < farmRecords.size(); i++){
				record = farmRecords.get(i).replace("[","").replace("]","");
				if(record.trim().isEmpty()) continue;
				recordData = record.split(",\\s*");
				if(recordData.length >= 5 && date.equals(recordData[0])){
					recordFound = true;
					recordIndex = i;
					break;
				}
			}		
			if(!recordFound){
				System.out.println("No record found for date: " + date);
			}
			else{
				previousFeedNo=Double.parseDouble(recordData[2]);
				previousDeathNo=Double.parseDouble(recordData[3]);
				isConfirm=false;
				while(isConfirm==false){
					eggNo = tools.getPositiveDoubleInput("Input number of Egg(s): "); 
					isConfirm=tools.confirm(eggNo);
				}
				isConfirm=false;
				while(isConfirm==false){
					feedNo = tools.getPositiveDoubleInput("Input number of Feed(s) used: ");
					isConfirm=tools.confirm(feedNo);
				}
				isConfirm=false;
				while(isConfirm==false){
					deathNo = tools.getPositiveDoubleInput("Input number of Death(s): ");
					isConfirm=tools.confirm(deathNo);
				}
				isConfirm=false;
				while(isConfirm==false){
					System.out.print("Comment: ");
					comment = read.nextLine();
					isConfirm=tools.confirm(comment);
					if (comment.isEmpty()){
						comment="null";
					}
				}
				double birdNo=deathNo;
				inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
				if(inventoryRecord.size() == 0){
				System.out.println("No previous record found. Creating first stock record...\n");
				String firstRecord = "[" + time.toLocalDate() + ", 0" + ", 0" + "]";
				tools.writer(FileNames.INVENTORY.getPath(), firstRecord);
				}
				inventoryRecord = tools.reader(FileNames.INVENTORY.getPath());
				String lastRecord = inventoryRecord.get(inventoryRecord.size()-1).replace("[","").replace("]",""); 
				String [] lastInRecord = lastRecord.split(",\\s");
				double currentBirdNo=Double.parseDouble(lastInRecord[1]);
				double currentFeedNo=Double.parseDouble(lastInRecord[2]);
				if(birdNo>currentBirdNo){
					System.out.println(
						"Insufficient. Total Birds: "+currentBirdNo+
						"\nPress ENTER to return to Farm Menu..."
						);
					read.nextLine();
					tools.clearScreen();
					farmMenu(homeCallBack);
				}
				if(feedNo>currentFeedNo){
					System.out.println(
						"Insufficient. Total Feeds: "+currentFeedNo+
						"\nPress ENTER to return to Farm Menu..."
						);
					read.nextLine();
					tools.clearScreen();
					farmMenu(homeCallBack);
					return;
				}
				currentBirdNo+=previousDeathNo;
				currentBirdNo-=birdNo;
				currentFeedNo+=previousFeedNo;
				currentFeedNo-=feedNo;
				lastInRecord[2]=String.valueOf(currentFeedNo);
				lastInRecord[1]=String.valueOf(currentBirdNo);
				tools.writer(FileNames.INVENTORY.getPath(),Arrays.toString(lastInRecord));
				data = new String[] {date, Double.toString(eggNo), Double.toString(feedNo), Double.toString(deathNo), comment};
				String updatedRecord = Arrays.toString(data);
				farmRecords.set(recordIndex, updatedRecord);
				tools.rewriteFile(FileNames.FARMRECORD.getPath(),farmRecords);
				System.out.println("\nRecord edited successfully!");
				System.out.println("Updated: " + updatedRecord);
			}
		}
		System.out.println("\n0. Back");
		System.out.print("Option: ");
        int option=-1;
        while(true){
            try{
                option=read.nextInt();
                read.nextLine();
                break;
            }catch(java.util.InputMismatchException e){
                System.out.println("\nInvalid input! Please enter numbers only.");
                read.nextLine();
                System.out.print("Option: ");
            }
        }
		if (option==0){
			tools.clearScreen();
			farmMenu(homeCallBack);
		}
		else{
			tools.clearScreen();
			System.out.print("Invalid Input");
			editRecord(homeCallBack);
		}
	}
}
