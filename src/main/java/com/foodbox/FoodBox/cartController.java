package com.foodbox.FoodBox;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class cartController {
	@CrossOrigin(origins = "*")
	@PostMapping("/get_cart")
	public Cart get_cart(@RequestBody Map<String, Object> payload) {
		Connection c = null;
		Statement stmt = null;
		String user = payload.get("user").toString();
		Cart newCart = new Cart();
		
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodbox","dummy", "123456");
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "SELECT * FROM carts WHERE user='" + user + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Product newProduct = new Product();
				newProduct.setName(rs.getString("name"));
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
	
}
