package com.erdemtsynduev.rotate360degree.common.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

fun AppCompatTextView?.text(isTrimRequired: Boolean = false): String {
    return if (this != null) if (isTrimRequired) text.toString().trim() else text.toString() else ""
}

fun AppCompatTextView.setColorOfSubstring(substring: String, color: Int) {
    if (text.contains(substring)) {
        val spannable = SpannableString(text)
        val start = text.indexOf(substring)
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, color)),
            start,
            start + substring.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannable
    }
}

fun AppCompatTextView.setUnderlineSpannableText(message: String) {
    text = if (message.isBlank()) {
        ""
    } else {
        SpannableString(message).apply {
            setSpan(UnderlineSpan(), 0, message.length, 0)
        }
    }
}
