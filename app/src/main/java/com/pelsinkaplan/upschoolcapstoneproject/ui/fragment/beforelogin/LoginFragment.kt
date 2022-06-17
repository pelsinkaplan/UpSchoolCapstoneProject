package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.beforelogin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentLoginBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.activity.AfterLoginActivity
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.LoginViewModel
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
//                val intent =
//                    Intent(requireActivity(), AfterLoginActivity::class.java)
//                startActivity(intent)
            }
        }
    }
}