package com.chul.githubsearcher

import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:onSearch")
    fun AppCompatEditText.onSearchClick(onSearch: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            val keyword = text?.toString() ?: ""
            if(keyword.isEmpty()) return@setOnEditorActionListener true
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    onSearch()
                    return@setOnEditorActionListener true
                }
            }
            false
        }
    }
}