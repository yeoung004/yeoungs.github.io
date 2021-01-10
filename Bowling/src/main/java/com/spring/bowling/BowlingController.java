package com.spring.bowling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BowlingController {
	
	BowlingService bowlingService; 
	
	private static final Logger logger = LoggerFactory.getLogger(BowlingController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Ready to game!");
		
		return "home";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("Welcome to Bowling Game!");
		
		return "index";
	}
	
	@RequestMapping(value = "/score", method = { RequestMethod.POST, RequestMethod.GET })
	
	public @ResponseBody Map<String,Object> score(
			@RequestParam(value="allScore[]") List<String> allScore
			){
		logger.info(allScore.toString());
		for (int i = 0; i < allScore.size(); i++) {
			//System.out.println(allScore.get(i));
		}
		
		Map<String,Object> retVal = new HashMap<String, Object>();
		retVal.put("code", "Ok");
		retVal.put("message", "등록에 성공하였습니다.");
		
		return retVal;
	}
}
