package com.foodbox.FoodBox;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
	private Connection c;
	
	public Connection getDBConnection() {
		return c;
	}

	public void setDBConnection(Connection dBConnection) {
		c = dBConnection;
	}

	public DBConnection() {
		Connection c = null;
		URI dbUri = null;
		try {
			dbUri = new URI(System.getenv("DATABASE_URL"));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
	    try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(dbUrl,username, password);
			System.out.println("Opened database successfully");
			setDBConnection(c);
	    }
	    catch (Exception e) {
	    	System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    	setDBConnection(null);
	    }
	}
}
