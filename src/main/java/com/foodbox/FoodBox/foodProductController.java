package com.foodbox.FoodBox;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@Component
@RestController
public class foodProductController {
	
	@Autowired
	private Connection c;

	public Connection getC() {
		return c;
	}

	private void setC(Connection c) {
		this.c = c;
	}

	public foodProductController() throws URISyntaxException {
		Connection connection = null;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
	    try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(dbUrl,username, password);
			System.out.println("Opened database successfully");
			setC(connection);
	    }
	    catch (Exception e) {
	    	System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    	setC(null);
	    }
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/get_products")
	public List<Product> get_products() {
		Connection c = null;
		Statement stmt = null;
		List<Product> productList = new ArrayList<Product>();
		
		try {
			c = this.getC();
			stmt = c.createStatement();
			String sql = "SELECT * FROM food_products";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Product newProduct = new Product();
				newProduct.setName(rs.getString("name"));
				System.out.println(newProduct.getName());
				newProduct.setPrice(rs.getDouble("price"));
				newProduct.setDescription(rs.getString("description"));
				newProduct.setCuisine(rs.getString("cuisine"));
				newProduct.setEnabled(rs.getBoolean("enabled"));
				newProduct.setImageurl(rs.getString("imageurl"));
				productList.add(newProduct);
			}
			return productList;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/get_cuisines")
	public List<Cuisine> get_cuisines() {
		Connection c = null;
		Statement stmt = null;
		List<Cuisine> cuisineList = new ArrayList<Cuisine>();
		
		try {
			c = this.getC();
			stmt = c.createStatement();
			String sql = "SELECT * FROM cuisines ORDER BY cuisine ASC";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Cuisine newCuisine = new Cuisine();
				newCuisine.setCuisine(rs.getString("cuisine"));
				newCuisine.setEnabled(rs.getBoolean("enabled"));
				newCuisine.setFlagImageURL(rs.getString("flag_image_url"));
				cuisineList.add(newCuisine);
			}
			return cuisineList;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping("/change_cuisine")
	public boolean change_cuisine(@RequestBody Map<String, Object> payload) {
		Connection c = null;
		Statement stmt = null;
		String cuisine = payload.get("cuisine").toString();
		String enabled = payload.get("enabled").toString();
		
		try {
			c = this.getC();
			stmt = c.createStatement();
			String sql = "UPDATE cuisines SET enabled="+enabled+" WHERE cuisine='" + cuisine + "';";
			String product_sql = "UPDATE food_products SET enabled="+ enabled +" WHERE cuisine='"+cuisine+"';";
			boolean rs = stmt.execute(sql);
			boolean product_rs = stmt.execute(product_sql);
			return !rs && !product_rs;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/add_product")
	public boolean add_product(@RequestBody Map<String, Object> payload) {
		
		String name = payload.get("name").toString();
		String description = payload.get("description").toString();
		String cuisine = payload.get("cuisine").toString();
		String imageURL = payload.get("imageurl").toString();
		String price = payload.get("price").toString();
		Connection c = null;
		Statement stmt = null;
		
		try {
			c = this.getC();
			stmt = c.createStatement();
			String sql = "INSERT INTO food_products (name, price, cuisine, description, enabled, imageurl) values ('" + name + "', "+price+", '" + cuisine + "', '" + description + "', true, '" + imageURL + "')";
			boolean rs = stmt.execute(sql);
			return !rs;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/delete_product")
	public boolean delete_product(@RequestBody Map<String, Object> payload) {
		
		String name = payload.get("name").toString();
		Connection c = null;
		Statement stmt = null;
		
		try {
			c = this.getC();
			stmt = c.createStatement();
			String sql = "DELETE FROM food_products WHERE name='"+name+"';";
			boolean rs = stmt.execute(sql);
			return !rs;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/change_product")
	public boolean change_product(@RequestBody Map<String, Object> payload) {
		
		String name = payload.get("name").toString();
		String description = payload.get("description").toString();
		String cuisine = payload.get("cuisine").toString();
		String imageURL = payload.get("imageurl").toString();
		String price = payload.get("price").toString();
		String enabled = payload.get("enabled").toString();
		Connection c = null;
		Statement stmt = null;
		
		try {
			c = this.getC();
			stmt = c.createStatement();
			String sql = "UPDATE food_products SET name='" + name + "', price="+price+", cuisine='" + cuisine + "', description='" + description + "', enabled=" + enabled + ", imageurl='" + imageURL + "' WHERE name='"+name+"';";
			boolean rs = stmt.execute(sql);
			return !rs;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/add_cuisine")
	public boolean add_cuisine(@RequestBody Map<String, Object> payload) {
		
		String cuisine = payload.get("cuisine").toString();
		String enabled = payload.get("enabled").toString();
		String flagImageURL = payload.get("flag_image_url").toString();
		
		Connection c = null;
		Statement stmt = null;
		
		try {
			c = this.getC();
			stmt = c.createStatement();
			String sql = "INSERT INTO cuisines (cuisine, enabled, flag_image_url) values ('" + cuisine + "', "+enabled+", '" + flagImageURL + "')";
			boolean rs = stmt.execute(sql);
			return !rs;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/delete_cuisine")
	public boolean delete_cuisine(@RequestBody Map<String, Object> payload) {
		
		String cuisine = payload.get("cuisine").toString();
		Connection c = null;
		Statement stmt = null;
		
		try {
			c = this.getC();
			stmt = c.createStatement();
			String sql = "DELETE FROM cuisines WHERE cuisine='"+cuisine+"';";
			boolean rs = stmt.execute(sql);
			return !rs;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	
	
}