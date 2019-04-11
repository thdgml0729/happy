package com.happy.iron;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Spring이 사용할 수 있는 bean으로 자동으로 만들어 준다. 
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET) // 자동적으로 실행
	public String home(Locale locale, Model model) { // request객체만 처리하기때문에 session을 사용하기 위해서는 여기에 써준다. 
		logger.info("Welcome home! The client locale is {}.", locale); // log4j2이다. locale을{}안에 바이딩 ㅐ준다. 
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate ); // Home.jsp에서 화면에 뿌려준다. 
		
		return "home";
	}
	
}
