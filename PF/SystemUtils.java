package PF; 
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class SystemUtils{
	public static final String FARMRECORD = "C:\\Users\\Noble Ekwere\\OneDrive\\Dokumente\\NIIT\\Poultry_Farm_Management_System\\data\\FarmRecords.txt";
	public static final String USERINFO = "C:\\Users\\Noble Ekwere\\OneDrive\\Dokumente\\NIIT\\Poultry_Farm_Management_System\\data\\UsersInfo.txt";
	public static final String INVENTORY = "C:\\Users\\Noble Ekwere\\OneDrive\\Dokumente\\NIIT\\Poultry_Farm_Management_System\\data\\Inventory.txt";
	private static boolean isConfirm=false;
	static Scanner read = new Scanner(System.in);
	static ArrayList<String> lines = new ArrayList<>();
	static LocalDateTime time = LocalDateTime.now();
	
	public static String center(String s,double x){
        double padding = (x-s.length())/2;
        return " ".repeat((int)padding)+s+" ".repeat((int)padding);
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void exit(){
        clearScreen();
        System.out.println("Closing program . . . . .");
    }
	public static boolean confirm(Object x){
        clearScreen();
        System.out.print("Confirm \""+x+"\" (Y,N)?");
        char confirm=read.next().charAt(0);
        confirm=Character.toLowerCase(confirm);
		if (confirm=='y'){
			clearScreen();
			System.out.println("Confimed . . .");
			isConfirm=true;    
		}
		else if(confirm=='n'){
			clearScreen();
			System.out.println(". . .");
			isConfirm=false;
		}
		else{
			clearScreen();
			System.out.println("Invalid Input");
			confirm(x);
		}
        return isConfirm;
	}
	public static String encrypt(String pin){
		int valid=0;
		int d4=0;
		int d3=0;
		int d2=0;
		int d1=0;
		
		while (valid==0){
			String num = pin;
			int length = num.length();
			
			if (length!=4){
				System.out.println("Invalid Input");
				valid = 1;
			}
			else{
				valid = 1;
				
				d1 = Integer.parseInt(String.valueOf(pin.charAt(0)));
				d2 = Integer.parseInt(String.valueOf(pin.charAt(1)));
				d3 = Integer.parseInt(String.valueOf(pin.charAt(2)));
				d4 = Integer.parseInt(String.valueOf(pin.charAt(3)));

				int temp = d1;
				d1 = d3;
				d3 = temp;
				
				temp = d2;
				d2 = d4;
				d4 = temp;
				
				d1 = (d1 + 7) % 10;
				d2 = (d2 + 7) % 10;
				d3 = (d3 + 7) % 10;
				d4 = (d4 + 7) % 10;

			}
		}
		int encryptedPin = (d1*1000)+(d2*100)+(d3*10)+d4;
		return String.format("%04d",encryptedPin);//pads int if 0 is at the front.
	}
	public static String decrypt(String ePin){
		int valid=0;
		int d4=0;
		int d3=0;
		int d2=0;
		int d1=0;
		
		while (valid==0){
			String eNum = ePin;
			
			int length = eNum.length();
			
			if (length!=4){
				System.out.println("Invalid Input");
				valid = 1;
			}
			else{
				valid = 1;
				
				d1 = Integer.parseInt(String.valueOf(ePin.charAt(0)));
				d2 = Integer.parseInt(String.valueOf(ePin.charAt(1)));
				d3 = Integer.parseInt(String.valueOf(ePin.charAt(2)));
				d4 = Integer.parseInt(String.valueOf(ePin.charAt(3)));

				int temp = d1;
				d1 = d3;
				d3 = temp;
				
				temp = d2;
				d2 = d4;
				d4 = temp;
				
				d1=(d1+3)%10;
				d2=(d2+3)%10;
				d3=(d3+3)%10;
				d4=(d4+3)%10;
			}
		}
		int decryptedPin = (d1*1000)+(d2*100)+(d3*10)+d4;
		return String.format("%04d",decryptedPin);
	}
	public static ArrayList<String> reader(String fileName){
		lines.clear();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String lineRead;
			while ((lineRead=reader.readLine())!=null){
				lines.add(lineRead);
			}
			//System.out.println(lines);
			System.out.println("Reading . . .\nReading Successful\n");
			reader.close();
		}
		catch(IOException e){
			System.out.println("File does not exist\nCreating new file . . .");
			writer(fileName,"");
			//e.printStackTrace();
		}
		return lines;
	}
	public static void writer(String fileName,String txtWritten){
		try{
			lines.clear();
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
			int count=0;
			for (String line : lines){
				writer.write(lines.get(count));
				writer.newLine();
				count++;
			}
			writer.write(txtWritten);
			writer.newLine();
			System.out.println("Writing . . .\nWriting Successful\n");
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	//converts month number to words
	public static String monthsOfTheYear(String dates){
		 String [] date = new String[3];
		 date = dates.split("-");
		 int month = Integer.parseInt(date[1]);
		 LocalDateTime monthObj = time.withMonth(month);
		 String monthInWords = monthObj.getMonth().toString();
		 return monthInWords;
	}
	public static double getPositiveDoubleInput(String prompt){
    double value;
    do{
        System.out.print(prompt);
        value = read.nextDouble();
    } while(value <= 0);
    return value;
}

}