package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.StreamingChatService;

import reactor.core.publisher.Flux;

@RestController
//@CrossOrigin(origins = "*") // Use cross origin if you are using different UI or HTML code outside of this project.
// Here I kept that HTML file in resource -> static and run this project so no need of cross origin.
public class StreamingChatController {
	
	@Autowired
	private StreamingChatService streamingChatService;
	
	// http://localhost:8085/stream.html
	@GetMapping(value = "/getStreamChatResponse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getStreamChatResponse(@RequestParam String question){
		Flux<String> streamChatResponse = streamingChatService.getStreamChatResponse(question);
		return streamChatResponse;
	}
	// To get feel of streaming response handle in UI because in Postman tool and browser 
	// streaming experience will not appear as like ChatGPT response.

}
