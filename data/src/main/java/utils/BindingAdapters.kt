package utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

interface BindingAdapters {

    @BindingAdapter("imageUrl")
    fun AppCompatImageView.setImageUrl(url: String?)
}
