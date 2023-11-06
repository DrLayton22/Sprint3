package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Message
import com.example.myapplication.repository.MessageRepository

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {

    private val messageRepository = MessageRepository.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_msg_viewholder, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messages = messageRepository.getMessages() // Obtener la lista de mensajes a través del repositorio
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messageRepository.getMessageCount() // Obtener la cantidad de mensajes a través del repositorio
    }
}

