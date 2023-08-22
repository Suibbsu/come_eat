package kr.or.comeeat.location.model.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import kr.or.comeeat.location.model.vo.Location;
import kr.or.comeeat.location.model.vo.LocationRowMapper;

@Component
public class LocationDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private LocationRowMapper locationRowMapper;

	//부산맛집 저장하기
	public int busanInsert(ArrayList<Location> list) {
		String query = "INSERT INTO LOCATION VALUES(LOCATION_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
		int result = 0;
		for(Location location : list) {
			Object[] params = {location.getLoCode(),location.getLoTitle(),location.getLoInfo(),location.getLoLat(),location.getLoLng(),location.getLoAddr(),location.getLoTel(),location.getLoTime(),location.getLoMenu(),location.getLoThumb()};
			result = jdbc.update(query,params);
		}
		return result;
	}

	//부산맛집 가져오기
	public List busanSelect() {
		String query = "SELECT * FROM LOCATION WHERE LO_CODE='BUSAN'";
		List bList = jdbc.query(query, locationRowMapper);
		return bList;
	}
	
}
