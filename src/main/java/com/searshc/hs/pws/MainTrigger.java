package com.searshc.hs.pws;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.searshc.hs.pws.filter.JDBCDriverFilter;
import com.searshc.hs.pws.util.CreateJdbcConnection;
import com.searshc.hs.pws.util.JdbcConnectionCmdLine;

public class MainTrigger {
	private static final Logger LOGGER = LogManager.getLogger(MainTrigger.class.getName());

	public static void main(String arguments[]) {
		LOGGER.info("=================================================================");
		if (arguments != null && arguments.length>=4) {
			LOGGER.info("Using following arguments for the jdbc connection : ");
			
			if(arguments[0].equalsIgnoreCase("-v") && arguments.length >= 5 ) {
				LOGGER.info("\n url= {} \n userName={} \n password ={} \n sqlQueries= {} \n",arguments[1],arguments[2],arguments[3],Arrays.toString(getArrayOfSqlQueries(arguments)));
				CreateJdbcConnection.jdbcConnectionTester(JDBCDriverFilter.getDriverClass(arguments[1]), arguments[2],
						arguments[3], arguments[4], getArrayOfSqlQueries(arguments));
			}
			else {
				LOGGER.info("\n url= {} \n userName={} \n password ={} \n ",arguments[1],arguments[2],arguments[3]);
				JdbcConnectionCmdLine.jdbcConnectionCmdCreater(JDBCDriverFilter.getDriverClass(arguments[0]), arguments[1],
						arguments[2], arguments[3]);
			}
		}
		else {
			System.out.println("\n\n uh-oh! Invalid parameters . Try again ");
			System.out.println("Usage: \n <DatabaseType> <JDBC_URL> <UserName> <password> \n DatabaseType can be:  mysql/oracle/db2/informix ");
		}
	}

	public static String[] getArrayOfSqlQueries(String args[]) {
		return Arrays.copyOfRange(args, 5, args.length);
	}
}
