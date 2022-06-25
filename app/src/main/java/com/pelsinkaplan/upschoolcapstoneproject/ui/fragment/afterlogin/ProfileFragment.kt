package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.AddressBottomSheetBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentProfileBinding
import com.pelsinkaplan.upschoolcapstoneproject.service.room.FavoritesDatabase
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ProductsViewModel
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var bindingBottomSheet: AddressBottomSheetBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProfileViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        bindingBottomSheet = AddressBottomSheetBinding.inflate(inflater, container, false)
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
            userAddressCustomProfileDetailLine.setTitleTextView("Address")
            viewModel.getAddresses(user.uid)
            viewModel.address.observe(viewLifecycleOwner) {
                if (it != null)
                    userAddressCustomProfileDetailLine.setDetailTextView(it.address)
                else
                    userAddressCustomProfileDetailLine.setDetailTextView("Please click to type your address.")
            }
            userAddressCustomProfileDetailLine.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                dialog.setContentView(bindingBottomSheet.root)
                dialog.show()

                bindingBottomSheet.saveButton.setOnClickListener {
                    viewModel.putAddress(
                        bindingBottomSheet.addressInputEditText.text.toString(),
                        user.uid
                    )
                    dialog.dismiss()
                }
            }
            try {
                userProfilePhotoImageView.setImageURI(user.photoUrl)
            } catch (e: Exception) {
                userProfilePhotoImageView.setImageResource(R.drawable.ic_profile_image)
            }
            userProfilePhotoImageView.setOnClickListener {
                pickImageFromGallery()
            }
            logoutButton.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val favoritesDatabase = FavoritesDatabase.getFavoritesDatabase(requireContext())!!
                favoritesDatabase.getFavoritesDao().deleteAll()

                val action =
                    ProfileFragmentDirections.actionProfileFragmentToBeforeLoginActivity()
                Navigation.findNavController(it).navigate(action)
            }
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