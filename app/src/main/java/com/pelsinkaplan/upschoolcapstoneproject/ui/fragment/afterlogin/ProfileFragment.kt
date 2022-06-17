package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = FirebaseAuth.getInstance().currentUser!!
        binding.apply {
            userNameSurnameCustomProfileDetailLine.setTitleTextView("Name Surname")
            userNameSurnameCustomProfileDetailLine.setDetailTextView(user.displayName.toString())
            userEmailCustomProfileDetailLine.setTitleTextView("E-mail")
            userEmailCustomProfileDetailLine.setDetailTextView(user.email.toString())
            try {
                userProfilePhotoImageView.setImageURI(user.photoUrl)
            } catch (e: Exception) {
                userProfilePhotoImageView.setImageResource(R.drawable.ic_three_dots)
            }
        }
        binding.userProfilePhotoImageView.setOnClickListener {
            pickImageFromGallery()
        }

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imageFromGalleryLauncher.launch(intent)
    }

    private var imageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                binding.userProfilePhotoImageView.setImageURI(data?.data)
                user.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setPhotoUri(data?.data).build()
                )
            }
        }
}