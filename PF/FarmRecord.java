package PF;
import PF.SystemUtils;
import java.util.Scanner;
import java.util.Arrays;

public class FarmRecord{
	static SystemUtils tools = new SystemUtils();
	static Scanner read = new Scanner(System.in);
	
	public static void todayData(){
		String [] data = new String [4];
        boolean isConfirm=false;
        double eggNo=0, feedNo=0, deathNo=0;
		String comment="";

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
		}
		data = new String[] {Double.toString(eggNo),Double.toString(feedNo),Double.toString(deathNo),comment};
        System.out.println("\nToday's data saved successfully/n");
		System.out.println(Arrays.toString(data));
    }
	public static void recordVaccination(){
		System.out.println("Under Condtruction");
	}
	public static void editRecord(){
		System.out.println("Under Condtruction");
	}
}