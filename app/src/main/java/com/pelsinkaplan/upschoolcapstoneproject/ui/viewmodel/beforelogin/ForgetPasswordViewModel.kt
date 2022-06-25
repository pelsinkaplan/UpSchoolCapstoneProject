package com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.beforelogin

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by Pelşin KAPLAN on 21.06.2022.
 */
class ForgetPasswordViewModel : ViewModel() {
    val resetSuccess = MutableLiveData<Boolean>()

    fun resetPassword(view: View, email: String) {
        when {
            TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                Toast.makeText(view.context, "Please enter email!", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                val emailControl = email.trim { it <= ' ' }
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailControl)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            resetSuccess.postValue(true)
                            Toast.makeText(
                                view.context,
                                "Login Successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            resetSuccess.postValue(false)

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