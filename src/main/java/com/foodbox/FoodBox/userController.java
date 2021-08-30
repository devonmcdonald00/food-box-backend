package com.foodbox.FoodBox;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class userController {

	@CrossOrigin(origins = "*")
	@GetMapping("/")
	public String greeting() {
		return "hello world!";
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/get_users")
	public List<String> get_users() {
		Connection c = null;
		Statement stmt = null;
		List<String> userList = new ArrayList<String>();
		System.out.println("In here");
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://162.196.141.205:5432/foodbox","dummy", "123456");
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "SELECT * FROM users";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				userList.add(rs.getString("username"));
			}
			return userList;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/user_exists")
    public boolean user_exists(@RequestBody Map<String, Object> payload) {
        System.out.println(payload);
        //System.out.println(payload.get("username"));
        String username = payload.get("username").toString();
        int count = 0;
        
        Connection c = null;
		Statement stmt = null;
		
        try {
        	Class.forName("org.postgresql.Driver");
        	c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodbox", "dummy", "123456");
        	System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "SELECT COUNT(username) FROM users WHERE username='"+username+"'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = Integer.parseInt(rs.getString("count"));
        	return count > 0;
        }
        catch (Exception e) {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	return false;
        }
    }
	
	@CrossOrigin(origins = "*")
	@PostMapping("/check_admin")
    public boolean check_admin(@RequestBody Map<String, Object> payload) {
        System.out.println(payload);
        //System.out.println(payload.get("username"));
        String username = payload.get("username").toString();
        
        Connection c = null;
		Statement stmt = null;
		
        try {
        	Class.forName("org.postgresql.Driver");
        	c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodbox", "dummy", "123456");
        	System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "SELECT (admin) FROM users WHERE username='"+username+"'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			System.out.println(rs.getBoolean("admin"));
			return rs.getBoolean("admin");
        }
        catch (Exception e) {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	return false;
        }
    }
	
	
	@CrossOrigin(origins = "*")
	@PostMapping("/check_password")
    public boolean check_password(@RequestBody Map<String, Object> payload) {
        System.out.println(payload);
        //System.out.println(payload.get("username"));
        String username = payload.get("username").toString();
        String password = payload.get("password").toString();
        
        Connection c = null;
		Statement stmt = null;
		
        try {
        	Class.forName("org.postgresql.Driver");
        	c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodbox", "dummy", "123456");
        	System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "SELECT (password) FROM users WHERE username='"+username+"'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			String dbPass = rs.getString("password");
        	return dbPass.equals(password);
        }
        catch (Exception e) {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	return false;
        }
    }
	
	@CrossOrigin(origins = "*")
	@PostMapping("/add_user")
    public boolean add_user(@RequestBody Map<String, Object> payload) {
        System.out.println(payload);
        //System.out.println(payload.get("username"));
        String username = payload.get("username").toString();
        String password = payload.get("password").toString();
        
        Connection c = null;
		Statement stmt = null;
		
        try {
        	Class.forName("org.postgresql.Driver");
        	c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodbox", "dummy", "123456");
        	System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";
			boolean status = stmt.execute(sql);
        	return !status;
        }
        catch (Exception e) {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	return false;
        }
    }
	
}