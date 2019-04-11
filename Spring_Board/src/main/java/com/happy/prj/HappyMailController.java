package com.happy.prj;

import java.io.UnsupportedEncodingException;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.nurigo.java_sdk.Coolsms;

@Controller
public class HappyMailController {
	// dto가 있으면 여러사람에게 보내는 것이 아능하다 
	
	public Logger logger = LoggerFactory.getLogger(HappyMailController.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping(value="/mail.do",method=RequestMethod.GET)
	public String mail() {
		logger.info("MailController mail");
		return "mailForm";
	}
	
	@RequestMapping(value="/mailSending.do", method=RequestMethod.POST)
	public String mailSending(String toMail, String title, String content) { // httpservlet안해도 된다. 알아서 된다. 
		String setFrom = "info.happy0612@gmail.com"; //보내는 사람
		//toMail : 받는사람 메일
		//title : 메일 제목 
		//content :메일 내용 
//		String subject = "Happy coperation";
		System.out.println(toMail+":"+title+":"+content);
		try {
			MimeMessage message = mailSender.createMimeMessage();// dto와 동일한 형태
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8"); // 메일을 보내는 객체를만들어야 한다. 
			// Spring 객체는 아니다. Spring은 보내는 애만 만든다. 
			messageHelper.setFrom(setFrom); // 보내는 사람 정보입력, 생략하거나 입력하지 않으면 작동되지 않음 
			messageHelper.setTo(toMail); //받는사람 주소
			messageHelper.setSubject(title); // 제목 생략해도 됨
			messageHelper.setText(content, true); // 메일 전속 내용 // setText(content, "UTF-8","html");
//			messageHelper.setText("<a href='#'>이메일인증</a>", true);
			
			DataSource dataSource = new FileDataSource("C:\\Happy\\이미지\\1513127807083.jpg");
			messageHelper.addAttachment(MimeUtility.encodeText("보노.jpg","UTF-8","B"), dataSource);
			
			//setCC(String), setCC(String[]) : 참조메일 주소
			//setBCC(String), setBCC(String[]) : 숨은 참조메일 주소
			//setSendDate(Date) : 예약메일
			
			mailSender.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "reirect:/memberList.do";
	}
	
	/*
	@ResponseBody
	@RequestMapping(value = "/sendSMS.do", method = RequestMethod.POST)
	public String sendSMS(String userPhoneNumber) throws Exception { // 휴대폰 문자보내기

		String api_key = "NCSNRUD6LZXEYC5J";
		String api_secret = "LWGVMHKOX8VEFKAPTUDAWV8NG6F1Z2Y8";
//		coolsms coolsms = new Coolsms(api_key, api_secret); // 메시지보내기 객체 생성
		Coolsms coolsms = new Coolsms(api_key, api_secret); 
//		String key = new TempKey().getNumKey(6); // 인증키 생성
//		userService.insertAuthCode(userPhoneNumber, key); // 휴대폰 인증 관련 서비스
		/*
		 * Parameters 관련정보 : http://www.coolsms.co.kr/SDK_Java_API_Reference_ko#toc-0
		 */
	/*
		HashMap<String, String> set = new HashMap<String, String>();
		set.put("to", "01065491058"); // 수신번호
		set.put("from", "01094096075"); // 발신번호
		set.put("text", "안녕하세요 정송희입니다. 문자전송테스트 중입니다. "); // 문자내용
		set.put("type", "sms"); // 문자 타입

		JSONObject result = coolsms.
		if ((boolean) result.get("status") == true) {
			// 메시지 보내기 성공 및 전송결과 출력
			System.out.println("성공");
			System.out.println(result.get("group_id")); // 그룹아이디
			System.out.println(result.get("result_code")); // 결과코드
			System.out.println(result.get("result_message")); // 결과 메시지
			System.out.println(result.get("success_count")); // 메시지아이디
			System.out.println(result.get("error_count")); // 여러개 보낼시 오류난 메시지 수
			return "success";
		} else {
			// 메시지 보내기 실패
			System.out.println("실패");
			System.out.println(result.get("code")); // REST API 에러코드
			System.out.println(result.get("message")); // 에러메시지
			return "fail";
		}
	}*/
}
