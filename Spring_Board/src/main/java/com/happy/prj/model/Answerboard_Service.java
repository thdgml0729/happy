package com.happy.prj.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.prj.dtos.Answerboard_DTO;
import com.happy.prj.dtos.RowNum_DTO;

@Service
public class Answerboard_Service implements Answerboard_IService {

	private Logger logger = LoggerFactory.getLogger(Answerboard_Service.class);
	@Autowired
	private Answerboard_Interface answerBoard_Interface;
	
	@Override
	public boolean writeBoard(Answerboard_DTO dto) {
		logger.info("새글작성 writeBoard {} ",dto);
		return answerBoard_Interface.writeBoard(dto);
	}

	@Override
	public boolean reply(Answerboard_DTO dto) {
		 logger.info("답글 reply {}",dto);
		 boolean isc = answerBoard_Interface.replyBoardUp(dto);
		 isc = answerBoard_Interface.replyBoardIn(dto);
		 logger.info("답글 reply {}", dto);
		 // update의 루트답글에 답글이 없을때 
		return isc;
	}

	@Override
	public Answerboard_DTO getOneBoard(String seq) {
		 logger.info("하나조회 getOneBoard {} ",seq);
		return answerBoard_Interface.getOneBoard(seq);
	}

	@Override
	public boolean readcountBoard(String seq) {
		logger.info("조회수 증가 readcountBoard {} ",seq);
		return answerBoard_Interface.readcountBoard(seq);
	}

	@Override
	public boolean modifyBoard(Answerboard_DTO dto) {
		logger.info("글수정 modifyBoard {} ",dto);
		return answerBoard_Interface.modifyBoard(dto);
	}

	@Override
	public boolean delflagBoard(Map<String, String[]> map) {
		 logger.info("delflag 변경 delflagBoard {} ", map);
		return answerBoard_Interface.delflagBoard(map);
	}

	@Override
	public List<Answerboard_DTO> deleteBoardSel(String seq) {
		 logger.info("하위 삭제글 확인 deleteBoardSel {} ",seq);
		return answerBoard_Interface.deleteBoardSel(seq);
	}

	@Override
	public boolean deleteBoard(Map<String, String[]> map) {
		 logger.info("진짜 삭제 deleteBoard {} ",map);
		return answerBoard_Interface.deleteBoard(map);
	}

	@Override
	public List<Answerboard_DTO> userBoardList() {
		 logger.info("전체글 조회 사용자 userBoardList ");
		return answerBoard_Interface.userBoardList();
	}

	@Override
	public List<Answerboard_DTO> adminBoardList() {
		 logger.info("전체글 조회 관리자 adminBoardList ");
		return answerBoard_Interface.adminBoardList();
	}

	@Override
	public List<Answerboard_DTO> userboardListRow(RowNum_DTO dto) {
		 logger.info("전체글 조회 사용자(페이징) userboardListRow {} ",dto);
		return answerBoard_Interface.userboardListRow(dto);
	}

	@Override
	public List<Answerboard_DTO> adminboardListRow(RowNum_DTO dto) {
		 logger.info("전체글 조회 관리자(페이징) adminboardListRow {} ",dto);
		return answerBoard_Interface.adminboardListRow(dto);
	}

	@Override
	public int userboardlisttotal() {
		 logger.info("전체글 숫자 사용자 userboardlisttotal");
		return answerBoard_Interface.userboardlisttotal();
	}

	@Override
	public int adminboardlisttotal() {
		 logger.info("전체글 숫자 관리자 adminboardlisttotal");
		return answerBoard_Interface.adminboardlisttotal();
	}

}
