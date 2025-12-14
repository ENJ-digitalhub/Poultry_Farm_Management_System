package PF;
import java.util.Scanner;

public class FeedBack{
	static Scanner read = Scanner(system.in);
	public static void feedbackMenu(Runnable homeCallBack){
		int choice;
		do{
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

			choice = read.nextInt();

			switch(choice){
				case 1:
					System.out.println(
						"--- Common Issues ---\n\n" +
						"• No records found\n" +
						"  → This means no stock has been added yet.\n\n" +
						"• Invalid input\n" +
						"  → Please enter numbers only where required.\n\n" +
						"• Inventory is empty\n" +
						"  → Add birds, feed, or vaccines to continue.\n\n" +
						"• Operation cancelled\n" +
						"  → No changes were made to the records.\n\n" +
						"• File access issue\n" +
						"  → Please restart the application and try again.\n\n"
					);
					break;
				case 2:
				   System.out.println(
						"--- Input Guidelines ---\n\n" +
						"• Enter only positive numbers for stock values.\n" +
						"• Follow on-screen instructions carefully.\n" +
						"• Confirm your input when asked.\n" +
						"• Select menu options using numbers only.\n\n"
					);
					break;
				case 3:
				   System.out.println(
						"--- System Limitations ---\n\n" +
						"• This system supports single-user access only.\n" +
						"• Records are stored locally.\n" +
						"• No automatic backup is available.\n" +
						"• Advanced reporting will be added in future versions.\n\n"
					);
					break;
				case 4:
				   System.out.println(
						"--- Developer Note ---\n\n" +
						"This Poultry Farm Management System was developed as a Phase One project.\n\n" +
						"Future improvements include:\n" +
						"• Better error handling\n" +
						"• Improved reports\n" +
						"• User authentication\n\n" +
						"Thank you for using this system.\n\n"
					);
					break;
				case 0:
					homeCallBack.run();
					break;
				default:
					System.out.println("Invalid option. Please try again.");
			}
		}while(choice != 0);
	}
}