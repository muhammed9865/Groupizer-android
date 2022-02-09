package com.example.groupizer.ui.group.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import com.example.groupizer.Constants
import com.example.groupizer.pojo.model.chat.SendMessage
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.pojo.repository.DashboardRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.*
import kotlin.math.log

class ChatViewModel(private val repository: DashboardRepository, token: String, chat_id: Int) : ViewModel() {
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
                Log.d(TAG, "onMessage: ${receiveMessage(text).user?.name } }}")
                _messages.value?.add(receiveMessage(text))
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

    private fun fromMessage(message: String): String {
        return gson.toJson(SendMessage(message))
    }

    fun sendMessage(text: String) {
        webSocket?.send(fromMessage(text))
    }

    fun receiveMessage(message: String): Message {
        return gson.fromJson(message, Message::class.java)
    }

    suspend fun getAllMessages() = repository.getGroupMessages(token.value!!, chat.value!!)

    fun setMessages(messages: MutableList<Message>) {
        _messages.value = messages
    }


    override fun onCleared() {
        super.onCleared()
        webSocket?.close(200, "User left the chat")
    }

    companion object {
        private const val TAG = "ChatViewModel"
    }

}