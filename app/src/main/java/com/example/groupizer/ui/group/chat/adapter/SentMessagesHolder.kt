package com.example.groupizer.ui.group.chat.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.databinding.ListItemMessageSentBinding
import com.example.groupizer.pojo.model.chat.group.messages.Message

class SentMessagesHolder(private val binding: ListItemMessageSentBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Message, user_id: Int) {
        binding.apply {
            messageText.text = item.text
            messageDate.text = item.sent

           /* if (item.user?.id == user_id) {
                messageBox.setBackgroundResource(R.color.pink)
            }else {
                messageBox.setBackgroundResource(R.color.bright_blue)
            }*/
        }
   }
}