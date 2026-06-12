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
	 * This below method is responsible to take your query/question and hit to
	 * Ollama mxbai-embed-large model which is creating embedding data. 
	 * 
	 * There is no need of Login details while communicate because in local instance we run the model and use with help of
	 * Ollama. 
	 * 
	 * In this project I use mxbai-embed-large model.
	 */
	
	// http://localhost:8085/getEmbeddingTextResponse?text="Text"
	@GetMapping("/getEmbeddingTextResponse")
	public float[] getEmbeddingTextResponse(@RequestParam String text) {
		return chatService.getEmbeddingTextResponse(text);
	}
	
	//http://localhost:8085/getSimilarityEmbeddingTextResult?textA="It is a shiny day"&textB="She is not there"
	@GetMapping("/getSimilarityEmbeddingTextResult")
	public double getCompareEmbeddingTextResult(@RequestParam String textA, @RequestParam String textB) {
		return chatService.getCompareEmbeddingTextResult(textA,textB);
	}
}
