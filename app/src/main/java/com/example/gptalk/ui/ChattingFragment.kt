package com.example.gptalk.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.utils.PrefUtil
import com.example.gptalk.databinding.FragmentChattingBinding
import com.example.gptalk.model.Chatting
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChattingFragment : Fragment() {

    private var _binding: FragmentChattingBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModels<ChattingViewModel>()
    private val adapter by lazy { ChatListAdapter(PrefUtil(requireContext())) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChattingBinding.inflate(inflater, container, false)

        initLayout()
        initListener()
        initObserve()

        return binding.root
    }

    private fun initLayout() {
        with(binding) {
            // 키패드
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                root.setOnApplyWindowInsetsListener { _, windowInsets ->
                    val imeHeight = windowInsets.getInsets(WindowInsets.Type.ime()).bottom
                    binding.root.setPadding(0, 0, 0, imeHeight)
                    val insets =
                        windowInsets.getInsets(WindowInsets.Type.ime() or WindowInsets.Type.systemGestures())
                    insets
                    windowInsets
                }
            } else {
                requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }

            recyclerMessages.adapter = adapter

            val layoutManager = LinearLayoutManager(context)
            layoutManager.stackFromEnd = true
            recyclerMessages.layoutManager = layoutManager
        }

    }

    private fun initListener() {
        with(binding) {
            // 전송 버튼 클릭
            btnSubmit.setOnClickListener {
                if(edtMessage.text.isNotEmpty()){
                    loadSubmit()
                }
            }
            // 입력창 클릭
            edtMessage.setOnEditorActionListener{ textView, action, event ->
                var handled = false
                if (action == EditorInfo.IME_ACTION_DONE) {
                    // 키보드 내리기
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(edtMessage.windowToken, 0)
                    handled = true
                }
                handled
            }
        }
    }

    private fun initObserve() {
        with(viewModel) {
            // 채팅 화면 연결
            chatList.observe(viewLifecycleOwner) {
                adapter.setChats(it)
                binding.recyclerMessages.scrollToPosition(adapter.itemCount - 1)
            }
            // 에러
            mutableErrorType.observe(viewLifecycleOwner){
                Toast.makeText(context, "$it 다시 시도해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 질문 제출
    private fun loadSubmit() {
        with(viewModel){
            with(binding){
                setChatting(
                    Chatting("Q", edtMessage.text.toString())
                )
                submit(edtMessage.text.toString())
                edtMessage.setText("")
            }
        }
        // TODO 로딩바
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.localGetChatting()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onStop(){
        super.onStop()
        val prefUtil = PrefUtil(requireContext())
        prefUtil.setBoolean("IS_SHARE",false)
        prefUtil.setString("SHARE_ITEM","")
    }

}