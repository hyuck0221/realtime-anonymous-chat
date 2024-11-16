package com.hshim.realtimeanonymouschat.config.websocket

import com.hshim.realtimeanonymouschat.database.repository.chat.ChatRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig(
    private val sessionManager: SessionManager,
    private val chatRepository: ChatRepository,
) {
    @Bean
    fun customWebSocketHandler(): CustomWebSocketHandler {
        return CustomWebSocketHandler(sessionManager, chatRepository)
    }
}