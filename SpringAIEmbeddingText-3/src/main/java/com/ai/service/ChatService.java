package com.ai.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
	private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

	@Autowired
	public EmbeddingModel embeddingModel;

	public float[] getEmbeddingTextResponse(String text) {
		logger.info("Entered in to getEmbeddingTextResponse() method");
		float[] embeddedResponse = null;
		try {
			embeddedResponse = embeddingModel.embed(text);
			logger.info("Embedded Response:::::" + Arrays.toString(embeddedResponse));
		} catch (Exception e) {
			logger.info("Exception occurred while doing embedding process of text....", e.getMessage());
		}
		logger.info("Exited getEmbeddingTextResponse() method");
		return embeddedResponse;
	}

	public double getCompareEmbeddingTextResult(String textOne, String textTwo) {
		logger.info("Entered in to getEmbeddingTextResponse() method");
		List<String> textList = List.of(textOne, textTwo);
		double cosineSimilarityVal = 0.0;
		try {
			List<float[]> embedVectorList = embeddingModel.embed(textList);
			float[] vectorValAryA = embedVectorList.get(0);
			float[] vectorValAryB = embedVectorList.get(1);
			logger.info("Embedded Response of TextOne:::::" + Arrays.toString(vectorValAryA));
			logger.info("Embedded Response of TextTwo:::::" + Arrays.toString(vectorValAryB));
			cosineSimilarityVal = cosineSimilarity(vectorValAryA, vectorValAryB);
			logger.info("Cossigne Similarity Percentage is :::::" + cosineSimilarityVal);
		} catch (Exception e) {
			logger.info("Exception occurred while doing embedding process of text....", e.getMessage());
		}
		logger.info("Exited getEmbeddingTextResponse() method");
		return cosineSimilarityVal;
	}

	// This below logic is responsible to compare two embedded details. 
	// There is no predefine method available for this in JAVA.
	private double cosineSimilarity(float[] vectorValAryA, float[] vectorValAryB) {
		if (vectorValAryA.length != vectorValAryB.length) {
			throw new IllegalArgumentException("Vectors must be of the same length");
		}
		double dotProduct = 0.0;
		double magnitudeA = 0.0;
		double magnitudeB = 0.0;

		for (int i = 0; i < vectorValAryA.length; i++) {
			dotProduct += vectorValAryA[i] * vectorValAryB[i];
			magnitudeA += vectorValAryA[i] * vectorValAryA[i];
			magnitudeB += vectorValAryB[i] * vectorValAryB[i];
		}
		double cosineSimilarityVal = (dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB))) * 100;
		return cosineSimilarityVal;
	}
}
/*
 * Similarity result scale
 * -----------------------

| Value | Meaning              |
| ----- | -------------------- |
| 1.0   | Exact semantic match |
| 0.9   | Very similar         |
| 0.7   | Related              |
| 0.5   | Weak similarity      |
| 0.0   | Completely unrelated |
*/
