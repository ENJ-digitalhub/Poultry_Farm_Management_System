package PF;

import java.util.Scanner;

public class Help {
	static Scanner read = new Scanner(System.in);
	static SystemUtils tools = new SystemUtils();

	public static void helpMenu(Runnable homeCallBack) {
		while (true) {
			tools.clearScreen();
			System.out.println("\n" + "=".repeat(60) + "\n");
			System.out.println(tools.center("HELP & SUPPORT CENTER", 50));
			System.out.println("\n" + "=".repeat(60) + "\n");
			System.out.println(
				"1. Quick Start Guide\n" +
				"2. Farm Management Help\n" +
				"3. Inventory Management Help\n" +
				"4. Reports Guide\n" +
				"5. Troubleshooting Common Issues\n" +
				"6. Security & Data Management\n" +
				"7. Contact & Support\n" +
				"8. System Information\n" +
				"0. Back to Main Menu\n" +
				"Option: "
			);

			int option = tools.getPositiveIntInput("");

			if (option == 0) {
				tools.clearScreen();
				homeCallBack.run();
				break;
			}

			tools.clearScreen();
			switch (option) {
				case 1:
					showQuickStartGuide();
					break;
				case 2:
					showFarmManagementHelp();
					break;
				case 3:
					showInventoryHelp();
					break;
				case 4:
					showReportsGuide();
					break;
				case 5:
					showTroubleshooting();
					break;
				case 6:
					showSecurityInfo();
					break;
				case 7:
					showContactSupport();
					break;
				case 8:
					showSystemInfo();
					break;
				default:
					System.out.println("Invalid option. Please select a number from the menu.");
					read.nextLine().trim();
					break;
			}
		}
	}
	private static void showQuickStartGuide() {
		System.out.println(
			"QUICK START GUIDE\n" +
			"=".repeat(40) + "\n\n" +
			"FIRST TIME SETUP:\n" +
			"1. Register as a new user\n" +
			"2. Add initial bird stock (Inventory -> Add Birds)\n" +
			"3. Add initial feed stock (Inventory -> Add Feed)\n\n" +
			
			"DAILY WORKFLOW:\n" +
			"1. Login with your username and PIN\n" +
			"2. Go to: Farm Management -> Record Today's Data\n" +
			"3. Enter:\n" +
			"   - Eggs collected (format: 5_3 = 5 crates + 3 eggs)\n" +
			"   - Broken eggs count\n" +
			"   - Feed used (in bags, e.g., 12.5)\n" +
			"   - Number of deaths\n" +
			"   - Optional comments\n" +
			"4. System automatically updates inventory\n\n" +
			
			"NAVIGATION TIPS:\n" +
			"- Use NUMBERS to select menu options\n" +
			"- Press 0 to go BACK\n" +
			"- Press ENTER to continue\n" +
			"- Type Y/N for confirmations\n" +
			"- Use N/P for Next/Previous in lists\n\n" +
			
			"EGG INPUT FORMAT:\n" +
			"- Format: [crates]_[loose eggs]\n" +
			"- Example: 5_3 = 5 crates (150 eggs) + 3 loose eggs = 153 total\n" +
			"- 1 crate = 30 eggs\n\n" +
			
			"Press ENTER to return to Help Menu..."
		);
		read.nextLine().trim();
	}
	private static void showFarmManagementHelp() {
		System.out.println(
			"FARM MANAGEMENT HELP\n" +
			"=".repeat(40) + "\n\n" +
			"RECORD TODAY'S DATA:\n" +
			"- Records daily egg production, feed consumption, and bird deaths\n" +
			"- Validates against current inventory\n" +
			"- Automatically updates stock levels\n" +
			"- Prevents duplicate entries for same day\n\n" +
			
			"VIEW FARM RECORD HISTORY:\n" +
			"- Browse all past records\n" +
			"- Paginated view (10 records per page)\n" +
			"- Shows date, eggs, feed, deaths, and comments\n" +
			"- Use N/P to navigate pages\n\n" +
			
			"UPDATE EXISTING RECORD:\n" +
			"- Edit past records if corrections needed\n" +
			"- Automatically adjusts inventory differences\n" +
			"- Shows recent records for easy selection\n\n" +
			
			"IMPORTANT RULES:\n" +
			"1. Cannot record more deaths than available birds\n" +
			"2. Cannot use more feed than in stock\n" +
			"3. Only one record per day per user\n" +
			"4. All values must be positive numbers\n\n" +
			
			"INVENTORY RECONCILIATION:\n" +
			"- When recording deaths: bird stock decreases\n" +
			"- When recording feed: feed stock decreases\n" +
			"- Collected eggs are added to inventory\n" +
			"- Broken eggs are tracked separately\n\n" +
			
			"Press ENTER to return to Help Menu..."
		);
		read.nextLine().trim();
	}
	private static void showInventoryHelp() {
		System.out.println(
			"INVENTORY MANAGEMENT HELP\n" +
			"=".repeat(40) + "\n\n" +
			"ADD NEW BIRDS:\n" +
			"- Increases total bird count\n" +
			"- Used for new purchases or hatches\n" +
			"- Creates inventory record for tracking\n\n" +
			
			"REMOVE BIRDS:\n" +
			"- Decreases total bird count\n" +
			"- Used for sales or culling\n" +
			"- Cannot remove more birds than available\n\n" +
			
			"RECORD FEED STOCK:\n" +
			"- Add new feed purchases\n" +
			"- Track feed inventory in bags\n" +
			"- Decimal values supported (e.g., 12.5 bags)\n\n" +
			
			"VIEW CURRENT STOCK:\n" +
			"- Shows real-time inventory\n" +
			"- Displays: birds, eggs, feed\n" +
			"- Option to view full history\n\n" +
			
			"VIEW STOCK HISTORY:\n" +
			"- Chronological inventory changes\n" +
			"- Tracks all additions and removals\n" +
			"- Shows date and user who made changes\n" +
			"- Paginated for easy navigation\n\n" +
			
			"BEST PRACTICES:\n" +
			"1. Update inventory after every purchase/sale\n" +
			"2. Record feed stock when deliveries arrive\n" +
			"3. Regularly check current stock levels\n" +
			"4. Use history to track consumption patterns\n\n" +
			
			"Press ENTER to return to Help Menu..."
		);
		read.nextLine().trim();
	}
	private static void showReportsGuide() {
		System.out.println(
			"REPORTS GUIDE\n" +
			"=".repeat(40) + "\n\n" +
			"DAILY REPORT:\n" +
			"- Shows today's complete data\n" +
			"- Includes eggs, feed, deaths, comments\n" +
			"- Quick overview of daily performance\n\n" +
			
			"WEEKLY REPORT:\n" +
			"- Last 7 days summary\n" +
			"- Total eggs, feed, deaths\n" +
			"- Average daily production\n" +
			"- Helps identify weekly trends\n\n" +
			
			"MONTHLY REPORT:\n" +
			"- Current month's performance\n" +
			"- Total production\n" +
			"- Best and worst production days\n" +
			"- Month-over-month comparison\n\n" +
			
			"SUMMARY REPORT:\n" +
			"- Quick snapshot overview\n" +
			"- Today + This Week + This Month\n" +
			"- Easy performance assessment\n\n" +
			
			"STATISTICS SUMMARY:\n" +
			"- Highest production day\n" +
			"- Lowest production day\n" +
			"- Performance extremes\n" +
			"- Historical benchmarks\n\n" +
			
			"REPORT EXAMPLES:\n" +
			"Weekly Report Output:\n" +
			"--- Weekly Report (Last 7 Days) ---\n" +
			"Total Crates(Eggs): 35(12)\n" +
			"Total Feeds: 175.5\n" +
			"Total Deaths: 8\n" +
			"Average Crates(Eggs)/Day: 5(1)\n\n" +
			
			"USING REPORTS EFFECTIVELY:\n" +
			"1. Check daily reports every evening\n" +
			"2. Review weekly reports every Monday\n" +
			"3. Analyze monthly reports for trends\n" +
			"4. Use statistics to set production goals\n\n" +
			
			"Press ENTER to return to Help Menu..."
		);
		read.nextLine().trim();
	}
	private static void showTroubleshooting() {
		System.out.println(
			"TROUBLESHOOTING COMMON ISSUES\n" +
			"=".repeat(40) + "\n\n" +
			"\"Java not found\" or \"Java not recognized\"\n" +
			"   Solution: Install/update Java from java.com\n\n" +
			
			"\"Could not find or load main class\"\n" +
			"   Solution: Run from correct folder with: java -jar PM_System.jar\n\n" +
			
			"\"SQLite JDBC driver not found\"\n" +
			"   Solution: Ensure lib/sqlite-jdbc-3.45.2.0.jar exists\n\n" +
			
			"\"Insufficient. Total Birds: X\"\n" +
			"   Solution: Check current stock before recording deaths\n\n" +
			
			"\"Record Already Exists\"\n" +
			"   Solution: Use 'Update Existing Record' instead\n\n" +
			
			"\"Invalid format. Use crate_eggs format.\"\n" +
			"   Solution: Enter eggs as: 5_3 (5 crates + 3 eggs)\n\n" +
			
			"Database permission errors\n" +
			"   Solution: Run as Administrator or check folder permissions\n\n" +
			
			"Program starts but crashes\n" +
			"   Solution: Close all instances, delete poultry.db-journal if exists\n\n" +
			
			"Slow performance with many records\n" +
			"   Solution: System handles 10,000+ records efficiently\n\n" +
			
			"DATA RECOVERY:\n" +
			"- Backup: Copy data/poultry.db regularly\n" +
			"- Restore: Replace damaged file with backup\n" +
			"- Default location: data/ folder\n\n" +
			
			"Press ENTER to return to Help Menu..."
		);
		read.nextLine().trim();
	}
	private static void showSecurityInfo() {
		System.out.println(
			"SECURITY & DATA MANAGEMENT\n" +
			"=".repeat(40) + "\n\n" +
			"PIN SECURITY:\n" +
			"- 4-digit PIN required\n" +
			"- Encrypted using substitution cipher\n" +
			"- Example: 1234 -> 0189 (encrypted)\n" +
			"- Never stored as plain text\n\n" +
			
			"USER MANAGEMENT:\n" +
			"- Multiple users supported\n" +
			"- Each user has unique username\n" +
			"- PIN cannot be recovered if forgotten\n" +
			"- Contact administrator for PIN reset\n\n" +
			
			"DATA STORAGE:\n" +
			"- SQLite database: data/poultry.db\n" +
			"- Local storage only (no internet)\n" +
			"- Automatic backup via file copy\n" +
			"- ACID compliant (data integrity)\n\n" +
			
			"AUDIT TRAIL:\n" +
			"- Tracks user who performed each action\n" +
			"- Timestamp for all operations\n" +
			"- Created_by field in all records\n" +
			"- Complete operation history\n\n" +
			
			"BACKUP PROCEDURE:\n" +
			"1. Close the program completely\n" +
			"2. Copy data/poultry.db to safe location\n" +
			"3. Use descriptive backup names\n" +
			"4. Test restore occasionally\n\n" +
			
			"MIGRATION TO NEW COMPUTER:\n" +
			"1. Install Java on new computer\n" +
			"2. Copy entire project folder\n" +
			"3. Copy your data/poultry.db file\n" +
			"4. Run program - data preserved\n\n" +
			
			"Press ENTER to return to Help Menu..."
		);
		read.nextLine().trim();
	}
	private static void showContactSupport() {
		System.out.println(
			"CONTACT & SUPPORT\n" +
			"=".repeat(40) + "\n\n" +
			"GETTING HELP:\n" +
			"1. First: Check this Help menu\n" +
			"2. Second: Review Troubleshooting section\n" +
			"3. Third: Contact system administrator\n\n" +
			
			"BEFORE CONTACTING SUPPORT:\n" +
			"- Note exact error message\n" +
			"- Record steps to reproduce issue\n" +
			"- Check Java version (java -version)\n" +
			"- Note your operating system\n\n" +
			
			"REPORTING ISSUES:\n" +
			"Provide these details:\n" +
			"1. Error message (exact text)\n" +
			"2. What you were trying to do\n" +
			"3. Steps to reproduce\n" +
			"4. Java version\n" +
			"5. Operating system\n\n" +
			
			"FEEDBACK & SUGGESTIONS:\n" +
			"We welcome:\n" +
			"- Bug reports\n" +
			"- Feature requests\n" +
			"- Usability feedback\n" +
			"- Success stories\n\n" +
			
			"UPDATES:\n" +
			"- Check Help menu for version info\n" +
			"- Updates released quarterly\n" +
			"- Announcements in program\n\n" +
			
			"CONTACT INFORMATION:\n" +
			"Refer to Developer Notes in main Help menu\n" +
			"or contact your system administrator.\n\n" +
			
			"Press ENTER to return to Help Menu..."
		);
		read.nextLine().trim();
	}
	private static void showSystemInfo() {
		System.out.println(
			"SYSTEM INFORMATION\n" +
			"=".repeat(40) + "\n\n" +
			"VERSION DETAILS:\n" +
			"- System: Poultry Farm Management System (PFMS)\n" +
			"- Version: 1.5 (Production Ready)\n" +
			"- Release: December 2024\n" +
			"- Architecture: Java CLI + SQLite\n\n" +
			
			"TECHNICAL SPECIFICATIONS:\n" +
			"- Language: Java 8+\n" +
			"- Database: SQLite 3.45.2\n" +
			"- Storage: Local file system\n" +
			"- Interface: Command Line (CLI)\n" +
			"- Codebase: ~2,500 lines\n\n" +
			
			"SYSTEM CAPABILITIES:\n" +
			"- Records: 10,000+ efficiently\n" +
			"- Users: Multiple simultaneous users\n" +
			"- Reports: 5 types with analytics\n" +
			"- Validation: Real-time inventory checks\n" +
			"- Backup: Manual file copy\n\n" +
			
			"DEVELOPMENT ROADMAP:\n" +
			"Version 1.0: Basic CLI with text files\n" +
			"Version 1.5: SQLite + multi-user + enhanced reporting\n" +
			"Version 2.0: CSV export, improved UI, user roles\n" +
			"Version 2.5: Advanced analytics, automation\n" +
			"Version 3.0: Multi-farm, mobile integration\n\n" +
			
			"DEVELOPER INFORMATION:\n" +
			"- Maintainer: ENJ Digital Hub\n" +
			"- Status: Actively Maintained\n" +
			"- Philosophy: \"Efficient farming begins with organized data management.\"\n\n" +
			
			"LICENSE:\n" +
			"- Type: Proprietary\n" +
			"- Usage: Contact maintainer for permissions\n" +
			"- Distribution: Authorized users only\n\n" +
			
			"Press ENTER to return to Help Menu..."
		);
		read.nextLine().trim();
	}
}