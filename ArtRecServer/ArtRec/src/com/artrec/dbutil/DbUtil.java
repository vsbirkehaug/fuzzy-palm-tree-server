package com.artrec.dbutil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connection class for the MySQL database
 * @author Vilde
 *
 */
public class DbUtil {
	
	private static Connection connection = null;
	
	/**
	 * Provides a static connection, cannot be instantiated more than once.
	 * If the connection does not exist, it is created.
	 * @return Connection The connection
	 * @throws SQLException This this if there is something wrong with the SQL
	 */
	public static Connection getConnection() throws SQLException {
		
		if (connection != null && !connection.isClosed()) {
			return connection;
		}
		else {
			try {
				Properties prop = new Properties();
				InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("/db.properties");
				prop.load(inputStream);
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");
				
				Class.forName(driver);
				
				connection = DriverManager.getConnection(url, user, password);
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return connection;
	}
	
	/**
	 * Static connection method to return the connection
	 * @return Connection to connect to the database
	 */
	protected static Connection connectToDatabase() {
		Connection conn = null;
		
		try {
			conn = DbUtil.getConnection();
			return conn;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
}
