package com.spring.bowling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value = "/score", method = RequestMethod.GET)
	public String score(Model model) {
		logger.info("Calculator score");
		
		return "score";
	}
}
