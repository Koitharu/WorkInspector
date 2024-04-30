package org.koitharu.workinspector.data.workers.model

import androidx.work.WorkInfo

internal data class WorkerInfo(
    val id: String,
    val name: String?,
    val workerClassName: String,
    val state: WorkInfo.State?,
    val isPeriodic: Boolean,
)
