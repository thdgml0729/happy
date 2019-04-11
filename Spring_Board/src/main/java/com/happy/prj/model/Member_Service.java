package com.happy.prj.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.prj.dtos.Member_DTO;

@Service
public class Member_Service implements Member_IService {
//Dao 조합할 것 X
	
	private Logger logger = LoggerFactory.getLogger(Member_Service.class);
	
	@Autowired
	private Member_Interface member_Interface;
	
	@Override
	public List<Member_DTO> memberList() {
		logger.info("회원 조회 memList");
		return member_Interface.memberList();
	}

	@Override
	public boolean signupmember(Member_DTO dto) {
		logger.info("회원 가입signupmember{}",dto);
		return member_Interface.signupmember(dto);
	}

	@Override
	public boolean idduplicatecheck(String id) {
		logger.info("아이디 중복검사 idduplicatecheck {}",id);
		return member_Interface.idduplicatecheck(id);
	}

	@Override
	public Member_DTO loginmember(Member_DTO dto) {
		logger.info("로그인 loginmember {}", dto);
		return member_Interface.loginmember(dto);
	}

}
