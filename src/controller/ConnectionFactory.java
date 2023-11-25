package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static String username = "root";
	private static final String password = "";
	private static final String DbURL = "jdbc:mariadb://localhost/contatosdb";
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(DbURL,username,password);
	}
}
