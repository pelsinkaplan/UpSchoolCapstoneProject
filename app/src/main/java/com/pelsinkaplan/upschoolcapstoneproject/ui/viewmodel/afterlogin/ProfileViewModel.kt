package com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.pelsinkaplan.upschoolcapstoneproject.data.apimodel.Address
import timber.log.Timber

/**
 * Created by Pelşin KAPLAN on 22.06.2022.
 */
class ProfileViewModel : ViewModel() {
    var firebaseFirestore = FirebaseFirestore.getInstance()
    var address = MutableLiveData<Address>()

    fun getAddresses(userId: String) {
        firebaseFirestore.collection(userId).document("address").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    address.postValue(
                        document.toObject(
                            Address()::class.java
                        )
                    )
                } else {
                    Timber.tag("FIRESTORE").d("No such document")
                }
            }.addOnFailureListener { exception ->
                Timber.tag("ERROR").d(exception, "get failed with ")
            }

    }

    fun putAddress(address: String, userId: String) {
        firebaseFirestore.collection(userId).document("address")
            .set(hashMapOf("address" to address))
            .addOnSuccessListener {
                Timber.tag("FIRESTORE").i("SUCCESS")
            }
            .addOnFailureListener { e ->
                Timber.tag("FIRESTORE ERROR").e(e.message!!)
            }
    }
}