package com.searshc.hs.pws.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcConnectionCmdLine {
	private static final Logger LOGGER = LogManager.getLogger(JdbcConnectionCmdLine.class.getName());

	public static void jdbcConnectionCmdCreater(Driver driver, String url, String user, String password) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			LOGGER.info("Registering Driver {} ", driver.getClass().getCanonicalName());
			DriverManager.registerDriver(driver);

			LOGGER.info("Creating JDBC connection using {} ", url);
			// Creating JDBC Connection by reference to connection interface
			con = DriverManager.getConnection(url, user, password);

			LOGGER.info("==========  Woo-hoo!  Connection Successful =========");
			st = con.createStatement();

			String strInput ="sql query";
			Scanner input = new Scanner(System.in);
			System.out.print("> Want to run queries in debug mode (Y/N) \n> ");
			String debugFlag = input.nextLine();
			while (!strInput.equalsIgnoreCase("quit") && !debugFlag.equalsIgnoreCase("quit")) {
				System.out.print("> ");
				strInput = input.nextLine();
				if (strInput != null && !strInput.equalsIgnoreCase("quit")) {
					/*
					 * LOGGER.info(
					 * "Connection has been created , moving forward and executing sql queries passed in args"
					 * ); System.out.println("\n\n"); LOGGER.info(
					 * "=================================================================================\n"
					 * + "Now Executing:  {} ", strInput);
					 */
					rs = st.executeQuery(strInput);
					ResultSetMetaData rsmd = rs.getMetaData();
//					LOGGER.info("Got the results initializing printer ------> ");
					System.out.println(
							"\n****************************************************************************************\n");

					// Printing column names
					int numberOfColumns = rsmd.getColumnCount();

					for (int i = 1; i <= numberOfColumns; i++) {
						if (i > 1)
							System.out.print(",  ");
						String columnName = rsmd.getColumnName(i);
						System.out.print(columnName);
					}
					System.out.println("");

					// print Column Values
					while (rs.next()) {
						for (int i = 1; i <= numberOfColumns; i++) {
							if (i > 1)
								System.out.print(",  ");
							String columnValue = rs.getString(i);
							System.out.print(columnValue);
						}
						System.out.println("");
					}
					System.out.println(
							"\n****************************************************************************************\n");
					System.out.println("\n");
					if (debugFlag.equalsIgnoreCase("Y")) {
						LOGGER.info("debugFlag was set to true , Processing Metadata obtained : \n");
						// print Column Data Types
						printColTypes(rsmd);
						System.out.println("=======================================================");
					}
				}

			}

			input.close();
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			try {
				if (con != null)
					con.close();
				if (st != null)
					st.close();
				if (rs != null) 
					rs.close();
				System.exit(0);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void printColTypes(ResultSetMetaData rsmd) throws SQLException {
		int columns = rsmd.getColumnCount();
		System.out.println(columns);
		for (int i = 1; i <= columns; i++) {
			int jdbcType = rsmd.getColumnType(i);
			String name = rsmd.getColumnTypeName(i);
			System.out.print("Column " + i + " is JDBC type " + jdbcType);
			System.out.println(", which the DBMS calls " + name);
		}
	}
}
