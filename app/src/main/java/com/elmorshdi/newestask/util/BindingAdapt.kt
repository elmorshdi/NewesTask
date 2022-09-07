package com.elmorshdi.newestask.util

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("appendTags")
fun appendTags(view: AppCompatTextView, list: List<String>?) {
    val text = StringBuilder()
    list?.forEach {
        text.append("( ").append(it).append(" )").append(" ")
    }
    view.text = text
}
