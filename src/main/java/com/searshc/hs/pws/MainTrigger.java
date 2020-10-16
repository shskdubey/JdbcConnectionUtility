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
		if (arguments != null) {
			LOGGER.info("Using following arguments for the jdbc connection : ");
			LOGGER.info("\n url= {} \n userName={} \n password ={} \n sqlQueries= {} \n",arguments[1],arguments[2],arguments[3],Arrays.toString(getArrayOfSqlQueries(arguments)));


			if(arguments[0].equalsIgnoreCase("-v") && arguments.length >= 4 ) {
				JdbcConnectionCmdLine.jdbcConnectionCmdCreater(JDBCDriverFilter.getDriverClass(arguments[1]), arguments[2],
						arguments[3], arguments[4]);
			}
			if (arguments.length >= 4) {
				CreateJdbcConnection.jdbcConnectionTester(JDBCDriverFilter.getDriverClass(arguments[0]), arguments[1],
						arguments[2], arguments[3], getArrayOfSqlQueries(arguments));
			}
		}
	}

	public static String[] getArrayOfSqlQueries(String args[]) {
		return Arrays.copyOfRange(args, 4, args.length);
	}
}
