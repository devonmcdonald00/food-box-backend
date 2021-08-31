package com.foodbox.FoodBox;
import java.net.URISyntaxException;
import java.util.List;


public interface DBRepository {
	List<DBConnection> findAll() throws URISyntaxException;
}
