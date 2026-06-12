package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class StreamingChatService {

	private ChatClient chatClient;
	
	public StreamingChatService(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}
	
	public Flux<String> getStreamChatResponse(String question) {
		Flux<String> streamResponse = null;
		try {
			System.out.println("Streaming process started");
			streamResponse = chatClient.prompt(question).stream().content();
			// To proof the data is coming as stream or not from ollama
//			streamResponse = chatClient.prompt(question).stream().content().doOnNext(chunk ->
//			System.out.println("Chunk => " + chunk));
			System.out.println("Streaming process ended");
		} catch (Exception e) {
			System.out.println("Exception occurred while getting response from AI......" + e.getMessage());
		}
		return streamResponse;
	}
}

/*
 * This bellow line of codes just for understand and compare with 
 * regular Chat Response and Streaming Chat Response.
 * 
 * public String getChatResponse(String question) {
		String response = "No data found";
		try {
			ChatResponse chatResponse = chatClient.prompt(question).call().chatResponse();
			response = chatResponse.getResult().getOutput().getContent();
		} catch (Exception e) {
			System.out.println("Exception occurred while getting response from AI" + e.getMessage());
		}
		return response;
	}
 * */
