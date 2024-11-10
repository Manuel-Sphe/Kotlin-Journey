package com.org.crud.controller

import com.org.crud.repository.Message
import com.org.crud.service.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/message")
class MessageController(val service : MessageService) {

    @GetMapping()
    fun index(@RequestParam("name") name: String) = "Hello, $name"

    @PostMapping("/create")
    fun createOne( @RequestBody param : Message): ResponseEntity<Message> {
        val savedMessage = service.save(param)
        return  ResponseEntity.created(URI("/${savedMessage.id}")).body(savedMessage)
    }

    @GetMapping("/all")
    fun getAllMessages() = ResponseEntity.ok(service.findMessages())

    @GetMapping("/{id}")
    fun findOneById(@PathVariable id : String)  = service.findOneById(id).toResponseEntity()

    private fun Message?.toResponseEntity(): ResponseEntity<Message> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
}