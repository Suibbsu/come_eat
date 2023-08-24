package kr.or.comeeat.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.comeeat.member.model.dao.MemberDao;
import kr.or.comeeat.member.model.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	//로그인
	public Member selectOneMemeber(String signId, String signPw) {
		Member m = memberDao.selectOneMember(signId, signPw);
		return m;
	}

	//회원가입
	@Transactional
	public int insertMember(Member member) {
		int result = memberDao.insertMember(member);
		return result;
	}
	
	//id중복검사
	public Member selectOneMember(String checkId) {
		Member m = memberDao.selectOneMember(checkId);
		return m;
	}

	public Member selectMemberId(String memberName, String memberEmail) {
			Member member = memberDao.selectMemberId(memberName, memberEmail);
			return member;
	}

	@Transactional
	public int deleteMember(int memberNo) {
		int result  = memberDao.deleteMember(memberNo);
		return result;
	}
	
	@Transactional
	//회원정보 수정
	public int updateMember(Member member) {
		int result = memberDao.updateMember(member);
		return result;
	}
	

}
