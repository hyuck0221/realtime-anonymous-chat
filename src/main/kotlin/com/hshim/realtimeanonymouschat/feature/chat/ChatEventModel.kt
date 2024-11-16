package com.hshim.realtimeanonymouschat.feature.chat

import com.hshim.realtimeanonymouschat.database.entity.chat.Chat
import io.autocrypt.sakarinblue.universe.util.DateUtil.dateToString

class ChatEventModel {
    class SessionInfo(
        val id: String,
        val beforeChats: List<BeforeChat>,
    ): BaseChatEventModel("session_info") {
        class BeforeChat(
            val content: String,
            val sessionId: String,
            val createDate: String,
        ) {
            constructor(chat: Chat): this (
                chat.content,
                chat.sessionId,
                chat.createDate.dateToString(),
            )
        }
    }

    class JoinInfo(
        val id: String,
        val userCnt: Int,
    ): BaseChatEventModel("join_info")

    class ExitInfo(
        val id: String,
        val userCnt: Int,
    ): BaseChatEventModel("exit_info")

    class ChatInfo(
        val id: String,
        val content: String,
    ): BaseChatEventModel("chat_info")
}