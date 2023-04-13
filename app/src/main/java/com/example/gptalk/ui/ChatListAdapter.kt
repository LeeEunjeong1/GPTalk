package com.example.gptalk.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.utils.PrefUtil
import com.example.domain.utils.Util
import com.example.gptalk.R
import com.example.gptalk.databinding.ItemChatBinding
import com.example.gptalk.model.Chatting

class ChatListAdapter(
    private val prefUtil: PrefUtil
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
                // 채팅 text
                txtChat.text = current.text
                if(current.mode=="Q"){
                    txtChat.gravity = Gravity.START
                    viewLeft.visibility = View.GONE
                    viewRight.visibility = View.VISIBLE
                }else{
                    txtChat.gravity = Gravity.END
                    txtChat.setBackgroundResource(R.color.my_light_primary)
                    viewLeft.visibility = View.VISIBLE
                    viewRight.visibility = View.GONE
                }
                // 공유 하기 모드
                if(prefUtil.getBoolean("IS_SHARE")){
                    checkChat.visibility = View.VISIBLE
                }else{
                    checkChat.visibility = View.GONE
                }
                // 공유할 채팅 저장
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

    // onBindViewHolder position 값 고정
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // 채팅 리스트 값 추가
    fun setChats(chat: List<Chatting>) {
        clearChats()
        chatList.addAll(chat)
        notifyDataSetChanged()
    }

    // 채팅 리스트 초기화
    fun clearChats() {
        val size = chatList.size
        chatList.clear()
        notifyItemRangeRemoved(0, size)
    }
}