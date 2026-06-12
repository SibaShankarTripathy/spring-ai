package com.ai.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//This configuration class is only available for maintain your chat history.
// InMemoryChatMemory() is a older method latest method you can use MessageWindowChatMemory.builder().build()
@Configuration
public class ChatHistoryConfig {
	
	@Bean
	ChatMemory chatMemory() {
		return new InMemoryChatMemory();
	}

}
