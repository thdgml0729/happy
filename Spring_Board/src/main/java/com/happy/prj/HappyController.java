package com.happy.prj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happy.prj.dtos.Answerboard_DTO;
import com.happy.prj.dtos.Member_DTO;
import com.happy.prj.dtos.RowNum_DTO;
import com.happy.prj.model.Answerboard_IService;
import com.happy.prj.model.Member_IService;

@Controller
public class HappyController {
	
	Logger logger = LoggerFactory.getLogger(HappyController.class);
	
	@Autowired
	private Member_IService iMember; 
	
	@Autowired
	private Answerboard_IService iAnswerboard;
	
	@RequestMapping(value="/intro.do", method=RequestMethod.GET)
	public String intro() {
		logger.info("처음 시작하는 Spring Intro Page");
		return "intro";
	}
	
	//loginForm.do 처음 로그인 화면 
	@RequestMapping(value="/loginForm.do",method=RequestMethod.GET)
	public String loginForm() {
		logger.info("Controller loginForm");
		return "loginForm";
	}
	
	//loginCheck.do, 로그인 정보가 있는지 확인 
	//ajax를 하기떄문에 화면이동을 못한다.
	@RequestMapping(value="/loginCheck.do",method=RequestMethod.POST,
			produces="application/text; charset=UTF-8") // application을 text로 처리하고, charset=UTF-8로 설정
	//produces를 처리하지 않으면 "성공"이라는 글자가 뜨지 않고, ??가 뜹니다. 
	@ResponseBody
	public String loginCheck(Member_DTO dto) {
		logger.info("요청받은 값 : {} {}",dto.getId(), dto.getPw());
		Member_DTO ldto = iMember.loginmember(dto);
		logger.info("Welcome loginCheck");
		return (ldto==null)?"실패":"성공/"+ldto.getAuth();
	}
	
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public String login(HttpSession session, Member_DTO dto) {
		Member_DTO ldto = iMember.loginmember(dto);
		logger.info("Controller login {}",ldto);
		session.setAttribute("mem", ldto);
		
		return "redirect:/boardList.do";// Request method 'POST' not supported 서버사이드에서 post값 처리 못한다. 
	}
	
	@RequestMapping(value="/signUpForm.do",method=RequestMethod.GET)
	public String signUpForm() {
		logger.info("Controller signUpForm");
		return "signUpForm";
	}
	
	@RequestMapping(value="/signUp.do", method=RequestMethod.POST)// 유효값처리와 같은 값 처리는 화면에서, 
	public String signUp(Member_DTO dto) {
		boolean isc = iMember.signupmember(dto);
		logger.info("Controller signUp {}", new Date()); // 시간 찍어주기 위해서 
		String str = "";
		return isc? "redirect:/loginForm.do":"redirect:/signUpForm.do"; // redirect는 response로 보내겠다.
	}
	
	//  "^[a-zA-Z0-9]*$"; // 첫글자는 글자고 글자는 a~z까지 A~Z까지 0~9까지 여러번 반복
	// 자바스크립트 length로 처리하는 것이 더 쉽다. 
	@RequestMapping(value="/idCheck.do", method=RequestMethod.POST, 
			produces="application/text; charset=UTF-8")
	@ResponseBody //ajax처리
	public String idCheck(String id) {
		String regex  = "^[a-zA-Z0-9]*$"; // 정규화 표현식
		boolean isc = id.matches(regex);
		
		boolean isc2 = iMember.idduplicatecheck(id); // 중복이면 true 
		System.out.println(isc2);
		
		logger.info("Controller signUp {} // {}",isc, new Date());
		return isc2?"사용가능한 아이디 입니다.":"중복된 아이디 입니다.";
	}
	//logOut.do
	@RequestMapping(value="/logOut.do",method=RequestMethod.GET)
	public String logOut(HttpSession session) {
		Member_DTO dto = (Member_DTO)session.getAttribute("mem");
		if(dto!=null) {
			session.removeAttribute("mem");
			session.removeAttribute("row");
			//session.invalidate();다지워진다.
		}
		return "redirect:/loginForm.do";
	}
	
	@RequestMapping(value="/memberList.do",method=RequestMethod.GET)
	public String memberList(Model model)	{
		List<Member_DTO> lists = iMember.memberList();
		logger.info("Controller memberList {} ", lists);
		model.addAttribute("mlists",lists);
		return "memberList";
	}
	
