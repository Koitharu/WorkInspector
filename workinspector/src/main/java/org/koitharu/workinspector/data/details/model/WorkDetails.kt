package org.koitharu.workinspector.data.details.model

import androidx.work.Data
import androidx.work.WorkInfo

internal data class WorkDetails(
    val id: String,
    val state: WorkInfo.State?,
    val input: Data,
    val output: Data,
    val initialDelay: Long,
    val intervalDuration: Long,
    val flexDuration: Long,
    val runAttemptCount: Int,
    val lastEnqueueTime: Long,
    val scheduleRequestedAt: Long,
    val stopReason: Int,
    val requiredNetworkType: Int,
    val requiresBatteryNotLow: Boolean,
    val requiresCharging: Boolean,
    val requiresDeviceIdle: Boolean,
    val requiresStorageNotLow: Boolean,
    val tag: String?,
)
