package com.example.groupizer.ui.group.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groupizer.Constants
import com.example.groupizer.pojo.model.chat.SendMessage
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.pojo.repository.DashboardRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.*
import java.util.*

class ChatViewModel(private val repository: DashboardRepository, token: String, chat_id: Int) :
    ViewModel() {
    private val _messages = MutableLiveData<MutableList<Message>>(mutableListOf())
    val messages: LiveData<MutableList<Message>> = _messages
    private val token = MutableLiveData(token)
    private val chat = MutableLiveData(chat_id)
    private val gson = Gson()
    private var webSocket: WebSocket? = null

    init {
        val listener = object : WebSocketListener() {
            override fun onOpen(
                webSocket: WebSocket, response: Response
            ) {
                super.onOpen(webSocket, response)
                Log.d(TAG, "Event = : Connection was established Successfully")
            }

            override fun onMessage(
                webSocket: WebSocket, text: String
            ) {
                super.onMessage(webSocket, text)
                Log.d(TAG, "onMessage: ${receiveMessage(text).user?.name} }}")
                val msg = receiveMessage(text)
                setMessages(msg)
            }

            override fun onClosing(
                webSocket: WebSocket, code: Int, reason: String
            ) {
                super.onClosed(webSocket, code, reason)
                // Do something when the connection is closing
            }

            override fun onFailure(
                webSocket: WebSocket, t: Throwable, response: Response?
            ) {
                super.onFailure(webSocket, t, response)
                Log.d(TAG, "onFailure: ${t.message}")
            }
        }

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.WS_BASE_URL + "${chat.value}/")
            .addHeader("Authorization", token)
            .build()

        webSocket = client.newWebSocket(request, listener)


    }

    fun sendMessage(text: String) {
        Log.d(TAG, "sendMessage: $text")
        webSocket?.send(fromMessage(text))
    }


    /*
       Encoding the message into json to be sent.
    */
    private fun fromMessage(message: String): String {
        return gson.toJson(SendMessage(message))
    }

    /*
        Decoding the received message into the Message data class.
     */
    fun receiveMessage(message: String): Message {
        Log.d(TAG, "receiveMessage: $message")
        return gson.fromJson(message, Message::class.java)
    }

    /*
        This is a normal http call, that fetches the messages
        that's associated with the group id "chat".
     */
    fun getAllMessages() {
        viewModelScope.launch {
            flow {
                emit(repository.getGroupMessages(token.value!!, chat.value!!))
            }.collect {
                // Reversing the list so it can appear as "reversed" hamada helal lol
                _messages.value = it.messages.toMutableList().asReversed()
            }
        }

    }

    /*
        The received message is processed by this function.
        it gets the messages value, then adds the new message at index 0
        to be shown at the bottom of the list, as it's 'reversed'.
     */
    private fun setMessages(message: Message) {
        viewModelScope.launch {
            val newMessages = _messages.value
            newMessages?.add(0,message)
            _messages.value = newMessages!!
        }
    }

    override fun onCleared() {
        super.onCleared()
        webSocket?.close(1000, "User left the chat")
    }

    companion object {
        private const val TAG = "CHATTESTING"
    }

}