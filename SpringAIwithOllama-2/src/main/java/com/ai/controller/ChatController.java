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
	 * This below method is responsible to take your query/question and hit to Ollama gemma:2b model API.
	 * There is no need of Login details while communicate because in local instance we run the model and use with help of Ollama.
	 * In this project I use gemma:2b model.
	 */

	// http://localhost:8085/getChatResponse
	// http://localhost:8085/getChatResponse?questionDetails="How can I use AI"
	@GetMapping("/getChatResponse")
	public String getChatResponse(@RequestParam String questionDetails) {
//		return chatService.getAnswerFromOllama(questionDetails);
//		return chatService.getAnswerUseChatOptionFromOllama(questionDetails);
		return chatService.getAnswerUseHistoryFromOllama(questionDetails);
	}
}
