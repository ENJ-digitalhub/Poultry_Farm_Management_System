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

		while(true){
			try{
				option = read.nextInt();
				read.nextLine();
				break;
			}catch(Exception e){
				System.out.println("Invalid input. Please enter a number corresponding to the menu option.");
				read.nextLine();
				System.out.print("Option: ");
			}
		}

		if (option==0){
			tools.clearScreen();
			homeCallBack.run();
		}
		else{
			switch(option){
				case 1:
					tools.clearScreen();
					System.out.println(
						"--- Common Issues & Quick Fixes ---\n\n" +
						"1. No records found\n" +
						"   - Solution: Add stock records first.\n\n" +
						"2. Invalid input\n" +
						"   - Solution: Enter numbers where required, menu selections must be digits.\n\n" +
						"3. Inventory appears empty\n" +
						"   - Solution: Ensure birds, feed, or vaccines are added.\n\n" +
						"4. Operation cancelled\n" +
						"   - Solution: Confirm your actions when prompted.\n\n" +
						"5. File access errors\n" +
						"   - Solution: Restart the program and try again.\n\n"+
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
						"- Use only positive numbers for quantities.\n" +
						"- Follow prompts carefully for accurate entries.\n" +
						"- Confirm your entries when requested.\n" +
						"- Navigate menus using numeric options only.\n\n"+
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
						"- Single-user access only.\n" +
						"- Data is stored locally on your machine.\n" +
						"- No automated backups available yet.\n" +
						"- Advanced reporting and analytics coming in future updates.\n\n"+
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
						"This system is a Phase One project for Poultry Farm Management.\n\n" +
						"Planned enhancements:\n" +
						"- Robust error handling\n" +
						"- Comprehensive reporting\n" +
						"- User authentication\n\n" +
						"Your feedback is welcome. Thank you for using the system.\n\n"+
						"Press ENTER to return to Help Menu..."
					);
					read.nextLine();
					tools.clearScreen();
					helpMenu(homeCallBack);
					break;
				default:
					tools.clearScreen();
					System.out.println("Invalid option. Please select a number from the menu.");
					helpMenu(homeCallBack);
			}
		}
	}
}
