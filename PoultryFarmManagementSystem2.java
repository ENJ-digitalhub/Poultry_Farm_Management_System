//for accepting input
import java.util.Scanner;
//for date and time
import java.time.LocalDateTime;
//for array operations
import java.util.Arrays;

public class PoultryFarmManagementSystem2{
    static Scanner read = new Scanner(System.in);
    static LocalDateTime now = LocalDateTime.now();
    static boolean isConfirm=false;
    static int sn = 1;
    static String firstName;
    static String lastName;
    static String fullName;
    static String userName;
    static int pin;
    static String [] info = new String [5];

    private static int encrypt(int pin){
        int valid=0;
        int d4=0, d3=0, d2=0, d1=0;
        while (valid==0){
            int num = pin;
            int length = String.valueOf(num).length();
            if (length!=4){
                System.out.println("Invalid Input");
                break;
            } else{
                d4=num%10;
                d3=(num/10)%10;
                d2=(num/100)%10;
                d1=(num/1000)%10;

                d1=(d1+7)%10;
                d2=(d2+7)%10;
                d3=(d3+7)%10;
                d4=(d4+7)%10;

                d1+=d3; d3=d1-d3; d1-=d3;
                d2+=d4; d4=d2-d4; d2-=d4;
            }
            valid=1;
        }
        return (d1*1000)+(d2*100)+(d3*10)+d4;
    }

    private static int decrypt(int ePin){
        int valid=0;
        int d4=0, d3=0, d2=0, d1=0;
        while (valid==0){
            int eNum = ePin;
            int length = String.valueOf(eNum).length();
            if (length!=4){
                System.out.println("Invalid Input");
                break;
            } else{
                d4=eNum%10;
                d3=(eNum/10)%10;
                d2=(eNum/100)%10;
                d1=(eNum/1000)%10;

                d1+=d3; d3=d1-d3; d1-=d3;
                d2+=d4; d4=d2-d4; d2-=d4;

                d1=(d1+3)%10;
                d2=(d2+3)%10;
                d3=(d3+3)%10;
                d4=(d4+3)%10;
            }
            valid=1;
        }
        return (d1*1000)+(d2*100)+(d3*10)+d4;
    }

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
        System.out.print("Confirm \""+x+" \"(Y,N)?");
        char confirm=read.next().charAt(0);
        confirm=Character.toLowerCase(confirm);
        if (confirm=='y'){
            clearScreen();
            System.out.println("Saved.");
            isConfirm=true;    
        } else if(confirm=='n'){
            clearScreen();
            System.out.print(". . .");
            isConfirm=false;
        }
        return isConfirm;
    }

    public static void todayData(){
        isConfirm=false;
        double eggNo=0, feedNo=0, deathNo=0;

        while(isConfirm==false){
            System.out.print("Input number of Eggs: "); 
            eggNo = read.nextInt();
            confirm(eggNo);
        }

        isConfirm=false;
        while(isConfirm==false){
            System.out.print("Input number of Feeds used: ");
            feedNo = read.nextInt();
            confirm(feedNo);
        }

        isConfirm=false;
        while(isConfirm==false){
            System.out.print("Input number of Deaths: ");
            deathNo = read.nextInt();
            confirm(deathNo);
        }

        System.out.print("Comment(press Enter to continue . . .): ");
        read.nextLine();
        read.nextLine();

        System.out.println("\nToday's data saved successfully in the session!\n");
    }

    public static void home(){}
    public static void recordEdit(){}
    public static void eggGraph(){}

    public static void register(){
        while (isConfirm==false){
            read.nextLine();
            System.out.print("First Name:");
            firstName=read.nextLine();
            confirm(firstName); 
        }
        isConfirm=false;
        while (isConfirm==false){
            read.nextLine();
            System.out.print("Last Name:");
            lastName=read.nextLine();
            confirm(lastName);
        }
        isConfirm=false;
        while(isConfirm==false){
            read.nextLine();
            System.out.print("User Name:");
            userName=read.nextLine();
            confirm(userName);
        }
        isConfirm=false;
        while (isConfirm==false){
            System.out.print("Pin (4 digits):");
            pin=read.nextInt();
            read.nextLine();
            confirm(pin);
        }
        isConfirm=false;
        info = new String[] {Integer.toString(sn),firstName,lastName,userName,Integer.toString(encrypt(pin))};
        System.out.println(Arrays.toString(info));
    }

    public static void login(){
        System.out.print("Enter Username: ");
        String uname = read.next();
        System.out.print("Enter Pin: ");
        int userPin = read.nextInt();

        if(uname.equals(userName) && userPin == decrypt(Integer.parseInt(info[4]))){
            clearScreen();
            System.out.println("Login Successful! Welcome "+firstName+" "+lastName);
            todayData(); // enter today's data
        } else{
            clearScreen();
            System.out.println("Login Failed! Try Again.");
            startupPage();
        }
    }

    public static void startupPage(){
        System.out.println("\n"+"=".repeat(50)+"\n");
        System.out.println(center("Poultry Farm Management System",50)+"\n");
        System.out.println(center("Date: "+now.toLocalDate()+"\tTime: "+now.toLocalTime(),50));
        System.out.println("\n"+"=".repeat(50)+"\n");
        System.out.print("1. Login\n2. Register\n3. Exit\nOption: ");
        int option=read.nextInt();
        switch (option){
            case 1:
                clearScreen();
                login();
                break;
            case 2:
                clearScreen();
                register();
                break;
            case 3:
                clearScreen();
                exit();
                break;
            default:
                clearScreen();
                System.out.println("Invalid selection");
                startupPage();
                break;
        }
    }

    public static void main(String[] args){
        startupPage();
    }
}
