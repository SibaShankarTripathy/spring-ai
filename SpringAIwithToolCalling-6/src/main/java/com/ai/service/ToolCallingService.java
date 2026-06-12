package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.ai.tool.ToolDetails;

@Service
public class ToolCallingService {

	private ChatClient chatClient;

	public ToolCallingService(ChatClient.Builder builder) {
		chatClient = builder.build();
	}
	
	public String askToAI(String question) {
		String content = "No data found";
		try {
			content = chatClient.prompt(question).tools(new ToolDetails()).call().content();
			System.out.println("Weather response from AI :::: "+content);
		} catch (Exception e) {
			System.out.println("Exception occurred while retriving data from tool calling process"+e.getMessage());
		}
		return content;
	}

}
