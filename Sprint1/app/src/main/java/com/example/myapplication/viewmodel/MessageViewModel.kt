package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Message
import com.example.myapplication.MessageAdapter
import com.example.myapplication.repository.MessageRepository

class MessageViewModel : ViewModel() {
    // LiveData para el adaptador del RecyclerView
    private val _adapterLiveData = MutableLiveData<MessageAdapter>()
    val adapterLiveData: LiveData<MessageAdapter>
        get() = _adapterLiveData

    // LiveData para la posición del último elemento
    private val _lastInsertedPositionLiveData = MutableLiveData<Int>()
    val lastInsertedPositionLiveData: LiveData<Int>
        get() = _lastInsertedPositionLiveData

    // Método para agregar un mensaje al repositorio y actualizar la lista
    fun addMessage(message: Message) {
        // Lógica para agregar el mensaje al repositorio
        val repository = MessageRepository.getInstance()
        repository.addMessage(message)

        // Actualizar la posición del último elemento
        _lastInsertedPositionLiveData.value = repository.getMessageCount() - 1

        // Notificar al adaptador para que refresque la lista
        _adapterLiveData.value?.notifyDataSetChanged()
    }

    // Inicialización del adaptador
    fun initAdapter(adapter: MessageAdapter) {
        _adapterLiveData.value = adapter
    }
}
