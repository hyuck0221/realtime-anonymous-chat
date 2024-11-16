package com.hshim.realtimeanonymouschat.config.websocket

import com.hshim.realtimeanonymouschat.feature.chat.ChatEventModel
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class CustomWebSocketHandler(
    private val sessionManager: SessionManager
) : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val groupId = session.attributes["groupId"] as String
        sessionManager.addSession(groupId, session)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val groupId = session.attributes["groupId"] as String
        sessionManager.send(groupId, ChatEventModel.ChatInfo(session.id, message.payload))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val groupId = session.attributes["groupId"] as String
        sessionManager.removeSession(groupId, session)
    }
}