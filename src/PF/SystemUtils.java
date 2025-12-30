package PF;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class SystemUtils {
	private static boolean isConfirm = false;
	static Scanner read = new Scanner(System.in);
	static LocalDateTime time = LocalDateTime.now();

	public static String center(String s, double width) {
		double padding = (width - s.length()) / 2;
		return " ".repeat((int) padding) + s + " ".repeat((int) padding);
	}
	public static void clearScreen() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (Exception e) {
			for (int i = 0; i < 50; i++) System.out.println();
		}
	}
	public static void exit() {
		clearScreen();
		System.out.println("Closing program . . .");
	}
	public static boolean confirm(Object x) {
		clearScreen();
		System.out.print("Confirm \"" + x + "\" (Y/N)? ");
		char confirm = read.next().charAt(0);
		confirm = Character.toLowerCase(confirm);
		if (confirm == 'y') {
			clearScreen();
			System.out.println("Confirmed . . .");
			isConfirm = true;
		} else if (confirm == 'n') {
			clearScreen();
			System.out.println(". . .");
			isConfirm = false;
		} else {
			clearScreen();
			System.out.println("Invalid Input");
			confirm(x);
		}
		return isConfirm;
	}
	public static String encrypt(String pin) {
		if (pin.length() != 4) throw new IllegalArgumentException("PIN must be 4 digits");
		int d1 = Integer.parseInt("" + pin.charAt(0));
		int d2 = Integer.parseInt("" + pin.charAt(1));
		int d3 = Integer.parseInt("" + pin.charAt(2));
		int d4 = Integer.parseInt("" + pin.charAt(3));

		int temp = d1; d1 = d3; d3 = temp;
		temp = d2; d2 = d4; d4 = temp;

		d1 = (d1 + 7) % 10;
		d2 = (d2 + 7) % 10;
		d3 = (d3 + 7) % 10;
		d4 = (d4 + 7) % 10;

		int encryptedPin = (d1 * 1000) + (d2 * 100) + (d3 * 10) + d4;
		return String.format("%04d", encryptedPin);
	}
	public static String decrypt(String ePin) {
		if (ePin.length() != 4) throw new IllegalArgumentException("Encrypted PIN must be 4 digits");
		int d1 = Integer.parseInt("" + ePin.charAt(0));
		int d2 = Integer.parseInt("" + ePin.charAt(1));
		int d3 = Integer.parseInt("" + ePin.charAt(2));
		int d4 = Integer.parseInt("" + ePin.charAt(3));

		int temp = d1; d1 = d3; d3 = temp;
		temp = d2; d2 = d4; d4 = temp;

		d1 = (d1 + 3) % 10;
		d2 = (d2 + 3) % 10;
		d3 = (d3 + 3) % 10;
		d4 = (d4 + 3) % 10;

		int decryptedPin = (d1 * 1000) + (d2 * 100) + (d3 * 10) + d4;
		return String.format("%04d", decryptedPin);
	}
	public static String monthsOfTheYear(String date) {
		String[] parts = date.split("-");
		int month = Integer.parseInt(parts[1]);
		LocalDateTime monthObj = time.withMonth(month);
		return monthObj.getMonth().toString();
	}
	public static double getPositiveDoubleInput(String prompt) {
		double value = -1;
		do {
			System.out.print(prompt);
			try { value = read.nextDouble(); read.nextLine(); } 
			catch (Exception e) { System.out.println("Invalid input."); read.nextLine(); value = -1; }
		} while (value < 0);
		return value;
	}
	public static int getPositiveIntInput(String prompt) {
		int value = -1;
		do {
			System.out.print(prompt);
			try { value = read.nextInt(); read.nextLine(); } 
			catch (Exception e) { System.out.println("Invalid input."); read.nextLine(); value = -1; }
		} while (value < 0);
		return value;
	}
	/** Reads all rows from a table */
	public static ArrayList<ArrayList<Object>> readTable(String tableName) {
		ArrayList<ArrayList<Object>> records = new ArrayList<>();
		try (Connection conn = Database.connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

			int columnCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				ArrayList<Object> row = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) row.add(rs.getObject(i));
				records.add(row);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return records;
	}
	/** Adds a record to a table */
	public static void addRecord(String tableName, Object[] values) {
		try (Connection conn = Database.connect()) {
			String placeholders = String.join(",", Collections.nCopies(values.length, "?"));
			String sql = "INSERT INTO " + tableName + " VALUES (NULL," + placeholders + ")"; // NULL for autoincrement ID
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				for (int i = 0; i < values.length; i++) pstmt.setObject(i + 1, values[i]);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	/** Updates record(s) in table */
	public static void updateRecord(String tableName, String[] columnNames, Object[] values, String condition) {
		if (columnNames.length != values.length) { System.out.println("Column names and values mismatch!"); return; }
		StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
		for (int i = 0; i < columnNames.length; i++) { sql.append(columnNames[i]).append("=?"); if (i < columnNames.length - 1) sql.append(", "); }
		sql.append(" WHERE ").append(condition);
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			for (int i = 0; i < values.length; i++) pstmt.setObject(i + 1, values[i]);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	/** Checks if a record exists in a table */
	public static boolean recordExists(String tableName, String columnName, Object value) {
		String sql = "SELECT 1 FROM " + tableName + " WHERE " + columnName + " = ? LIMIT 1";
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setObject(1, value);
			try (ResultSet rs = pstmt.executeQuery()) { return rs.next(); }
		} catch (SQLException e) { e.printStackTrace(); }
		return false;
	}
	/** Gets all records as Object[][] (for FarmRecord view) */
	public static Object[][] getAllRecords(String tableName) {
		ArrayList<Object[]> list = new ArrayList<>();
		try (Connection conn = Database.connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {
			int cols = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] row = new Object[cols];
				for (int i = 1; i <= cols; i++) row[i-1] = rs.getObject(i);
				list.add(row);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return list.toArray(new Object[0][]);
	}
	/** Gets single record by date */
	public static Object[] getRecordByDate(String tableName, String dateColumn, String date) {
		try (Connection conn = Database.connect()) {
			String sql = "SELECT * FROM " + tableName + " WHERE " + dateColumn + " = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			ResultSet rs = pstmt.executeQuery();
			int cols = rs.getMetaData().getColumnCount();
			if (rs.next()) {
				Object[] row = new Object[cols];
				for (int i = 1; i <= cols; i++) row[i-1] = rs.getObject(i);
				return row;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	/** Returns a paginated sublist from an ArrayList */
	public static <T> ArrayList<T> getPage(ArrayList<T> records, int page, int pageSize) {
		int fromIndex = (page - 1) * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, records.size());
		if (fromIndex >= records.size()) return new ArrayList<>();
		return new ArrayList<>(records.subList(fromIndex, toIndex));
	}
}
