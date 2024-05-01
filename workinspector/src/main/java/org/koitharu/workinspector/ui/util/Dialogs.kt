package org.koitharu.workinspector.ui.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koitharu.workinspector.R

internal fun showErrorDialog(
    context: Context,
    error: Throwable,
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(R.string.wi_error_generic)
        .setMessage(error.message)
        .setNegativeButton(android.R.string.ok, null)
        .setCancelable(true)
        .show()
}
