package com.example.gptalk.ui

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gptalk.databinding.ItemChatBinding
import com.example.gptalk.model.Chatting

class ChatListAdapter :
    RecyclerView.Adapter<ChatListAdapter.ProductViewHolder>() {
    private var chatList: MutableList<Chatting> = mutableListOf()

    inner class ProductViewHolder(val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = chatList[position]

        with(holder) {
            with(binding) {
                txtChat.text = current.text
                if(current.mode=="Q"){ // 질문
                    txtChat.gravity = Gravity.START
                }else{ // 답변
                    txtChat.gravity = Gravity.END
                }
            }
        }
    }

    fun setChats(chat: List<Chatting>) {
        clearChats()
        chatList.addAll(chat)
        notifyDataSetChanged()
    }

    fun clearChats() {
        val size = chatList.size
        chatList.clear()
        notifyItemRangeRemoved(0, size)
    }


}