package com.org.crud.service

import com.org.crud.repository.Message
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MessageService(private val db: JdbcTemplate) {

    fun findMessages() : List<Message> = db.query("select id, text from messages"){ response, _ ->
        Message(response.getString("id"), response.getString("text"))
    }

    fun findOneById(id: String) : Message? =
        db.query("select id, text from id = ?", id) {response , _ ->
            Message(response.getString("id"), response.getString("text"))
        }.singleOrNull()

    fun save(message: Message) : Message {
        val id = message.id ?: UUID.randomUUID().toString()
        db.update(
            "insert into messages value (?, ?)"
            , id, message.text
        )
        return message.copy(id = id)
    }
}