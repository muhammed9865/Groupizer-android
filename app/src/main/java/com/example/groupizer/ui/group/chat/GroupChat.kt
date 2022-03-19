package com.example.groupizer.ui.group.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.FragmentGroupChatBinding
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.pojo.repository.DashboardRepository
import com.example.groupizer.ui.group.chat.adapter.MessagesAdapter
import com.example.groupizer.ui.getID
import com.example.groupizer.ui.getToken
import com.example.groupizer.ui.group.chat.viewmodel.ChatViewModel
import com.example.groupizer.ui.group.chat.viewmodel.ChatViewModelFactory
import com.example.groupizer.ui.group.viewmodel.GroupViewModel
import kotlinx.coroutines.*


class GroupChat : Fragment() {
    private val mainViewModel: GroupViewModel by activityViewModels()
    private val chatViewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(
            DashboardRepository.getInstance(),
            getToken()!!, mainViewModel.getChatId()
        )

    }
    private lateinit var adapter: MessagesAdapter
    private lateinit var binding: FragmentGroupChatBinding
    private lateinit var newMessages: MutableList<Message>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupChatBinding.inflate(inflater, container, false)
        adapter = MessagesAdapter(getID())



        prepareAdapter()

        // On Send Button clicked, it will be sent.
        binding.sendMessageBtn.setOnClickListener {
            binding.message.text.let {
                if (it.isNotEmpty()) {
                    chatViewModel.sendMessage(it.toString())
                    binding.message.text.clear()
                }
            }

        }

        // On Any Message Received, it will be added.
        chatViewModel.onMessageReceived(object : MessageReceive {
            override fun onMessageReceived(message: Message) {
                runBlocking(Dispatchers.Main) {
                    newMessages.add(0, message)
                    adapter.submitList(newMessages)
                    adapter.notifyDataSetChanged()
                }
            }
        })
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun prepareAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            chatViewModel.getAllMessages().let {
                chatViewModel.setMessages(it.messages as MutableList<Message>)
                newMessages = it.messages.asReversed()
                observeChat()
                setupMessagesRv(adapter)

            }
        }


    }

    private fun setupMessagesRv(iAdapter: MessagesAdapter) {
        binding.messagesRv.apply {
            adapter = iAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
        }
    }

    private fun observeChat() {
        adapter.submitList(newMessages)
    }



    companion object {
        private const val TAG = "GroupChat"
    }


}