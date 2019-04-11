package com.happy.prj.model;

import java.util.List;

import com.happy.prj.dtos.Member_DTO;

public interface Member_IService {
	
	//memberList : 전체 회원 조회
//	void memberList(); - 
	//Q. interface에서 접근제한자없이 만들게되면 어떻게되지? 자동으로 public이 붙는다. 
	//A. interface는 private, protected(상속전제를 할 때 사용하는데 interface는 애초에 상속이 전제되어있어서)는 사용할 수없다.
	public List<Member_DTO> memberList();
	
	//signupmember : 회원 가입
	public boolean signupmember(Member_DTO dto);
	
	//idduplicatecheck : 아이디 중복 체크
	public boolean idduplicatecheck(String id);
	
	//loginmember : 로그임
	public Member_DTO loginmember(Member_DTO dto);

}
