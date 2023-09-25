package kr.or.comeeat.location.model.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import kr.or.comeeat.location.model.vo.Location;
import kr.or.comeeat.location.model.vo.LocationRowMapper;
import kr.or.comeeat.location.model.vo.SavePlace;
import kr.or.comeeat.location.model.vo.SavePlaceRowMapper;

@Repository
public class LocationDao {
	private static final String Location = null;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private LocationRowMapper locationRowMapper;
	@Autowired SavePlaceRowMapper savePlaceRowMapper;
	
	
	//맛집저장조회
	public int selectSavePlace(int loNo, int memberNo) {
		int result = 0;
		String query = "SELECT * FROM SAVEPLACE WHERE LO_NO = ? AND MEMBER_NO = ?";
		List list = jdbc.query(query,savePlaceRowMapper, loNo, memberNo);
		if(!list.isEmpty()) {
			result = 1;
		}
		return result;
	}
	
	//맛집저장하기
	public int insertSavePlace(int loNo, int memberNo) {
		String query = "INSERT INTO SAVEPLACE VALUES(SAVEPLACE_SEQ.NEXTVAL,?,?)";
		int result = jdbc.update(query, memberNo, loNo);
		return result;
	}

	//맛집저장취소하기
	public int deleteSavePlace(int loNo, int memberNo) {
		String query = "DELETE FROM SAVEPLACE WHERE LO_NO = ? AND MEMBER_NO = ?";
		int result = jdbc.update(query, loNo, memberNo);
		return result;
	}

	//맛집 저장하기
	public int locationInsert(ArrayList<Location> list) {
		String query = "INSERT INTO LOCATION VALUES(LOCATION_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
		int result = 0;
		for(Location location : list) {
			Object[] params = {location.getLoCode(),location.getLoTitle(),location.getLoInfo(),location.getLoLat(),location.getLoLng(),location.getLoAddr(),location.getLoTel(),location.getLoTime(),location.getLoMenu(),location.getLoThumb()};
			result = jdbc.update(query,params);
		}
		return result;
	}

	//맛집 가져오기
	public List locationSelect(String loCode, int end, int start) {
		String query = "SELECT l.*,round((select avg(review_grade) from review r where r.lo_no=l.lo_no),1) star_rate FROM (SELECT ROWNUM AS RNUM, L.* FROM (SELECT * FROM LOCATION WHERE LO_CODE=? ORDER BY LO_NO DESC) L) L WHERE RNUM BETWEEN ? AND ?";
		Object[] params = {loCode,start,end};
		List bList = jdbc.query(query, locationRowMapper,params);
		return bList;
	}
	
	//네비게이션제작 - 전체게시물 수 조회
	public int selectTotal(String loCode) {
		String query = "SELECT COUNT(*) FROM LOCATION WHERE LO_CODE=?";
		int result = jdbc.queryForObject(query,Integer.class,loCode);
		return result;
	}

	//지도위치 전체출력
	public List locationMap(String loCode) {
		String query = "SELECT l.*,round((select avg(review_grade) from review r where r.lo_no=l.lo_no),1) star_rate FROM LOCATION L WHERE LO_CODE=?";
		List list = jdbc.query(query, locationRowMapper,loCode);
		return list;
	}


	public List searchAroundPlace(String searchPlace) {
		//select l.*,round((select avg(review_grade) from review r where r.lo_no=l.lo_no),1) star_rate from location l where lo_addr like '%서울%'
		String query = "select l.*,round((select avg(review_grade) from review r where r.lo_no=l.lo_no),1) star_rate from location l where lo_addr like '%' || ? || '%'";
		List list = jdbc.query(query, locationRowMapper,searchPlace);
		return list;
	}

	public List savePlaceMember(int memberNo) {
		String query ="select * from saveplace where member_no =?";
		List list = jdbc.query(query, savePlaceRowMapper,memberNo);
		return list;
	}

	
	
	//베스트맛집조회
	public List reviewBest(int end, int start) {
		String query = "SELECT L.* ,ROUND((SELECT AVG(REVIEW_GRADE) FROM REVIEW R WHERE R.LO_NO=L.LO_NO),1) STAR_RATE FROM LOCATION L INNER JOIN (SELECT LO_NO FROM (SELECT ROWNUM AS RNUM, R.* FROM (SELECT LO_NO, AVG(REVIEW_GRADE) GRADE, COUNT(*) COUNT FROM REVIEW GROUP BY LO_NO ORDER BY GRADE DESC, COUNT DESC, LO_NO DESC) R ORDER BY 1)  WHERE RNUM BETWEEN ? AND ?) R  ON L.LO_NO = R.LO_NO";
		List list = jdbc.query(query, locationRowMapper, start, end);
		return list;
	}
	
