package com.example.myapplication

data class Message(val username: String, val text: String)

object MessageManager {
    private val messageList = mutableListOf<Message>()

    fun addMessage(username: String, text: String) {
        val message = Message(username, text)
        messageList.add(message)
    }

    fun getMessages(): List<Message> {
        return messageList
    }
}
