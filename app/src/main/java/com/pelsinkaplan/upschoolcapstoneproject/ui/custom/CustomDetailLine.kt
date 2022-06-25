package com.pelsinkaplan.upschoolcapstoneproject.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.pelsinkaplan.upschoolcapstoneproject.databinding.CustomDetailLineBinding

/**
 * Created by Pelşin KAPLAN on 6.06.2022.
 */
class CustomDetailLine : ConstraintLayout {
    private lateinit var binding: CustomDetailLineBinding

    constructor(context: Context) : super(context) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout()
    }

    private fun initLayout() {
        binding = CustomDetailLineBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTitleTextView(detail: String) {
        binding.titleTextView.text = detail
    }

    fun setDetailTextView(detail: String) {
        binding.detailTextView.text = detail
    }
}