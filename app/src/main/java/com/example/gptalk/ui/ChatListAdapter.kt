package com.example.gptalk.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gptalk.databinding.ItemChatBinding

class ChatListAdapter :
    RecyclerView.Adapter<ChatListAdapter.ProductViewHolder>() {
    private var chatList: MutableList<String> = mutableListOf()

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

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = chatList[position]

        with(holder) {
            with(binding) {
                txtChat.text = current
            }
        }
    }

    fun setChats(chat: List<String>) {
        //unFilteredList = products
        chatList.addAll(chat)
        notifyDataSetChanged()
    }

    fun clearChats() {
        val size = chatList.size
        chatList.clear()
        notifyItemRangeRemoved(0, size)
    }


}