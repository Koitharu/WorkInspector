package org.koitharu.workinspector.data.workers.model

import androidx.work.NetworkType
import androidx.work.WorkInfo

internal data class WorkerInfo(
    val workerClassName: String,
    val state: WorkInfo.State?,
    val isPeriodic: Boolean,
    val lastEnqueueTime: Long,
    val requiredNetworkType: NetworkType?,
    val requiresBatteryNotLow: Boolean,
    val requiresCharging: Boolean,
    val requiresDeviceIdle: Boolean,
    val requiresStorageNotLow: Boolean,
)
