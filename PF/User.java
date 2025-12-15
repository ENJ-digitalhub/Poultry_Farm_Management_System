package PF;
import PF.SystemUtils;
import java.util.Scanner;
import java.util.Arrays;

public class User{
	static Scanner read = new Scanner(System.in);
	static SystemUtils tools = new SystemUtils();
	
	static boolean isConfirm=false;
	static int sn = 1;
    static String firstName;
    static String lastName;
    static String fullName;
    static String userName;
    static  String pin;
    static String [] info = new String [5];
	
	 public static void register(){
        while (isConfirm==false){
            System.out.print("First Name:");
            firstName=read.nextLine();
            isConfirm=tools.confirm(firstName); 
        }
        isConfirm=false;
        while (isConfirm==false){
            System.out.print("Last Name:");
            lastName=read.nextLine();
            isConfirm=tools.confirm(lastName);
        }
        isConfirm=false;
        while(isConfirm==false){
            System.out.print("User Name:");
            userName=read.nextLine();
            isConfirm=tools.confirm(userName);
        }
        isConfirm=false;
        while (isConfirm==false){
            System.out.print("Pin (4 digits):");
            pin=read.nextLine();
            isConfirm=tools.confirm(pin);
        }
        isConfirm=false;
        info = new String[] {Integer.toString(sn),firstName,lastName,userName,tools.encrypt(pin)};
        System.out.println(Arrays.toString(info));
		
		tools.reader(tools.USERINFO);
		tools.writer(tools.USERINFO,(Arrays.toString(info)));
    }
	public static void login(Runnable startupPageCallBack){
		System.out.println("Under Construction");
		System.out.println("\n0. Back");

        int option = -1;
		while(true){
			try{
				option = read.nextInt();
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
            startupPageCallBack.run();
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            login(startupPageCallBack);
        }
	}
}
