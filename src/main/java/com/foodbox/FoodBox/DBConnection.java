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

	public DBConnection() throws URISyntaxException {
		Connection c = null;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
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
