package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.beforelogin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentLoginBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin.ProfileFragmentDirections
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.beforelogin.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: LoginViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginButton.setOnClickListener {
                viewModel.service(
                    emailInputEditText.text.toString(),
                    passwordInputEditText.text.toString(),
                    view
                )
                viewModel.loginSuccess.observe(viewLifecycleOwner) {
                    if (it) {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_loginFragment_to_afterLoginActivity)
                    }
                }
            }
            forgetPasswordButton.setOnClickListener {
                val action =
                    LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}