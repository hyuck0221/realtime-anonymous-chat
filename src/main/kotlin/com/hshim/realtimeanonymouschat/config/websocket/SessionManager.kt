package com.hshim.realtimeanonymouschat.config.websocket

import com.hshim.realtimeanonymouschat.database.repository.chat.ChatRepository
import com.hshim.realtimeanonymouschat.feature.chat.BaseChatEventModel
import com.hshim.realtimeanonymouschat.feature.chat.ChatEventModel
import io.autocrypt.sakarinblue.universe.util.CommonUtil.convertObject2JsonString
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionManager(private val chatRepository: ChatRepository) {
    private val sessionGroups: MutableMap<String, MutableSet<WebSocketSession>> = ConcurrentHashMap()

    fun addSession(groupId: String, session: WebSocketSession) {
        sessionGroups.computeIfAbsent(groupId) { ConcurrentHashMap.newKeySet() }.add(session)
        val userCnt = getSessionCnt(groupId)
        val chats = chatRepository.findAllByGroupIdAndCreateDateAfterOrderByCreateDateAsc(
            groupId = groupId,
            createDate = LocalDate.now().atStartOfDay(),
        ).map { ChatEventModel.SessionInfo.BeforeChat(it) }
        send(session, ChatEventModel.SessionInfo(session.id, chats))
        send(groupId, ChatEventModel.JoinInfo(session.id, userCnt))
    }

    fun removeSession(groupId: String, session: WebSocketSession) {
        sessionGroups[groupId]?.remove(session)
        when (val userCnt = getSessionCnt(groupId)) {
            0 -> sessionGroups.remove(groupId)
            else -> send(groupId, ChatEventModel.ExitInfo(session.id, userCnt))
        }
    }

    fun send(groupId: String, event: BaseChatEventModel) = sessionGroups[groupId]?.forEach { send(it, event) }
    fun send(session: WebSocketSession, event: BaseChatEventModel) {
        if (session.isOpen) session.sendMessage(TextMessage(convertObject2JsonString(event)))
    }

    fun getSessionCnt(groupId: String) = sessionGroups[groupId]?.size ?: 0
}