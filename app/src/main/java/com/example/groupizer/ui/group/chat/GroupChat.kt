package com.example.groupizer.ui.group.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupizer.R
import com.example.groupizer.databinding.FragmentGroupChatBinding
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.pojo.repository.DashboardRepository
import com.example.groupizer.ui.dashboard.home.others_adapter.MessagesAdapter
import com.example.groupizer.ui.getToken
import com.example.groupizer.ui.group.chat.viewmodel.ChatViewModel
import com.example.groupizer.ui.group.chat.viewmodel.ChatViewModelFactory
import com.example.groupizer.ui.group.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GroupChat : Fragment() {
    private val TAG = "GroupChat"
    private val mainViewModel: GroupViewModel by activityViewModels()
    private val chatViewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(
            DashboardRepository.getInstance(),
            getToken()!!, mainViewModel.getGroup().value!!.chat)

    }
    private lateinit var adapter: MessagesAdapter
    private lateinit var binding: FragmentGroupChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupChatBinding.inflate(inflater, container, false)
        adapter = MessagesAdapter()


        prepareAdapter()

        binding.sendMessageBtn.setOnClickListener {
            chatViewModel.sendMessage(
                binding.message.text.toString()
            )

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun prepareAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            chatViewModel.getAllMessages().let {
                chatViewModel.setMessages(it.messages as MutableList<Message>)
                chatViewModel.messages.observe(this@GroupChat) { messages ->
                    adapter.submitList(messages)
                }
            }
        }


        setupMessagesRv(adapter)

    }

    private fun setupMessagesRv(iAdapter: MessagesAdapter) {
        binding.messagesRv.apply {
            adapter = iAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}