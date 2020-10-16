package com.searshc.hs.pws.filter;

import java.sql.Driver;
import java.sql.SQLException;

public class JDBCDriverFilter {
	public static Driver getDriverClass(String driverName) {
		try {
			if (driverName.equalsIgnoreCase("mysql")) {
				return new com.mysql.jdbc.Driver();
			} else if (driverName.equalsIgnoreCase("oracle")) {
				return new oracle.jdbc.OracleDriver();
			} else if (driverName.equalsIgnoreCase("db2")) {
				return new com.ibm.db2.jcc.DB2Driver();
			} else if (driverName.equalsIgnoreCase("informix")) {
				return new com.informix.jdbc.IfxDriver();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
