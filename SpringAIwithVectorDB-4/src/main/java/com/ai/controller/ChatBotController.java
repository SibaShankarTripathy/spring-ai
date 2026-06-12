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
//	http://localhost:8085/getVectorResponse?question=Data Science
	@GetMapping("/getVectorResponse")
	public String getVectorResponse(@RequestParam String question) {
		String chatBotResponse = chatBotService.getVectorResponse(question);
		System.out.println("This is chatbot controller class askAnything().....");
		return chatBotResponse;
	}
	
	// Run it once to store the details and create chunks in DB
	// http://localhost:8085/createDBChunks
	@GetMapping("/createDBChunks")
	public String createDBChunks() {
		System.out.println("This is chatbot controller class createDBChunks().....");
		String response = documentsDetails.saveDocument();
		return response;
	}
}
