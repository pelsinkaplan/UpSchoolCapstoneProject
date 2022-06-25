package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.beforelogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentForgetPasswordBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentLoginBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.beforelogin.ForgetPasswordViewModel
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.beforelogin.LoginViewModel

class ForgetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var viewModel: ForgetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ForgetPasswordViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resetPasswordButton.setOnClickListener {
            viewModel.resetPassword(view, binding.emailInputEditText.text.toString())
            viewModel.resetSuccess.observe(viewLifecycleOwner) {
                if (it) {
                    val action =
                        ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToLoginFragment()
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }
        }
    }
}

//resetPasswordbutton