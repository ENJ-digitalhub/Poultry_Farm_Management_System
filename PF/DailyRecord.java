package PF;
import PF.SystemUtils;
import java.util.Scanner;

public class DailyRecord{
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner(System.in);
	
	 public static void todayData(){
        boolean isConfirm=false;
        double eggNo=0, feedNo=0, deathNo=0;

        while(isConfirm==false){
            System.out.print("Input number of Eggs: "); 
            eggNo = read.nextInt();
            tools.confirm(eggNo);
        }

        isConfirm=false;
        while(isConfirm==false){
            System.out.print("Input number of Feeds used: ");
            feedNo = read.nextInt();
            tools.confirm(feedNo);
        }

        isConfirm=false;
        while(isConfirm==false){
            System.out.print("Input number of Deaths: ");
            deathNo = read.nextInt();
            tools.confirm(deathNo);
        }

        System.out.print("Comment(press Enter to continue . . .): ");
        read.nextLine();
        read.nextLine();

        System.out.println("\nToday's data saved successfully in the session!\n");
    }
}