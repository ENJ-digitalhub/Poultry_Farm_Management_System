import java.util.Scanner;

public class PoultryFarmManagementSystem{
	static Scanner read = new Scanner(System.in);
	public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
	public static void exit(){
		clearScreen();
		System.out.println("Closing program . . . . . .");
	}
	public static boolean confirm(double x){
		clearScreen();
		System.out.print("Confirm \""+x+" \"(Y,N)?");
		boolean isConfirm=false;
		char confirm=read.next().charAt(0);
		confirm=Character.toLowerCase(confirm);
		if (confirm=='y'){
			clearScreen();
			System.out.println("Saving. . . . .");
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
	public static void todayData(){}
	public static void home(){}
	public static void recordEdit(){}
	public static void eggGraph(){}
	public static void main(String[] args){}