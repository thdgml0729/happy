package com.happy.prj.model;

import java.util.List;
import java.util.Map;

import com.happy.prj.dtos.Answerboard_DTO;
import com.happy.prj.dtos.RowNum_DTO;

public interface Answerboard_IService {
	
	//글입력 
	public boolean writeBoard(Answerboard_DTO dto);
	
	//답글입력(Dao에서 update와 insert를 조합해서 사용
	public boolean reply(Answerboard_DTO dto);
	
	//상세글조회
	public Answerboard_DTO getOneBoard(String seq);
	
	//조회수증가
	public boolean readcountBoard(String seq);
	
	//글 수정
	public boolean modifyBoard(Answerboard_DTO dto);
	
	//글 삭제(삭제로 변경)
	public boolean delflagBoard(Map<String, String[]> map);
	
	//삭제 글 찾기
	public List<Answerboard_DTO> deleteBoardSel(String seq);
	
	//글 삭제 (DB 삭제)
	public boolean deleteBoard(Map<String, String[]> map);
	
	//글조회(전체)	
	public List<Answerboard_DTO> userBoardList();
	
	//글조회(전체-관리자)
	public List<Answerboard_DTO> adminBoardList();
	
	//글조회(전체-페이지-사용자)
	public List<Answerboard_DTO> userboardListRow(RowNum_DTO dto);
	
	//글조회(전체-페이지-관리자)
	public List<Answerboard_DTO> adminboardListRow(RowNum_DTO dto);
	
	//글갯수(전체-사용자)
	public int userboardlisttotal();
	
	//글갯수(전체-관리자)
	public int adminboardlisttotal();
	
	
	

}
