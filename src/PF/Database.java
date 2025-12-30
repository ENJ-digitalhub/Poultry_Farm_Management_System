package PF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;

public class Database {
	// Flag to track if database is already initialized
	private static boolean isInitialized = false;
	public static Connection connect() {
		Connection conn = null;
		try {
			// Load the SQLite JDBC driver
			Class.forName("org.sqlite.JDBC");
			
			// Database path - this creates the database in a data folder
			String url = "jdbc:sqlite:data/poultry.db";
			
			// Make sure the data folder exists
			File dataDir = new File("data");
			if (!dataDir.exists()) {
				dataDir.mkdirs();
				System.out.println("Created data folder for database storage");
			}
			
			// Connect to the database
			conn = DriverManager.getConnection(url);
			
			// If this is the first connection, check if tables exist
			if (!isInitialized) {
				checkIfTablesExist(conn);
			}
			
			System.out.println("Connected to database successfully");
		} catch (ClassNotFoundException e) {
			System.err.println("SQLite JDBC driver not found!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Connection to database failed!");
			e.printStackTrace();
		}
		return conn;
	}
	// Check if our tables already exist in the database
	private static void checkIfTablesExist(Connection conn) {
		try {
			// SQL query to check if users table exists
			String checkSQL = "SELECT COUNT(*) as count FROM sqlite_master WHERE type='table' AND name='users'";
			
			try (Statement checkStmt = conn.createStatement();
				 ResultSet result = checkStmt.executeQuery(checkSQL)) {
				
				// If result is 0, tables don't exist yet
				if (result.next() && result.getInt("count") == 0) {
					System.out.println("Tables not found - creating them now...");
					createAllTables(conn);
					isInitialized = true;
				} else {
					// Tables already exist
					System.out.println("Database tables are ready");
					isInitialized = true;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking for tables: " + e.getMessage());
			
			// If checking failed, try to create tables anyway
			try {
				System.out.println("Trying to create tables...");
				createAllTables(conn);
				isInitialized = true;
			} catch (SQLException e2) {
				System.err.println("Could not create tables: " + e2.getMessage());
			}
		}
	}
	// Create all necessary tables for the application
	public static void init() {
		try (Connection conn = connect()) {
			if (conn != null) {
				createAllTables(conn);
				isInitialized = true;
			}
		} catch (SQLException e) {
			System.out.println("Database setup failed");
			e.printStackTrace();
		}
	}
	 // Actually create the tables in the database
	private static void createAllTables(Connection conn) throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			
			// Create users table if it doesn't exist
			// Stores user information like name, username, and PIN
			String usersTable = """
				CREATE TABLE IF NOT EXISTS users (
					user_id INTEGER PRIMARY KEY AUTOINCREMENT,
					firstname TEXT NOT NULL,
					lastname TEXT NOT NULL,
					username TEXT UNIQUE NOT NULL,
					pin TEXT NOT NULL,
					created_at TEXT DEFAULT CURRENT_TIMESTAMP
				);
			""";
			stmt.execute(usersTable);
			
			// Create farm_records table if it doesn't exist
			// Stores daily farm activity records
			String farmRecordsTable = """
				CREATE TABLE IF NOT EXISTS farm_records (
					record_id INTEGER PRIMARY KEY AUTOINCREMENT,
					record_date TEXT NOT NULL,         -- just the date, matches your data array
					eggs_collected INTEGER NOT NULL,
					feeds_used REAL NOT NULL,
					death INTEGER NOT NULL,
					comment TEXT,
					created_at TEXT DEFAULT CURRENT_TIMESTAMP -- full date-time automatically
				);

			""";
			stmt.execute(farmRecordsTable);
			
			// Create inventory table if it doesn't exist
			// Tracks current inventory of birds, eggs, and feeds
			String inventoryTable = """
				CREATE TABLE IF NOT EXISTS inventory (
					inventory_id INTEGER PRIMARY KEY AUTOINCREMENT,
					bird_no INTEGER NOT NULL,
					eggs_no INTEGER NOT NULL,
					feeds_no INTEGER NOT NULL,
					created_at TEXT DEFAULT CURRENT_TIMESTAMP
				);
			""";
			stmt.execute(inventoryTable);

			System.out.println("All database tables created successfully");

		}
	}
	// Check if database has been set up
	public static boolean isDatabaseReady() {
		return isInitialized;
	}
}