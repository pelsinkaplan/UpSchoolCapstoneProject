package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.beforelogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentRegisterBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: RegisterViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            registerButton.setOnClickListener {
                viewModel.service(
                    nameSurnameInputEditText.text.toString(),
                    emailInputEditText.text.toString(),
                    passwordInputEditText.text.toString(),
                    rePasswordInputEditText.text.toString(),
                    view
                )
            }
        }
    }
}