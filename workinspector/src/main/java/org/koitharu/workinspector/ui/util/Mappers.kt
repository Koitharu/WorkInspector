package org.koitharu.workinspector.ui.util

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.work.NetworkType
import androidx.work.NetworkType.CONNECTED
import androidx.work.NetworkType.METERED
import androidx.work.NetworkType.NOT_ROAMING
import androidx.work.NetworkType.TEMPORARILY_UNMETERED
import androidx.work.NetworkType.UNMETERED
import androidx.work.WorkInfo
import androidx.work.WorkInfo.Companion.STOP_REASON_APP_STANDBY
import androidx.work.WorkInfo.Companion.STOP_REASON_BACKGROUND_RESTRICTION
import androidx.work.WorkInfo.Companion.STOP_REASON_CANCELLED_BY_APP
import androidx.work.WorkInfo.Companion.STOP_REASON_CONSTRAINT_BATTERY_NOT_LOW
import androidx.work.WorkInfo.Companion.STOP_REASON_CONSTRAINT_CHARGING
import androidx.work.WorkInfo.Companion.STOP_REASON_CONSTRAINT_CONNECTIVITY
import androidx.work.WorkInfo.Companion.STOP_REASON_CONSTRAINT_DEVICE_IDLE
import androidx.work.WorkInfo.Companion.STOP_REASON_CONSTRAINT_STORAGE_NOT_LOW
import androidx.work.WorkInfo.Companion.STOP_REASON_DEVICE_STATE
import androidx.work.WorkInfo.Companion.STOP_REASON_ESTIMATED_APP_LAUNCH_TIME_CHANGED
import androidx.work.WorkInfo.Companion.STOP_REASON_NOT_STOPPED
import androidx.work.WorkInfo.Companion.STOP_REASON_PREEMPT
import androidx.work.WorkInfo.Companion.STOP_REASON_QUOTA
import androidx.work.WorkInfo.Companion.STOP_REASON_SYSTEM_PROCESSING
import androidx.work.WorkInfo.Companion.STOP_REASON_TIMEOUT
import androidx.work.WorkInfo.Companion.STOP_REASON_UNKNOWN
import androidx.work.WorkInfo.Companion.STOP_REASON_USER
import org.koitharu.workinspector.R
import org.koitharu.workinspector.data.details.model.WorkDetails
import org.koitharu.workinspector.data.workers.model.WorkerInfo
import org.koitharu.workinspector.ui.WorkConstraint
import org.koitharu.workinspector.ui.details.WorkDetailsItem
import org.koitharu.workinspector.ui.workers.WorkerItem
import java.util.EnumSet

internal fun WorkerInfo.toWorkerItem() =
    WorkerItem(
        name = workerClassName.substringAfterLast('.'),
        className = workerClassName,
        stateIcon = state.iconResId,
        stateTitle = state.titleResId,
        isPeriodic = isPeriodic,
        lastEnqueueTime = lastEnqueueTime,
        constraints =
            constraintsSet(
                requiredNetworkType = requiredNetworkType,
                requiresBatteryNotLow = requiresBatteryNotLow,
                requiresCharging = requiresCharging,
                requiresDeviceIdle = requiresDeviceIdle,
                requiresStorageNotLow = requiresStorageNotLow,
            ),
    )

internal fun WorkDetails.toWorkDetailsItem() =
    WorkDetailsItem(
        id = id,
        stateIcon = state.iconResId,
        stateTitle = state.titleResId,
        runAttemptCount = runAttemptCount,
        constraints =
            constraintsSet(
                requiredNetworkType = requiredNetworkType,
                requiresBatteryNotLow = requiresBatteryNotLow,
                requiresCharging = requiresCharging,
                requiresDeviceIdle = requiresDeviceIdle,
                requiresStorageNotLow = requiresStorageNotLow,
            ),
        tags =
            tags.filterNot { it == className }
                .map { ChipsView.ChipModel(title = it, icon = R.drawable.wi_ic_tag) },
        inputData = input.keyValueMap,
        outputData = output.keyValueMap,
        lastEnqueueTime = lastEnqueueTime,
        periodicInterval = intervalDuration,
        stopReasonText = mapStopReason(stopReason),
        periodCount = periodCount,
    )

