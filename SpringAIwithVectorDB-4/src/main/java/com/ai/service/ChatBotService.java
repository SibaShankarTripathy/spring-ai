package com.ai.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {
	@Autowired
	private VectorStore vectorStore;

	private static final Logger logger = LoggerFactory.getLogger(ChatBotService.class);

	public String getVectorResponse(String question) {
		logger.info("Client Questions:::::" + question);
		// Raw response from vectorStore.
		// You can use this below line to get top 3 response from DB
//		List<Document> tempListResponse = vectorStore.similaritySearch(SearchRequest.builder().topK(3).query(question).build());
		List<Document> tempListResponse = vectorStore.similaritySearch(question);
		logger.info("=================== Raw Response ===================\n");
		logger.info("Raw response from vectorStore:::::\n{}", tempListResponse);
		logger.info("======================== End =======================");

		// This below logic to collect all possible response
		List<String> textListResponse = new ArrayList<>();
		for (Document response : tempListResponse) {
			textListResponse.add(response.getText());
		}
		logger.info("================ All Possible Search ===============");
		logger.info("All possible result from vectorStore:::::\n{}", textListResponse);
		logger.info("======================== End =======================");

		// This below logic to collect only best possible response which match score is high.
		// Collect highest score
		Double maxScore = Double.MIN_VALUE;
		String matchedResponse = "";
		for (Document response : tempListResponse) {
			if(response.getScore() > maxScore) {
				maxScore = response.getScore();
				matchedResponse = response.getText();
			}
		}
		logger.info("================ Best Possible Search ===============");
		logger.info("Best possible matched result from vectorStore:::::\n{}", matchedResponse);
		logger.info("======================== End =======================");

		return matchedResponse;
	}

}
/*
 * In this POC we are storing the required documents in Vector DB(Qdrant DB) in the form of chunks.
 * In this service class we are just collecting possibility response data from Vector DB based on client question.
 * In this service we are not using any AI just collecting the possibility response from DB.
 * When those response(chunks) details we will pass to any AI model and get the output then that process is called RAG.
 * We will learn RAG in next POC.
 * */
