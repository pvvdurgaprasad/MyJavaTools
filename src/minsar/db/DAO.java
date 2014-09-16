package minsar.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAO {
	private Connection conn = null;
	private String connDriverClassName = "";
	private String connUserName = "";
	private String connPassword = "";
	private String connUrl = "";
	//private String connDbName = ""; // can be mysql,Oracle,SQL Server,PostgreSQL

	public void loadProperties() throws Exception {
		Properties dbprops;

		dbprops = new Properties();
		try {
			dbprops.load(new FileInputStream(
					"D:/Eclipse_workspace/conf/db.properties"));

			connDriverClassName = dbprops
					.getProperty("minsar.db.driverClassName");
			// System.out.println("Driver Class Name : " + connDriverClassName);

			connUrl = dbprops.getProperty("minsar.db.url");
			// System.out.println("Url : " + connUrl);

			connUserName = dbprops.getProperty("minsar.db.username");
			// System.out.println("User Name : " + connUserName);

			connPassword = dbprops.getProperty("minsar.db.password");
			// System.out.println("Password : " + connPassword.replaceAll("\\S",
			// "*"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	}

	private void openDbConnection() throws Exception {
		try {
			Class.forName(connDriverClassName).newInstance();
			conn = DriverManager.getConnection(connUrl, connUserName,
					connPassword);
			conn.setAutoCommit(false);
			System.out.println("Connected.\n");
		} catch (IllegalAccessException e) {
			System.out.println("Illegal Access Exception: (Open Connection).");
			e.getMessage();
			// e.printStackTrace();
			throw e;
		} catch (InstantiationException e) {
			System.out.println("Instantiation Exception: (Open Connection).");
			e.getMessage();
			// e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception: (Open Connection).");
			e.getMessage();
			// e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			System.out.println("Caught SQL Exception: (Open Connection).");
			e.getMessage();
			// e.printStackTrace();
			throw e;
		}
	}

	public Connection getConnection() throws Exception {
		if (conn == null) {
			this.loadProperties();
			this.openDbConnection();
		}
		return conn;
	}

	public void closeConnection() throws Exception {
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Disconnected.\n");
			} catch (SQLException e) {
				System.out
						.println("Caught SQL Exception: (Closing Connection).");
				e.printStackTrace();
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e2) {
						System.out
								.println("Caught SQL (Rollback Failed) Exception.");
						e2.printStackTrace();
					}
				}
				throw e;
			}
		}

	}

}
