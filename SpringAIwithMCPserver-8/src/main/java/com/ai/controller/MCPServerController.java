package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.MCPServerService;

@RestController
public class MCPServerController {
	
	@Autowired
	private MCPServerService mcpServerService;
	
	// http://localhost:8085/getDummyMessageFromMCPServer?name=Shankar
	@GetMapping("/getDummyMessageFromMCPServer")
	public String getDummyMessageFromMCPServer(@RequestParam String name) {
		return mcpServerService.getDummyMessageFromMCPServer(name);
	}
}
