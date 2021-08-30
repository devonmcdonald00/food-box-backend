package com.foodbox.FoodBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class FoodBoxApplication {
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
      return "Hello World!";
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

	public static void main(String[] args) {
		SpringApplication.run(FoodBoxApplication.class, args);
	}

}
