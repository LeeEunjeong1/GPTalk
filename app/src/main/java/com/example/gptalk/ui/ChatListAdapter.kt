package com.example.gptalk.ui

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.utils.PrefUtil
import com.example.domain.utils.Util
import com.example.gptalk.databinding.ItemChatBinding
import com.example.gptalk.model.Chatting

class ChatListAdapter(
    val prefUtil: PrefUtil
):
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
                if(prefUtil.getBoolean("IS_SHARE")){
                    checkChat.visibility = View.VISIBLE
                }else{
                    checkChat.visibility = View.GONE
                }
                txtChat.text = current.text
                if(current.mode=="Q"){ // 질문
                    txtChat.gravity = Gravity.START
                }else{ // 답변
                    txtChat.gravity = Gravity.END
                }
                checkChat.setOnClickListener {
                    val itemStr = if(current.mode=="Q"){"질문 : ${current.text}\n"}else{"답변 : ${current.text}\n"}
                    var shareItem = prefUtil.getString("SHARE_ITEM")
                    if(checkChat.isChecked){
                        shareItem += itemStr
                        prefUtil.setString("SHARE_ITEM",shareItem)
                    }else{
                        val shareItemRemoved = shareItem.replace(itemStr," ")
                        prefUtil.setString("SHARE_ITEM",shareItemRemoved)
                    }

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
        Util.logMessage("clearChats1 :: $chatList")
        val size = chatList.size
        chatList.clear()
        notifyItemRangeRemoved(0, size)
        Util.logMessage("clearChats2 :: $chatList")
    }


}