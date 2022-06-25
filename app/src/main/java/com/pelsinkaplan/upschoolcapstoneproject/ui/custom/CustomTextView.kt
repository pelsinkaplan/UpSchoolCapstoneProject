package com.pelsinkaplan.upschoolcapstoneproject.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.pelsinkaplan.upschoolcapstoneproject.databinding.CustomTextViewBinding

/**
 * Created by Pelşin KAPLAN on 16.06.2022.
 */
class CustomTextView : ConstraintLayout {
    private lateinit var binding: CustomTextViewBinding

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
        binding = CustomTextViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTextViewText(text: String) {
        binding.textView.text = text
    }

    fun setTextViewSize(size: Float) {
        binding.textView.textSize = size
    }

    fun setTextViewBold() {
        binding.textView.setTypeface(null, Typeface.BOLD)
    }

    fun setTextViewVisible() {
        binding.apply {
            textView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}