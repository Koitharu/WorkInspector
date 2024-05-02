package org.koitharu.workinspector

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

public object WorkInspector {
    private const val LAUNCHER_ACTIVITY_ALIAS_SUFFIX = ".WorkInspectorLauncherActivity"

    @JvmStatic
    public fun getIntent(context: Context): Intent = Intent(context, WorkInspectorActivity::class.java)

    @JvmStatic
    public fun setLauncherIconEnabled(
        context: Context,
        isEnabled: Boolean,
    ) {
        val component = ComponentName(context, context.packageName + LAUNCHER_ACTIVITY_ALIAS_SUFFIX)
        val state =
            if (isEnabled) {
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            } else {
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            }
        context.packageManager.setComponentEnabledSetting(
            component,
            state,
            PackageManager.DONT_KILL_APP,
        )
    }
}
