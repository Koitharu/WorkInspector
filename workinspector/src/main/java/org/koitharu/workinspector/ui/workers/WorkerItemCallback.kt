package org.koitharu.workinspector.ui.workers

import androidx.recyclerview.widget.DiffUtil
import org.koitharu.workinspector.data.workers.model.WorkerInfo

internal class WorkerItemCallback : DiffUtil.ItemCallback<WorkerInfo>() {
    override fun areItemsTheSame(
        oldItem: WorkerInfo,
        newItem: WorkerInfo,
    ): Boolean = oldItem.workerClassName == newItem.workerClassName

    override fun areContentsTheSame(
        oldItem: WorkerInfo,
        newItem: WorkerInfo,
    ): Boolean = oldItem == newItem
}
