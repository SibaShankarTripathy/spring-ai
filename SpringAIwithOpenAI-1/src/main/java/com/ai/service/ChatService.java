package com.ai.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	private ChatClient chatClient;// Common Chat model
	private ChatMemory chatMemory;
	static int count = 1;

	// Constructor Injection of ChatClient
//	public ChatService(ChatClient.Builder builder) {
//		chatClient = builder.build();
//	}

	// This is Constructor Injection and here ChatClient object using inMemory(chat history) to store history of chat.
	public ChatService(ChatClient.Builder builder, ChatMemory chatMemory) {
		this.chatMemory = chatMemory;
		chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
	}

	public String getAnswerFromGpt(String question) {
		String response = "No Data found";
		try {
			ChatResponse chatResponse = chatClient.prompt(question).call().chatResponse();
			response = chatResponse.getResult().getOutput().getContent();
		} catch (Exception e) {
			System.out.println("Exception occurred while getting response from OpenAI API......" + e.getMessage());
		}
		return response;
	}

	// This method will only display response from model.
	// In this method we added ChatOptions
	public String getAnswerUseChatOptionFromGpt(String question) {
		System.out.println("Entered into getAnswerUseChatOptionFromGpt() method");
		String response = "No data found";
		try {
			System.out.println("Client Query::::::" + question);
			// You can set this chat option in properties file as well.
			// Before uncomment bellow codes please comment chat option details in
			// properties file
//				OpenAiChatOptions chatOption = new OpenAiChatOptions();
//				chatOption.setMaxTokens(25);
//				ChatResponse chatResponse = chatClient.prompt(new Prompt(question, chatOption)).call().chatResponse();

			ChatResponse chatResponse = chatClient.prompt(question).call().chatResponse();

			response = chatResponse.getResult().getOutput().getContent();
			System.out.println("Response from OpenAI API::::" + response);
		} catch (Exception e) {
			System.out.println("Exception occurred while getting response from OpenAI....." + e.getMessage());
		}
		System.out.println("Exited getAnswerUseChatOptionFromGpt() method");
		return response;
	}

	// This method will display old and all chat history in console and response from model
	public String getAnswerUseHistoryFromGpt(String question) {
		String response = "No Data found";
		String conversationId = "myUserId" + 1;
		try {
			// Last messages stored in memory
			List<Message> oldMessages = chatMemory.get(conversationId, 10);

			System.out.println("************ CHAT MEMORY BEFORE REQUEST TO LLM *************");

			oldMessages.forEach(msg -> {
				System.out.println(msg.getMessageType() + " : " + msg.getText());
			});

			System.out.println("************ CHAT END ***** COUNT::: " + count + " *************");
			// Send request to LLM
			// Internally MessageChatMemoryAdvisor will append old chats(both request and response) with current question
			response = chatClient.prompt().user(question)
					.advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
					.call().content();

			List<Message> messages = chatMemory.get(conversationId, 10);
			// Here 10 is it will store last 9 messages in memory.
			System.out.println("====================== ALL CHAT MEMORY =====================");

			messages.forEach(msg -> {
				System.out.println(msg.getMessageType() + " : " + msg.getText());
			});

			System.out.println("=============== END ======= COUNT::: " + count + " =============");
		} catch (Exception e) {
			System.out.println("Exception occured while getting response from Ollama Gemma:2b....." + e.getMessage());
		}
		count++;
		return response;
	}

}
