package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.pelsinkaplan.upschoolcapstoneproject.R


class RegisterViewModel : ViewModel() {
    fun service(
        name: String, email: String,
        password: String, repassword: String,
        view: View
    ) {
        when {
            TextUtils.isEmpty(name.trim { it <= ' ' }) -> {
                Toast.makeText(view.context, "Please enter name surname!", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                Toast.makeText(view.context, "Please enter email!", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(password.trim { it <= ' ' }) -> {
                Toast.makeText(view.context, "Please enter password!", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(repassword.trim { it <= ' ' }) -> {
                Toast.makeText(view.context, "Please enter repassword!", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                val nameControl = name.trim() { it <= ' ' }
                val emailControl = email.trim() { it <= ' ' }
                val passwordControl = password.trim() { it <= ' ' }
                val rePasswordControl = repassword.trim() { it <= ' ' }
                if (passwordControl != rePasswordControl)
                    Toast.makeText(
                        view.context,
                        "Passwords do not match!",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailControl, passwordControl)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    view.context,
                                    "Registration Successful!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val user = FirebaseAuth.getInstance().currentUser
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameControl).build()
                                user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener {
                                        if (task.isSuccessful) {
                                            Log.d("USER DISPLAY NAME", "User profile updated.")
                                        }
                                    }
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_registerFragment_to_loginFragment)
                            } else
                                Toast.makeText(
                                    view.context,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                }
            }
        }
    }
}