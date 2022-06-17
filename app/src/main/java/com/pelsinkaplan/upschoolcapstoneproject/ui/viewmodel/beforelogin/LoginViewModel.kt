package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.ui.activity.AfterLoginActivity

class LoginViewModel : ViewModel() {
    fun service(
        email: String,
        password: String,
        view: View
    ) {

        when {
            TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                Toast.makeText(view.context, "Please enter email!", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(
                password.trim { it <= ' ' }) -> {
                Toast.makeText(
                    view.context,
                    "Please enter password!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else -> {
                val emailControl = email.trim { it <= ' ' }
                val passwordControl = password.trim { it <= ' ' }

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailControl, passwordControl)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                view.context,
                                "Login Successful!",
                                Toast.LENGTH_SHORT
                            ).show()

                            Navigation.findNavController(view)
                                .navigate(R.id.action_loginFragment_to_afterLoginActivity)
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