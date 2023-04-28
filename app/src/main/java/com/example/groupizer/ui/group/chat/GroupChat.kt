package com.example.groupizer.ui.group.chat

import android.annotation.SuppressLint
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
import com.example.groupizer.pojo.repository.DashboardRepository
import com.example.groupizer.ui.group.chat.adapter.MessagesAdapter
import com.example.groupizer.ui.getID
import com.example.groupizer.ui.getToken
import com.example.groupizer.ui.group.chat.viewmodel.ChatViewModel
import com.example.groupizer.ui.group.chat.viewmodel.ChatViewModelFactory
import com.example.groupizer.ui.group.viewmodel.GroupViewModel



class GroupChat : Fragment() {
    private val mainViewModel: GroupViewModel by activityViewModels()
    private val chatViewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(
            DashboardRepository.getInstance(),
            getToken()!!, mainViewModel.getChatId()
        )

    }
    private val mAdapter: MessagesAdapter by lazy {
        MessagesAdapter(getID())
    }
    private lateinit var binding: FragmentGroupChatBinding
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupChatBinding.inflate(inflater, container, false)

        binding.messagesRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
        }


        // On Send Button clicked, it will be sent.
        binding.sendMessageBtn.setOnClickListener {
            binding.message.text.let {
                if (it.isNotEmpty()) {
                    chatViewModel.sendMessage(it.toString())
                    binding.message.text.clear()
                }
            }
        }

        chatViewModel.getAllMessages()
        chatViewModel.messages.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
            mAdapter.notifyDataSetChanged()
        }

        // On Any Message Received, it will be added.
        // Inflate the layout for this fragment
        return binding.root
    }




}