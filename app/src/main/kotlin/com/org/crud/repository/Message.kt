package com.org.crud.repository

import java.util.*

data class Message(val id: String? = UUID.randomUUID().toString(), val text : String)
