package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.ChatService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;

	/* 
	 * This below method is responsible to take your query/question and hit to OpenAI API.
	 * After that get response from OpenAI API.
	 * There is no need of Login details while communicate with OpenAI because we generated an API KEY from OpenAI and that key contains all details.
	 * To communicate with OpenAI API we need to pay amount then only API KEY will activate.
	 */
	
	// http://localhost:8085/getChatResponse
	@GetMapping("/getChatResponse")
	public String getChatResponse(@RequestParam String questionDetails) {
//		return chatService.getAnswerFromGpt(questionDetails);
//		return chatService.getAnswerUseChatOptionFromGpt(questionDetails);
		return chatService.getAnswerUseHistoryFromGpt(questionDetails);
	}
}
