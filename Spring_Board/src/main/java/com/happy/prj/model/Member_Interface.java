package com.happy.prj.model;

import java.util.List;

import com.happy.prj.dtos.Member_DTO;

public interface Member_Interface {
	
	//A. interface는 private, 
		public List<Member_DTO> memberList();
		
		//signupmember : 회원 가입
		public boolean signupmember(Member_DTO dto);
		
		//idduplicatecheck : 아이디 중복 체크
		public boolean idduplicatecheck(String id);
		
		//loginmember : 로그임
		public Member_DTO loginmember(Member_DTO dto);

}
