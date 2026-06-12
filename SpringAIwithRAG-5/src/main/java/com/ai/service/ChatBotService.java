package com.ai.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {
	
	public ChatClient chatClient;
	
	public ChatBotService(ChatClient.Builder builder) {
		chatClient = builder.build();
	}
	
	@Autowired
	private VectorStore vectorStore;
	
	private static final Logger logger = LoggerFactory.getLogger(ChatBotService.class);

	public String getRAGResponse(String question) {
		String matchedResponse = "No matches sentences found";
		String aiContent = "No AI content found";
		try {
			logger.info("Client Questions:::::" + question);
			// Raw response from vectorStore.
			// You can use this below line to get top 3 response from DB
//			List<Document> tempListResponse = vectorStore.similaritySearch(SearchRequest.builder().topK(3).query(question).build());
//			List<Document> tempListResponse = vectorStore.similaritySearch(question);
			
			// This below method will execute similaritySearch() internally then embedding and then make a text and return.
			SearchRequest searchRequest = SearchRequest.builder().build();
			QuestionAnswerAdvisor qusAnsAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
												.searchRequest(searchRequest)
												.build();
			// Now we push our QuestionAnswerAdvisor response to AI model to get perfect output
			aiContent = chatClient.prompt(question).advisors(qusAnsAdvisor).call().content();
		} catch (Exception e) {
			logger.info("Exception occurred while getting response from Vector DB.......");
		}
		logger.info("RAG content::::::::{}",aiContent);
		
		return aiContent;
	}

}
/*
 * In this POC we are storing the required documents in Vector DB(Qdrant DB) in the form of chunks.
 * In this service class we are collecting possibility response data from Vector DB based on client question.
 * Those details are contain only stored data. So we are passing those stored data to an AI model to make perfect. 
 * This process/concept is called RAG.
 * You can understand in another way like when we ask something to AI it is giving pre store data in AI model, 
 * but when we need AI response include current latest details then we use RAG concept.
 */
