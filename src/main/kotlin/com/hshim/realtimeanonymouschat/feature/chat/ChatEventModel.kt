package com.hshim.realtimeanonymouschat.feature.chat

class ChatEventModel {
    class SessionInfo(
        val id: String,
    ): BaseChatEventModel("session_info")

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