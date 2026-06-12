package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.ToolCallingService;

@RestController
public class ToolCallingController {
	
	@Autowired
	private ToolCallingService toolCallingService;
	
	@GetMapping("/askToAI")
	public String askToAI(@RequestParam String question) {
		String responseFromTool = toolCallingService.askToAI(question);
		return responseFromTool;
	}

}
