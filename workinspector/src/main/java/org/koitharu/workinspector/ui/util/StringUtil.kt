package org.koitharu.workinspector.ui.util

import android.graphics.Color
import android.text.SpannableStringBuilder
import androidx.core.text.color

internal fun SpannableStringBuilder.appendNullable(value: Any?) = apply {
    if (value == null) {
        color(Color.GRAY) {
            append("null")
        }
    } else {
        append(value.toString())
    }
}