package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class ConnectionPool {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
	
	/**
	 * DataSource field.
	 */
	private static DataSource dataSource;
	
	/**
	 * For unit tests
	 * @param dataSource
	 */
	public ConnectionPool(DataSource dataSource) {
		ConnectionPool.dataSource = dataSource;
	}
	
	/**
	 * Get free connection from pool.
	 * 
	 * @return connection.
	 */
	public static synchronized Connection getConnection() {
		
		if (dataSource == null) {
			try {
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				dataSource = (DataSource) envContext.lookup("jdbc/adv_lib");
			} catch (NamingException e) {
				LOGGER.error(e.getMessage());
			}
		}

		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
}