	//네비게이션제작 - 전체게시물 수 조회 - 전체맛집
	public int searchTotalBest() {
		String query = "SELECT COUNT(*) FROM (SELECT DISTINCT LO_NO FROM REVIEW)";
		int result = jdbc.queryForObject(query,Integer.class);
		return result;
	}
	
	//검색조회 - 일반
	public List searchList(String search, int end, int start) {
		String query = "SELECT L.* ,ROUND((SELECT AVG(REVIEW_GRADE) FROM REVIEW R WHERE R.LO_NO=L.LO_NO),1) STAR_RATE FROM (SELECT ROWNUM AS RNUM, L.* FROM (SELECT * FROM LOCATION WHERE (LO_ADDR LIKE ?) OR (LO_TITLE LIKE ?) OR  (LO_INFO LIKE ?) OR (LO_MENU LIKE ?) ORDER BY LO_NO DESC) L ) L WHERE RNUM BETWEEN ? AND ?";
		Object[] params = {"%"+search+"%","%"+search+"%","%"+search+"%","%"+search+"%",start,end};
		List bList = jdbc.query(query, locationRowMapper,params);
		return bList;
	}

	//네비게이션제작 - 전체게시물 수 조회 - 일반
	public int searchTotal(String search) {
		String query = "SELECT COUNT(*) FROM (SELECT * FROM LOCATION WHERE (LO_ADDR LIKE ?) OR (LO_TITLE LIKE ?) OR  (LO_INFO LIKE ?) OR (LO_MENU LIKE ?))";
		Object[] params = {"%"+search+"%","%"+search+"%","%"+search+"%","%"+search+"%"};
		int result = jdbc.queryForObject(query,Integer.class,params);
		return result;
	}

	//검색조회 - 전체맛집
	public List searchListAll(String search, int end, int start) {
		String query = "SELECT L.* ,ROUND((SELECT AVG(REVIEW_GRADE) FROM REVIEW R WHERE R.LO_NO=L.LO_NO),1) STAR_RATE FROM (SELECT ROWNUM AS RNUM, L.* FROM (SELECT * FROM LOCATION ORDER BY LO_NO DESC) L) L WHERE RNUM BETWEEN ? AND ?";
		Object[] params = {start,end};
		List bList = jdbc.query(query, locationRowMapper,params);
		return bList;
	}
	
	//네비게이션제작 - 전체게시물 수 조회 - 전체맛집
	public int searchTotalAll() {
		String query = "SELECT COUNT(*) FROM (SELECT * FROM LOCATION)";
		int result = jdbc.queryForObject(query,Integer.class);
		return result;
	}
	
	//검색조회 - 지역 포함 일반검색
	public List searchListLocode(String loCode, String search, int end, int start) {
		String query = "SELECT L.* ,ROUND((SELECT AVG(REVIEW_GRADE) FROM REVIEW R WHERE R.LO_NO=L.LO_NO),1) STAR_RATE FROM (SELECT ROWNUM AS RNUM, L.* FROM (SELECT * FROM LOCATION WHERE ((LO_ADDR LIKE ?) OR (LO_TITLE LIKE ?) OR  (LO_INFO LIKE ?) OR (LO_MENU LIKE ?)) AND LO_CODE=? ORDER BY LO_NO DESC) L ) L WHERE RNUM BETWEEN ? AND ?";
		Object[] params = {"%"+search+"%","%"+search+"%","%"+search+"%","%"+search+"%",loCode,start,end};
		List bList = jdbc.query(query, locationRowMapper,params);
		return bList;
	}
	
	//네비게이션제작 - 전체게시물 수 조회 - 일반
	public int searchTotalLocode(String search, String loCode) {
		String query = "SELECT COUNT(*) FROM (SELECT * FROM LOCATION WHERE ((LO_ADDR LIKE ?) OR (LO_TITLE LIKE ?) OR  (LO_INFO LIKE ?) OR (LO_MENU LIKE ?)) AND LO_CODE=?)";
		Object[] params = {"%"+search+"%","%"+search+"%","%"+search+"%","%"+search+"%",loCode};
		int result = jdbc.queryForObject(query,Integer.class,params);
		return result;
	}

	//마이페이지 맛집 저장 필요한 정보 불러오기
	public List selectMySavePlace(int memberNo) {
		String query = "select l.*,round((select avg(review_grade) from review r where r.lo_no=l.lo_no),1) star_rate from location l where lo_no in (select lo_no from saveplace where member_no=?)";
		List list = jdbc.query(query, locationRowMapper,memberNo);
		return list;
	}

	
}
