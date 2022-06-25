package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.AddressBottomSheetBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentChartBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentPaymentBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.PopupBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ChartViewModel
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.PaymentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel
    private lateinit var bindingBottomSheet: AddressBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: PaymentViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        bindingBottomSheet = AddressBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: PaymentFragmentArgs by navArgs()
        val totalAmount = args.totalAmount
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        binding.apply {
            totalAmountCustomProfileDetailLine.setTitleTextView("Total Amount")
            totalAmountCustomProfileDetailLine.setDetailTextView(totalAmount.toString())
            addressAmountCustomProfileDetailLine.setTitleTextView("Address")
            viewModel.getAddresses(userId)
            viewModel.address.observe(viewLifecycleOwner) {
                if (it != null)
                    addressAmountCustomProfileDetailLine.setDetailTextView(it.address)
                else
                    addressAmountCustomProfileDetailLine.setDetailTextView("Please click to type your address.")
            }
            addressAmountCustomProfileDetailLine.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                dialog.setContentView(bindingBottomSheet.root)
                dialog.show()

                bindingBottomSheet.saveButton.setOnClickListener {
                    viewModel.putAddress(
                        bindingBottomSheet.addressInputEditText.text.toString(),
                        userId
                    )
                    dialog.dismiss()
                }
            }
            confirmButton.setOnClickListener {
                lateinit var popup: AlertDialog
                val alertBinding = PopupBinding.inflate(LayoutInflater.from(requireContext()))
                val builder = AlertDialog.Builder(requireContext())
                alertBinding.apply {
                    backToShopButton.setOnClickListener {
                        val action =
                            PaymentFragmentDirections.actionPaymentFragmentToHomeFragment()
                        Navigation.findNavController(view).navigate(action)
                        popup.dismiss()
                    }
                }
                popup = builder.setView(alertBinding.root)
                    .setCancelable(false)
                    .create()
                popup.window?.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        com.firebase.ui.auth.R.drawable.fui_ic_anonymous_white_24dp
                    )
                )
                popup.show()

                CoroutineScope(Dispatchers.Main).launch {
                    for (item in viewModel.getBagList(userId)!!) {
                        viewModel.deleteProductFromBag(item.id)
                    }
                }
            }
        }
    }
}