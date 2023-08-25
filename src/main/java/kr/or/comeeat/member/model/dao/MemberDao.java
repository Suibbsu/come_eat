package kr.or.comeeat.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.comeeat.member.model.vo.Member;
import kr.or.comeeat.member.model.vo.MemberRowMapper;
import kr.or.comeeat.member.model.vo.SavePlaceRowMapper;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	@Autowired SavePlaceRowMapper savePlaceRowMapper;
	
	public Member selectOneMember(String signId, String signPw) {
		String query = "select * from member where member_id=? and member_pw=?";
		List list = jdbc.query(query, memberRowMapper, signId, signPw);
		if(list.isEmpty()) {
			return null; 
		}
		return (Member)list.get(0);
	}

	//회원가입
	public int insertMember(Member member) {
		String query ="insert into member values(member_seq.nextval,?,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {member.getMemberId(),member.getMemberPw(),member.getMemberName(),member.getMemberEmail(),2,member.getMemberPhone()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//id중복검사
	public Member selectOneMember(String checkId) {
		String query = "select * from member where member_id=?";
		List list = jdbc.query(query, memberRowMapper,checkId);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}

	public Member selectMemberId(String memberName, String memberEmail) {
		String query = "select * from member where member_name=? and member_email=?";
		List list = jdbc.query(query, memberRowMapper,memberName,memberEmail);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}
	
	//회원 삭제
	public int deleteMember(int memberNo) {
		String query = "delete from member where member_no=?";
		int result = jdbc.update(query,memberNo);
		return result;
	}

	//회원정보 수정
	public int updateMember(Member member) {
		String query = "update member set member_pw=?,member_name=?,member_email=?,member_phone=? where member_id=?";
		Object[] params = {member.getMemberPw(), member.getMemberName(), member.getMemberEmail(), member.getMemberPhone(),member.getMemberId()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//전체회원 조회 : 관리자 (관리자가 1순위로 조회될수 있게 쿼리문 작성)
	public List selectAllMember() {
		String query = "select * from member order by (case when member_Level = 1 then 1 else 2 end),member_no asc";
		List list = jdbc.query(query, memberRowMapper);
		return list;
	}
	public int changeLevel(int memberNo, int memberLevel) {
		String query = "update member set member_level = ? where member_no = ?";
		Object[] params = {memberLevel,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}

	//맛집저장조회
	public List selectSavePlace(int loNo, int memberNo) {
		String query = "SELECT * FROM SAVEPLACE WHERE LO_NO = ? AND MEMBER_NO = ?";
		List list = jdbc.query(query,savePlaceRowMapper, loNo, memberNo);
		return list;
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

}
