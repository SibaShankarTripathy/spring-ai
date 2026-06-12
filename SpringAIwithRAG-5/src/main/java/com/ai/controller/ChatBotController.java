package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.data.DocumentsDetails;
import com.ai.service.ChatBotService;

@RestController
public class ChatBotController {
	
	@Autowired
	ChatBotService chatBotService;
	
	@Autowired
	DocumentsDetails documentsDetails;
//	http://localhost:8085/askAnything?question=Data Science
	@GetMapping("/getRAGResponse")
	public String getRAGResponse(@RequestParam String question) {
		String chatBotResponse = chatBotService.getRAGResponse(question);
		System.out.println("This is ask anything controller class.....");
		return chatBotResponse;
	}
	
	// Run it once to store the details and create chunks in DB
	// http://localhost:8085/createDBChunks
	@GetMapping("/createDBChunks")
	public String createDBChunks() {
		String response = documentsDetails.saveDocument();
		return response;
	}
}
