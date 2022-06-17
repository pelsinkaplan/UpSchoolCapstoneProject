package com.pelsinkaplan.upschoolcapstoneproject.ui.custom

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.pelsinkaplan.upschoolcapstoneproject.databinding.CustomImageViewBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.CustomTextViewBinding

/**
 * Created by Pelşin KAPLAN on 16.06.2022.
 */
class CustomImageView : ConstraintLayout {
    private lateinit var binding: CustomImageViewBinding
    lateinit var imageView: ImageView

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
        binding = CustomImageViewBinding.inflate(LayoutInflater.from(context), this, true)
        imageView = binding.imageView

    }

    fun setImageViewVisible() {
        binding.apply {
            imageView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}