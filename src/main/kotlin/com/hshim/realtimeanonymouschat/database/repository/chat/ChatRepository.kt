package com.hshim.realtimeanonymouschat.database.repository.chat

import com.hshim.realtimeanonymouschat.database.entity.chat.Chat
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ChatRepository: JpaRepository<Chat, String> {
    fun findAllByGroupIdAndCreateDateAfterOrderByCreateDateAsc(
        groupId: String,
        createDate: LocalDateTime,
    ): List<Chat>
}