	//--------------------------------------------------------------------------
	@RequestMapping(value="/boardWriteForm.do",method=RequestMethod.GET)
	public String boardWriteForm() {
		logger.info("Controller boardWriteForm");	
		return "boardWriteForm";
	}
	
	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public String boardWrite(Answerboard_DTO dto) {
		logger.info("Controller boardWrite {} ",dto);
		boolean isc = iAnswerboard.writeBoard(dto);
		return isc? "redirect:/boardList.do":"boardWriteForm";
		// return "boardList"; <-이렇게 작성하게 되면 값을 가지고 가지 않는다. 
		//boardWriteForm의 경우에는 값을 가지고 가지 않고, 페이지만 이동하기때문에 "boardWriteForm"작성해도 된다. 
	}
	
	@RequestMapping(value="/multidel.do",method=RequestMethod.POST)
	public String multidel(String[] chkVal, Model model) { // seqs가 아니라 chkVal!!!!
		logger.info("Cotroller multidel {} ", Arrays.toString(chkVal));
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("seq_", chkVal);
		boolean isc = iAnswerboard.delflagBoard(map);
//		return isc?"redirect:/boardList.do":;
		if(isc) {
			return "redirect:/boardList.do";
		}else {
			//errMsg, url
			model.addAttribute("errMsg", "잘못된 접근입니다.\n 관리자에 문의 하세요");
			model.addAttribute("url","./loginForm.do");
			return "error_req";
		}

	}
	
	
	//boardList.do
	@RequestMapping(value="/boardList.do",method=RequestMethod.GET)
	public String boardList(HttpSession session, Model model) {
		logger.info("Controller boardList {} ",(Member_DTO)session.getAttribute("mem"));
		Member_DTO dto = (Member_DTO)session.getAttribute("mem");
		RowNum_DTO rowDto = null;
		List<Answerboard_DTO> lists = null;
		
		//session에 페이지에 관련된 정보를 담는다. "row"
		if(session.getAttribute("row")==null) {// ajax때문에 담아놓는것
			rowDto = new RowNum_DTO();
		}else {
			rowDto = (RowNum_DTO)session.getAttribute("row");
		}
		// 여기까지 페이지정보
		
		if(dto.getAuth().trim().equals("U")) {
			rowDto.setTotal(iAnswerboard.userboardlisttotal());
			
			lists = iAnswerboard.userboardListRow(rowDto);
		}else {
			rowDto.setTotal(iAnswerboard.adminboardlisttotal());
			lists = iAnswerboard.adminboardListRow(rowDto);
		}
		model.addAttribute("lists", lists);
		model.addAttribute("row",rowDto);
		
		return "boardList"; // 다음으로 이동될 
	}
	
	@RequestMapping(value="/paging.do",method=RequestMethod.POST, 
			produces="application/text; charset=UTF-8")
	@ResponseBody // ajax로 처리한 값을 페이지에서 필요할때
	public String paging(Model model, HttpSession session, RowNum_DTO rowDto) {
		Member_DTO dto = (Member_DTO)session.getAttribute("mem");
		logger.info("Controller paging {} ",(Member_DTO)session.getAttribute("mem"));
		JSONObject json = null; // 밑의 objectJson에서 만들어서 던져줄것이라 new 안함
		
		if(dto.getAuth().trim().equalsIgnoreCase("U")) { // compare로 비교할때는 ''
			rowDto.setTotal(iAnswerboard.userboardlisttotal());
			
			//게시판 글을 조회 객체 -> JSON으로 담음
			json = objectJson(iAnswerboard.userboardListRow(rowDto), rowDto, dto);
		}else {
			rowDto.setTotal(iAnswerboard.adminboardlisttotal());
			
			//게시판 글을 조회 객체 -> JSON으로 담음
			json = objectJson(iAnswerboard.adminboardListRow(rowDto), rowDto, dto);
		}
		session.removeAttribute("row");
		session.setAttribute("row", rowDto);
		logger.info("Controller boardList {} ",json.toString());
		return json.toString();
	}
	
	@SuppressWarnings("unchecked") // 경고로 인식하지않음(jLists.add(jList);)
	private JSONObject objectJson(List<Answerboard_DTO> lists, RowNum_DTO row, Member_DTO mem) { // session정보 가져옴
		JSONObject json = new JSONObject();
		JSONArray jLists = new JSONArray();// JSON안에 list만들어야 하니 Array필요
		JSONObject jList = null;
		
		//게시글 관련
		for(Answerboard_DTO dto : lists) {
			jList = new JSONObject();
			jList.put("seq",dto.getSeq());    
			jList.put("id",dto.getId()); 
			jList.put("title",dto.getTitle()); 
			jList.put("content",dto.getContent());     
			jList.put("readcount",dto.getReadcount());    
			jList.put("regdate",dto.getRegdate()); 
			jList.put("delflag",dto.getDelflag()); 
			jList.put("memid", mem.getId());
			
			jLists.add(jList);
		}
		
		//페이징에 관련되
		jList = new JSONObject();
		jList.put("pageList", row.getPageList());
		jList.put("index", row.getIndex());
		jList.put("pageNum", row.getPageNum());
		jList.put("listNum", row.getListNum());
		jList.put("total", row.getTotal());
		jList.put("count", row.getCount());
		
		json.put("lists", jLists);
		json.put("row", jList);

		
		return json;
		
	}
	