@get:DrawableRes
private val WorkInfo.State?.iconResId: Int
    get() =
        when (this) {
            WorkInfo.State.ENQUEUED -> R.drawable.wi_ic_state_queued
            WorkInfo.State.RUNNING -> R.drawable.wi_ic_state_running
            WorkInfo.State.SUCCEEDED -> R.drawable.wi_ic_state_succeeded
            WorkInfo.State.FAILED -> R.drawable.wi_ic_state_failed
            WorkInfo.State.BLOCKED -> R.drawable.wi_ic_state_blocked
            WorkInfo.State.CANCELLED -> R.drawable.wi_ic_state_cancelled
            else -> R.drawable.wi_ic_state_unknown
        }

@get:StringRes
private val WorkInfo.State?.titleResId: Int
    get() =
        when (this) {
            WorkInfo.State.ENQUEUED -> R.string.wi_state_queued
            WorkInfo.State.RUNNING -> R.string.wi_state_running
            WorkInfo.State.SUCCEEDED -> R.string.wi_state_succeeded
            WorkInfo.State.FAILED -> R.string.wi_state_failed
            WorkInfo.State.BLOCKED -> R.string.wi_state_blocked
            WorkInfo.State.CANCELLED -> R.string.wi_state_cancelled
            else -> R.string.wi_state_unknown
        }

private fun constraintsSet(
    requiredNetworkType: NetworkType?,
    requiresBatteryNotLow: Boolean,
    requiresCharging: Boolean,
    requiresDeviceIdle: Boolean,
    requiresStorageNotLow: Boolean,
): Set<WorkConstraint> {
    val result = EnumSet.noneOf(WorkConstraint::class.java)
    if (requiredNetworkType == CONNECTED || requiredNetworkType == METERED || requiredNetworkType == NOT_ROAMING) {
        result += WorkConstraint.NETWORK_METERED
    }
    if (requiredNetworkType == UNMETERED || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && requiredNetworkType == TEMPORARILY_UNMETERED)) {
        result += WorkConstraint.NETWORK_UNMETERED
    }
    if (requiresBatteryNotLow) {
        result += WorkConstraint.BATTERY
    }
    if (requiresCharging) {
        result += WorkConstraint.CHARGING
    }
    if (requiresDeviceIdle) {
        result += WorkConstraint.IDLE
    }
    if (requiresStorageNotLow) {
        result += WorkConstraint.STORAGE
    }
    return result
}

@StringRes
private fun mapStopReason(reason: Int): Int =
    when (reason) {
        STOP_REASON_NOT_STOPPED -> 0
        STOP_REASON_UNKNOWN -> R.string.wi_stop_reason_unknown
        STOP_REASON_CANCELLED_BY_APP -> R.string.wi_stop_reason_cancelled_by_app
        STOP_REASON_PREEMPT -> R.string.wi_stop_reason_preempt
        STOP_REASON_TIMEOUT -> R.string.wi_stop_reason_timeout
        STOP_REASON_DEVICE_STATE -> R.string.wi_stop_reason_device_state
        STOP_REASON_CONSTRAINT_BATTERY_NOT_LOW -> R.string.wi_stop_reason_constraint_battery_not_low
        STOP_REASON_CONSTRAINT_CHARGING -> R.string.wi_stop_reason_constraint_charging
        STOP_REASON_CONSTRAINT_CONNECTIVITY -> R.string.wi_stop_reason_constraint_connectivity
        STOP_REASON_CONSTRAINT_DEVICE_IDLE -> R.string.wi_stop_reason_constraint_device_idle
        STOP_REASON_CONSTRAINT_STORAGE_NOT_LOW -> R.string.wi_stop_reason_constraint_storage_not_low
        STOP_REASON_QUOTA -> R.string.wi_stop_reason_quota
        STOP_REASON_BACKGROUND_RESTRICTION -> R.string.wi_stop_reason_background_restriction
        STOP_REASON_APP_STANDBY -> R.string.wi_stop_reason_app_standby
        STOP_REASON_USER -> R.string.wi_stop_reason_user
        STOP_REASON_SYSTEM_PROCESSING -> R.string.wi_stop_reason_system_processing
        STOP_REASON_ESTIMATED_APP_LAUNCH_TIME_CHANGED -> R.string.wi_stop_reason_estimated_app_launch_time_changed
        else -> 0
    }
