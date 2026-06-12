package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.MCPServerClient;

@RestController
public class MCPClientController {
	
	@Autowired
	private MCPServerClient mcpServerClient;
	
	//http://localhost:8087/getAnsFromMCPServer?question=First let me know today's date and time with day and greet Shankar
	@GetMapping("/getAnsFromMCPServer")
	public String getAnsFromMCPServer(@RequestParam String question) {
		return mcpServerClient.getResponseFromMCPServer(question);
	}

}
