package PF;
import PF.SystemUtils;
import java.util.Scanner;

public class Help{
	static Scanner read = new Scanner(System.in);
	static SystemUtils tools = new SystemUtils();
	
	public static void helpMenu(Runnable homeCallBack){
		int option;
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(tools.center("HELP MENU",50));
		System.out.println("\n"+"=".repeat(50)+"\n");
		System.out.println(
			"1. Common Issues and Solutions\n"+
			"2. Input Guidelines\n"+
			"3. System Limitations\n"+
			"4. Contact / Developer Note\n"+
			"0. Back\n"+
			"Option: "
		);
		option = read.nextInt();
		if (option==0){
			tools.clearScreen();
			homeCallBack.run();
		}
		else{
			read.nextLine();
			switch(option){
				case 1:
					tools.clearScreen();
					System.out.println(
						"--- Common Issues ---\n\n" +
						"- No records found\n" +
						"  - This means no stock has been added yet.\n\n" +
						"- Invalid input\n" +
						"  - Please enter numbers only where required.\n\n" +
						"- Inventory is empty\n" +
						"  - Add birds, feed, or vaccines to continue.\n\n" +
						"- Operation cancelled\n" +
						"  - No changes were made to the records.\n\n" +
						"- File access issue\n" +
						"  - Please restart the application and try again.\n\n"+
						"Press ENTER to return to Help Menu..."
					);
					read.nextLine();
					tools.clearScreen();
					helpMenu(homeCallBack);
					break;
				case 2:
					tools.clearScreen();
				   System.out.println(
						"--- Input Guidelines ---\n\n" +
						"- Enter only positive numbers for stock values.\n" +
						"- Follow on-screen instructions carefully.\n" +
						"- Confirm your input when asked.\n" +
						"- Select menu options using numbers only.\n\n"+
						"Press ENTER to return to Help Menu..."
					);
					read.nextLine();
					tools.clearScreen();
					helpMenu(homeCallBack);
					break;
				case 3:
					tools.clearScreen();
				    System.out.println(
						"--- System Limitations ---\n\n" +
						"- This system supports single-user access only.\n" +
						"- Records are stored locally.\n" +
						"- No automatic backup is available.\n" +
						"- Advanced reporting will be added in future versions.\n\n"+
						"Press ENTER to return to Help Menu..."
					);
					read.nextLine();
					tools.clearScreen();
					helpMenu(homeCallBack);
					break;
				case 4:
					tools.clearScreen();
				    System.out.println(
						"--- Developer Note ---\n\n" +
						"This Poultry Farm Management System was developed as a Phase One project.\n\n" +
						"Future improvements include:\n" +
						"- Better error handling\n" +
						"- Improved reports\n" +
						"- User authentication\n\n" +
						"Thank you for using this system.\n\n"+
						"Press ENTER to return to Help Menu..."
					);
					read.nextLine();
					tools.clearScreen();
					helpMenu(homeCallBack);
					break;
				case 0:
					tools.clearScreen();
					homeCallBack.run();
					break;
				default:
					tools.clearScreen();
					System.out.println("Invalid option. Please try again.");
					helpMenu(homeCallBack);
			}
		}
	}
}