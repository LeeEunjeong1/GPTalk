package com.example.gptalk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.domain.utils.Util
import com.example.gptalk.databinding.FragmentFirstBinding
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

//        viewModel.init()
        initLayout()
        initListener()
        return binding.root
    }

    fun initLayout(){
        with(binding) {
            recyclerMessages.adapter = adapter
        }

    }

    fun initListener(){
        with(viewModel){
            chatList.observe(viewLifecycleOwner){
                Util.logMessage("it :: $it")
                adapter.setChats(it)
            }
        }
        binding.btnSubmit.setOnClickListener{
            Util.logMessage("ititit :: ${binding.edtMessage.text}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}