	//글수정하기 modifyForm.do
	@RequestMapping(value="/modifyForm.do",method=RequestMethod.POST,
			produces="application/text; charset=UTF-8")
	@ResponseBody//아작스일떄 써줌
	public String modifyForm(String seq) {
		JSONObject json = new JSONObject();
		Answerboard_DTO dto = iAnswerboard.getOneBoard(seq);
		System.out.println(dto);
		json.put("seq", dto.getSeq());
		json.put("id", dto.getId());
		json.put("title", dto.getTitle());
		json.put("content", dto.getContent());
		json.put("readcount", dto.getReadcount());
		json.put("regdate", dto.getRegdate());
		logger.info("Controller modifyForm {} ", json.toString());
		
		return json.toString();
	}
	
	@RequestMapping(value="/modify.do",method=RequestMethod.POST)
	public String modify(Answerboard_DTO dto) {
		logger.info("Controller modify {} ",dto);
		boolean isc = iAnswerboard.modifyBoard(dto);
		return "redirect:/boardList.do";
	}
	
	@RequestMapping(value="/del.do",method=RequestMethod.GET)
	public String del(String[] seq, HttpSession session) {
		Member_DTO mdto = (Member_DTO)session.getAttribute("mem");
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("seq_",seq);
		
		if(mdto.getAuth().trim().equalsIgnoreCase("U")) { // 사용자
			iAnswerboard.delflagBoard(map);
		}else { // 관리자
			iAnswerboard.deleteBoard(map);
		}
		
		return "redirect:/boardList.do";
	}
	
	@RequestMapping(value="/replyForm.do", method=RequestMethod.POST,
			produces="application/text; charset=UTF-8")
	@ResponseBody
	public String replyForm(HttpSession session, String seq) {
		JSONObject json = new JSONObject();
		Answerboard_DTO dto = iAnswerboard.getOneBoard(seq);
		Member_DTO mdto = (Member_DTO)session.getAttribute("mem");
		json.put("seq", dto.getSeq());
		json.put("id", dto.getId());
		json.put("title", dto.getTitle());
		json.put("content", dto.getContent());
		json.put("readcount", dto.getReadcount());
		json.put("regdate", dto.getRegdate());
		json.put("memid", mdto.getId());
		logger.info("Contrller replyForm{} ",json.toString());
		
		return json.toString();
	}
	
	//String으로 보낼때는 텍스트로 인식시키기 위해서 produces를 해줬던것
	// But 여기서는 객체로 보내서 거기서 풀어서 사용하는 것이니 더 간단
	//jacksonmapper가 map으로 던진것을 알아서 json객체로 만들어준다. 
	@RequestMapping(value="/replyForm2.do",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Answerboard_DTO> replyForm2(String seq){
		Map<String, Answerboard_DTO> map = new HashMap<String, Answerboard_DTO>();
		Answerboard_DTO dto = iAnswerboard.getOneBoard(seq);
		map.put("dto", dto);
//		map.put("isc", true);
		return map;
	}
	
	@RequestMapping(value="/reply.do",method=RequestMethod.POST)
	public String reply(HttpSession sesssion, Answerboard_DTO dto) {
		logger.info("Controller reply {} ",dto);
		Member_DTO mdto = (Member_DTO)sesssion.getAttribute("mem");
		dto.setId(mdto.getId());
		boolean isc = iAnswerboard.reply(dto);
		logger.info("Controller reply {} ",isc);
		return "redirect:/boardList.do";
	}
	
	@RequestMapping(value="/captchankey.do",method=RequestMethod.GET)
	public String CaptChaNKey() {
		 String clientId = "YOUR_CLIENT_ID";//애플리케이션 클라이언트 아이디값";
	        String clientSecret = "YOUR_CLIENT_SECRET";//애플리케이션 클라이언트 시크릿값";
	        try {
	            String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
	            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            System.out.println(response.toString());
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	        return null;
	}
	
}
