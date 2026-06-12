package com.ai.tool;

import org.springframework.ai.tool.annotation.Tool;

public class ToolDetails {
	/*
	 * Tool Calling or function calling is a concept.
	 * In a service class there will be several methods or functions.
	 * Based on client query AI will decide to which method should execute is called Tool Calling concept.
	 * AI will choose the method based on description of that method.
	 * For this Tool Calling concept gemma:2b is NOT supporting so I used here qwen3:8b or llama3.1:8b or llama3.2 or mistral-small:latest model with Ollama.
	 * Ex: In CMD type: ollama pull qwen3:8b
	 *
	 */
	
	@Tool(description = "Fetch the current weather condition for given city")
	public String getCityWeather(String city) {
		System.out.println("City Name: "+city);
		return "This is "+city+" and today's temperature is 40 degree....";
	}
	
	@Tool(description = "Provide weather related advice based on given weather condition")
	public String getCityWeatherDescription(String weather) {
		System.out.println("Weather Condition: "+weather);
		if(weather.toLowerCase().contains("hot"))
			return "Do not go out side during 11 AM to 3 PM";
		else if(weather.toLowerCase().contains("cool"))
			return "Wear warm clothes to feel comfort";
		else
			return "Keep umberella with you";
	}

}
