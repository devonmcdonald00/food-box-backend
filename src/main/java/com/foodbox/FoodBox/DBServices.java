package com.foodbox.FoodBox;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DBServices implements DBRepository{
	@Override
	public List<DBConnection> findAll() throws URISyntaxException {
		var DBConnections = new ArrayList<DBConnection>();
		DBConnections.add(new DBConnection());
		return DBConnections;
	}
}
