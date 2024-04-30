package org.koitharu.workinspector.ui.util

import androidx.annotation.DrawableRes
import androidx.work.WorkInfo
import org.koitharu.workinspector.R

@get:DrawableRes
internal val WorkInfo.State?.iconResId: Int
    get() =
        when (this) {
            WorkInfo.State.ENQUEUED -> R.drawable.ic_state_queued
            WorkInfo.State.RUNNING -> R.drawable.ic_state_running
            WorkInfo.State.SUCCEEDED -> R.drawable.ic_state_succeeded
            WorkInfo.State.FAILED -> R.drawable.ic_state_failed
            WorkInfo.State.BLOCKED -> R.drawable.ic_state_blocked
            WorkInfo.State.CANCELLED -> R.drawable.ic_state_cancelled
            else -> R.drawable.ic_state_unknown
        }
