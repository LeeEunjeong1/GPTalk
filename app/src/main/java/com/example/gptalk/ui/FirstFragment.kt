package com.example.gptalk.ui

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gptalk.databinding.FragmentFirstBinding
import com.example.gptalk.model.Chatting
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModels<FirstViewModel>()
    private val adapter by lazy { ChatListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        initLayout()
        initListener()
        initObserve()
        return binding.root
    }

    fun initLayout() {
        with(binding) {
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

    fun initListener() {
        with(binding) {
            btnSubmit.setOnClickListener {
                loadSubmit()
            }
        }
    }

    fun initObserve() {
        with(viewModel) {
            chatList.observe(viewLifecycleOwner) {
                adapter.setChats(it)
                binding.recyclerMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    // 질문 제출
    private fun loadSubmit() {
        viewModel.setChatting(
            Chatting("Q", binding.edtMessage.text.toString())
        )
        viewModel.submit(binding.edtMessage.text.toString())
        // TODO 로딩바
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}