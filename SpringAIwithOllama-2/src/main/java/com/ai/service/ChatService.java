package com.ai.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.Message;

@Service
public class ChatService {

	private ChatClient chatClient;// Common for all Chat model
	private ChatMemory chatMemory;
	
	public EmbeddingModel embeddingModel;
	static int count = 1;

	// This is Constructor Injection and here ChatClient object without using inMemory(chat history)
//	public ChatService(ChatClient.Builder builder) {
//		chatClient = builder.build();
//	}

	// This is Constructor Injection and here ChatClient object using inMemory(chat history) to store history of chat
	// Important is defaultAdvisors() and MessageChatMemoryAdvisor for maintain chat history.
	// When we use MessageChatMemoryAdvisor then in every request will include previous chat details as well. 
	public ChatService(ChatClient.Builder builder, ChatMemory chatMemory) {
		this.chatMemory = chatMemory;
		chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
	}

	// This method will only display response from model. 
	// This method is simple and same for with and without chat history details
	public String getAnswerFromOllama(String question) {
		String response = "No data found";
		try {
			System.out.println("Client Query::::::"+question);
			ChatResponse chatResponse = chatClient.prompt(question).call().chatResponse();
			response = chatResponse.getResult().getOutput().getContent();
			System.out.println("Response from Ollama-Gemma:2b model::::" + response);
		} catch (Exception e) {
			System.out.println("Exception occurred while getting response from ollama gemma:2b....." + e.getMessage());
		}
		return response;
	}
	
	// ChatOption is nothing but it is a one type of model configuration.
	// This method will only display response from model.
	// In this method we added ChatOptions object to set configuration
	public String getAnswerUseChatOptionFromOllama(String question) {
		System.out.println("Entered into getAnswerUseChatOptionFromOllama() method");
		String response = "No data found";
		try {
			System.out.println("Client Query::::::"+question);
			// You can set this chat option in properties file as well.
			// Before uncomment bellow codes please comment chat option details in properties file
//			OllamaOptions chatOption = new OllamaOptions();
//			chatOption.setMaxTokens(25);
//			chatOption.setModel("gemma:2b");
//			chatOption.setTemperature(0.7);
//			ChatResponse chatResponse = chatClient.prompt(new Prompt(question, chatOption)).call().chatResponse();
		
			ChatResponse chatResponse = chatClient.prompt(question).call().chatResponse();
			
			response = chatResponse.getResult().getOutput().getContent();
			System.out.println("Response from Ollama-gemma:2b model::::" + response);
		} catch (Exception e) {
			System.out.println("Exception occurred while getting response from ollama gemma:2b....." + e.getMessage());
		}
		System.out.println("Exited getAnswerUseChatOptionFromOllama() method");
		return response;
	}
	
	// This method will display old and all chat history in console and response from model
	public String getAnswerUseHistoryFromOllama(String question) {
		String response = "No Data found";
		String conversationId = "myUserId"+1;
		try {
			// Last messages stored in memory
			List<Message> oldMessages = chatMemory.get(conversationId, 10);

			System.out.println("************ CHAT MEMORY BEFORE REQUEST TO LLM *************");

			oldMessages.forEach(msg -> {
				System.out.println(msg.getMessageType() + " : " + msg.getText());
			});

			System.out.println("************ CHAT END ***** COUNT::: "+count+" *************");
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

			System.out.println("=============== END ======= COUNT::: "+count+" =============");
		} catch (Exception e) {
			System.out.println("Exception occured while getting response from Ollama Gemma:2b....." + e.getMessage());
		}
		count ++;
		return response;
	}

}
// Examples of chat to display memory is available
// http://localhost:8085/getChatResponse?questionDetails=I want to learn swimming give me basic instruction
// http://localhost:8085/getChatResponse?questionDetails=Make summary of all points in one paragraph
// http://localhost:8085/getChatResponse?questionDetails=Make summary of few important points





// Alternate of this by using Builder method
//OllamaOptions chatOption = new OllamaOptions();
//chatOption.setMaxTokens(25);
//chatOption.setModel("gemma:2b");
//chatOption.setTemperature(0.7);

// To this using Builder Method
//OllamaOptions chatOption = OllamaOptions.builder()
//.withModel("gemma:2b")
//.withTemperature(0.7)
//.build();
