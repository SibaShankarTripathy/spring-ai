package com.ai.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Service;

@Service
public class MCPServerService {
	// This is also called Tool Server
	
	@McpTool(description = "This method is displaying the current date and time")
	public String getCurrentDateAndTime() {
		String dateTime = "";
		String tempDateTime = LocalDateTime.now().toString();
		System.out.println("Current Date and Time::::"+tempDateTime);
		System.out.println("Today's Date is::::"+tempDateTime.substring(0,10)+" and Current time is::::"+tempDateTime.substring(11,16));
		dateTime = "Date: ".concat(tempDateTime.substring(0,10)).concat(" Time: ").concat(tempDateTime.substring(11,16));
		return dateTime;
	}
	
	
	@McpTool(description = "This method is return wish message based on local date and time")
	public String getWishMessage(String user) {
		int hr = LocalDateTime.now().getHour();
		String message = "Good night dear "+user;
		if(hr<12)
			message = "Good morning dear "+user;
		if(hr>12 && hr<16)
			message = "Good afternoon dear "+user;
		if(hr>16 && hr<21)
			message = "Good evening dear "+user;
		System.out.println("Wish message::::"+message);
		return message;
	}
	
	@McpTool(description = "This method is return today's name")
	public String getTodaysDay() {
		DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
		String day = dayOfWeek.toString();
		System.out.println("Today is :::: "+day);
		return day;
	}
	
	public String getDummyMessageFromMCPServer(String name) {
		return "Hi "+name+" how are you";
	}
}

/*
 * Brief about MCP Server
 * MCP Server - Model Context Protocol Server
 * For this no need of AI model so in dependency there is no AI model dependency
 * This is a regular Spring Boot project and in this project only service class required to make it MCP server.
 * But you can also keep rest controller class for other API design as per require. 
 * In this service class contains only tool as several custom methods based on requirement. You can add prompt and resource as well with tool if need. 
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
