package com.mealmaker.babiyo.error;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ErrorController {
	
	Logger log = LoggerFactory.getLogger(ErrorController.class);
	
	@GetMapping(value="/error-{code}")
	public String httpError(@PathVariable int code, HttpServletRequest request) {
		log.info("{} 에러발생", code);
		
		return "error";
	}

}
