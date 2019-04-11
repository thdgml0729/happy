package com.happy.prj.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.happy.prj.dtos.Member_DTO;

@Repository
public class Member_Dao implements Member_Interface{
	
	private final String NS = "com.happy.prj.Statement_Member.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private PasswordEncoder passwordEncoder; // crypto로 해야한다.

	@Override
	public List<Member_DTO> memberList() {
		return sqlSession.selectList(NS+"memberList");
	}

	@Override
	public boolean signupmember(Member_DTO dto) {
		//전달받은 비밀번호를 암호화 처리 
		String encodePw = passwordEncoder.encode(dto.getPw());
		dto.setPw(encodePw);
		
		int n = sqlSession.insert(NS+"signupmember", dto);
		return n>0? true:false;
	}

	@Override
	public boolean idduplicatecheck(String id) {
		int n = sqlSession.selectOne(NS+"idduplicatecheck", id);
		return n>0?true:false;
	}

	@Override
	public Member_DTO loginmember(Member_DTO dto) {
		//id를 통해 DB의 페스워드 확인 : selStringPw
//		String securityPw = sqlSession.selectOne(NS+"selStringPw", dto);
//		String encodePw = passwordEncoder.encode(dto.getPw());
//		
//		System.out.println("DB의 Password : "+securityPw);
//		System.out.println("입력받은 PassWord :"+encodePw);
//		
//		if(securityPw.equals(encodePw)) {
//			System.out.println("비밀번호 일치");
//			return sqlSession.selectOne(NS+"loginmember",dto);
//		}
		
		//좋은 방법
		//입력한 id에 해당하는 pw(DB)
		String securityPw = sqlSession.selectOne(NS+"selStringPw", dto); //$2a$10$GgCM.MtWE2uQlI6OP/0oSe9cgBhFUeEBqNHj3gP4/n4mI56KnE9ZW
		
		//dto.getPw() : 내가 로그인시 입력한 비밀번호(1111)
		//$2a$10$GgCM.MtWE2uQlI6OP/0oSe9cgBhFUeEBqNHj3gP4/n4mI56KnE9ZW -> 1111로 디코딩해서 두개가 일치하는지 확인 
		//이 순서로 넣어주어야 한다.
		if(passwordEncoder.matches(dto.getPw(), securityPw)) { // 역으로 디코딩해줘서
			dto.setPw(securityPw);
			System.out.println("비밀번호 일치");
			return sqlSession.selectOne(NS+"loginmember",dto);
		}
		
		return null;
	}

}
