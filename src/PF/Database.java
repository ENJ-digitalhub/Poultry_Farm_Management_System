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
		int maxRetries = 5;
		int attempt = 0;
		int retryDelay = 1000; // 1 second
		
		for (attempt = 1; attempt <= maxRetries; attempt++) {
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
				
				// Add connection parameters to handle OneDrive/locking issues
				url += "?journal_mode=WAL&synchronous=NORMAL&locking_mode=NORMAL&busy_timeout=5000";
				
				// Connect to the database
				conn = DriverManager.getConnection(url);
				
				// Set pragmas for better handling
				try (Statement stmt = conn.createStatement()) {
					stmt.execute("PRAGMA journal_mode=WAL");
					stmt.execute("PRAGMA synchronous=NORMAL");
					stmt.execute("PRAGMA locking_mode=NORMAL");
					stmt.execute("PRAGMA busy_timeout=5000");
				}
				
				// If this is the first connection, check if tables exist
				if (!isInitialized) {
					checkIfTablesExist(conn);
				}
				
				System.out.println("[Notification] Connected to database successfully (attempt " + attempt + ")");
				attempt++;
				return conn;
				
			} catch (ClassNotFoundException e) {
				System.err.println("SQLite JDBC driver not found!");
				e.printStackTrace();
				break;
			} catch (SQLException e) {
				System.err.println("Connection attempt " + attempt + " failed: " + e.getMessage());
				attempt++;
				
				// Close connection if it was partially created
				if (conn != null) {
					try { conn.close(); } catch (SQLException e2) { /* ignore */ }
				}
				
				if (attempt < maxRetries) {
					System.out.println("Retrying in " + (retryDelay/1000) + " seconds...");
					try {
						Thread.sleep(retryDelay);
						retryDelay *= 2; // Exponential backoff
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
						break;
					}
				} else {
					System.err.println("Failed to connect after " + maxRetries + " attempts");
					attempt++;
					e.printStackTrace();
				}
			}
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
					System.out.println("[Notification] Database tables are ready");
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
			
			// Create farm_records table with TEXT created_by
			String farmRecordsTable = """
				CREATE TABLE IF NOT EXISTS farm_records (
					record_id INTEGER PRIMARY KEY AUTOINCREMENT,
					record_date TEXT NOT NULL DEFAULT CURRENT_DATE ,
					eggs_collected INTEGER NOT NULL,
					broken_eggs INTEGER NOT NULL,
					feeds_used REAL NOT NULL,
					death INTEGER NOT NULL,
					comment TEXT,
					created_by TEXT NOT NULL,
					created_at TEXT DEFAULT CURRENT_TIMESTAMP
				);
			""";
			stmt.execute(farmRecordsTable);
			
			// Create inventory table with TEXT created_by
			String inventoryTable = """
				CREATE TABLE IF NOT EXISTS inventory (
					inventory_id INTEGER PRIMARY KEY AUTOINCREMENT,
					bird_no INTEGER NOT NULL,
					eggs_no INTEGER NOT NULL,
					broken_eggs INTEGER NOT NULL,
					feeds_no INTEGER NOT NULL,
					created_by TEXT NOT NULL, 
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