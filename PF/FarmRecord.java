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
	static ArrayList <String> records = new ArrayList<>();
	
	public static void todayData(Runnable homeCallBack){
		records = tools.reader(tools.FARMRECORD);
		String lastRecord = records.get(records.size()-1).replace("[","").replace("]",""); 
		String [] lastInRecord = lastRecord.split(",\\s");
        boolean isConfirm=false;
		String [] data = new String [5];
        double eggNo=0, feedNo=0, deathNo=0;
		String comment="";
		
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
					System.out.println("Back to Main Menu");
					isConfirm=true;
					homeCallBack.run();
					return;
				}
				else{
					tools.clearScreen();
					System.out.println("Invalid Input");
				}
			}
		}
        while(isConfirm==false){
            System.out.print("Input number of Eggs: "); 
            eggNo = read.nextInt();
            isConfirm=tools.confirm(eggNo);
			isConfirm=tools.doubleValidation(eggNo);
        }
        isConfirm=false;
        while(isConfirm==false){
            System.out.print("Input number of Feeds used: ");
            feedNo = read.nextInt();
            isConfirm=tools.confirm(feedNo);
			isConfirm=tools.doubleValidation(feedNo);
        }
        isConfirm=false;
        while(isConfirm==false){
            System.out.print("Input number of Deaths: ");
            deathNo = read.nextInt();
            isConfirm=tools.confirm(deathNo);
			isConfirm=tools.doubleValidation(deathNo);
        }
		isConfirm=false;
		while(isConfirm==false){
			System.out.print("Comment: ");
			read.nextLine();
			comment=read.nextLine();
			isConfirm=tools.confirm(comment);
			if (comment==""){
				comment="null";
			}
		}
		data = new String[] {time.toLocalDate().toString(),Double.toString(eggNo),Double.toString(feedNo),Double.toString(deathNo),comment};
        System.out.println("\nToday's data saved successfully\n");
		tools.writer(tools.FARMRECORD,(Arrays.toString(data)));
		tools.reader(tools.FARMRECORD);
		System.out.println(Arrays.toString(data));
		homeCallBack.run();
    }
	public static void recordVaccination(Runnable homeCallBack){
		System.out.println("Under Construction");
		System.out.println("\n99. Back");

		System.out.print("Option: ");
        int option = read.nextInt();
        if (option==99){
            tools.clearScreen();
            homeCallBack.run();
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            recordVaccination(homeCallBack);
        }
	}
	public static void editRecord(Runnable homeCallBack){
		System.out.println("Under Construction");
		System.out.println("\n99. Back");

		System.out.print("Option: ");
        int option = read.nextInt();
        if (option==99){
            tools.clearScreen();
            homeCallBack.run();
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            editRecord(homeCallBack);
        }
	}
}