package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MCPServerClient {
	
	private ChatClient chatClient;
	
	@Autowired
	private ToolCallbackProvider toolCallbackProvider;
	
	MCPServerClient(ChatClient.Builder builder){
		chatClient = builder.build();
	}
	
	public String getResponseFromMCPServer(String question) {
		String response = "No data found from MCP Server....";
		try {
			System.out.println("Client question is::::::"+question);
			response = chatClient.prompt(question).toolCallbacks(toolCallbackProvider).call().content();
			System.out.println("Response from Ollama-llama3.2 model::::" + response);
		} catch (Exception e) {
			System.out.println("Exception occurred while getting response from ollama-llama3.2....." + e.getMessage());
		}
		return response;
	}
	
}

/*
 * Brief about MCP Client
 * MCP Client - Model Context Protocol Client
 * For this we need of AI model so in dependency I added ollama model dependency.
 * This is also regular Spring Boot project which can contains Controller, Service, Repository.
 * The most important thing for MCP client is ToolCallbackProvider object toolCallbacks() method we need. 
 */




















/*
 * Brief about MCP Server
 * MCP Server - Model Context Protocol Server
 * This is a regular Spring Boot project and in this project only service class required to make it MCP server 
 * In it's service class contains several custom methods based on requirement. You can add prompt and resource as well if need. 
 * The only difference is when a method become MCP Tool then on the top of that method @McpTool() annotation should be there.
 * When a API call happen from MCP client then based on DESCRIPTION the AI model will execute the method and sent response.
 * 
 * To test MCP server code please follow below lines of code
 * ---------------------------------------------------------
 * We will test this MCP service tools/methods using Postman.
 * Open postman -> + button -> select MCP.
 * Paste the URL -> select HTTP and press connect button
 * It will show in list how many tools/methods available in service class.
 * Select any of of them and hit run it will give response.
 * 
 * */
