package com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.pelsinkaplan.upschoolcapstoneproject.data.apimodel.Address
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 23.06.2022.
 */
@HiltViewModel
class PaymentViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI) : ViewModel() {
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

    suspend fun getBagList(user: String): List<Product>? {
        val dataResponse = retrofitAPI.getBagProductsByUser(user)
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }

    suspend fun deleteProductFromBag(id: Int) {
        retrofitAPI.deleteFromBag(id)
    }
}