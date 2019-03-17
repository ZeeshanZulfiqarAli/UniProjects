package application;

import java.sql.*;
public class connectivity {

	private static ResultSet rs=null;
	public static Connection getConnection() {
		//DriverPropertyInfo
		Connection con=null;
		try {
		String connectionUrl = "jdbc:sqlserver://LAPTOP-7QPS6FOI:1433;instanceName=SQLEXPRESS;user=zeeshan;password=zeeshan;databaseName=pharmacy";
		con = DriverManager.getConnection(connectionUrl);
		
		
		} catch(SQLException e) {
			System.out.println(e);
		}
		
		return con;
	}

}
