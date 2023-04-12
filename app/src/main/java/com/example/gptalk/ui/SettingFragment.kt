package com.example.gptalk.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.data.utils.PrefUtil
import com.example.gptalk.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        initListener()
        initObserve()

        return binding.root


    }

    private fun initListener(){

        with(binding){
            val prefUtil = PrefUtil(requireContext())
            editTemperature.setText(prefUtil.getString("TEMPERATURE"))
            editFrequency.setText(prefUtil.getString("FREQUENCY_PENALTY"))
            editTemperature.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    prefUtil.setString("TEMPERATURE",charSequence.toString())
                }

                override fun afterTextChanged(editable: Editable) {
                }
            })
            editFrequency.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    prefUtil.setString("FREQUENCY_PENALTY",charSequence.toString())
                }

                override fun afterTextChanged(editable: Editable) {
                }
            })
            btnReset.setOnClickListener {
                viewModel.chatReset()
            }
        }
    }

    private fun initObserve(){
        with(viewModel){
            // 초기화 완료
            chatResetResult.observe(viewLifecycleOwner){
                if(it){
                    Toast.makeText(context, "채팅이 초기화되었습니다..", Toast.LENGTH_SHORT).show()
                }
            }
            // 에러
            mutableErrorType.observe(viewLifecycleOwner){
                Toast.makeText(context, "$it 다시 시도해 주세요", Toast.LENGTH_SHORT).show()
            }
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