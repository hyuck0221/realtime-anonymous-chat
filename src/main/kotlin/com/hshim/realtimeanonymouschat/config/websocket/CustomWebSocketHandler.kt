package com.hshim.realtimeanonymouschat.config.websocket

import com.hshim.realtimeanonymouschat.database.entity.chat.Chat
import com.hshim.realtimeanonymouschat.database.repository.chat.ChatRepository
import com.hshim.realtimeanonymouschat.feature.chat.ChatEventModel
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class CustomWebSocketHandler(
    private val sessionManager: SessionManager,
    private val chatRepository: ChatRepository,
) : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val groupId = session.attributes["groupId"] as String
        sessionManager.addSession(groupId, session)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val groupId = session.attributes["groupId"] as String
        val content = message.payload
        val chat = Chat(
            sessionId = session.id,
            content = content,
            groupId = groupId,
        )
        chatRepository.save(chat)
        sessionManager.send(groupId, ChatEventModel.ChatInfo(session.id, content))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val groupId = session.attributes["groupId"] as String
        sessionManager.removeSession(groupId, session)
    }
}