package com.hshim.realtimeanonymouschat.database.entity.chat

import com.hshim.realtimeanonymouschat.database.entity.BaseTimeEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "chat")
class Chat (
    @Id
    @Column(nullable = false, columnDefinition = "char(32)")
    var id: String = uuid(),

    @Column(nullable = false)
    val sessionId: String,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    var groupId: String,
) : BaseTimeEntity()