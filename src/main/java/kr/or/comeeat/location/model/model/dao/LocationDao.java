package kr.or.comeeat.location.model.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationDao {
	@Autowired
	private JdbcTemplate jdbc;
	
}