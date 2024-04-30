package org.koitharu.workinspector

import android.content.Context
import android.content.Intent

public object WorkInspector {
    @JvmStatic
    public fun getIntent(context: Context): Intent = Intent(context, WorkInspectorActivity::class.java)
}
