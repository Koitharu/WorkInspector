package org.koitharu.workinspector.ui.workers

import androidx.recyclerview.widget.DiffUtil
import org.koitharu.workinspector.data.workers.model.WorkerInfo

internal class WorkerItemCallback : DiffUtil.ItemCallback<WorkerInfo>() {
    override fun areItemsTheSame(
        oldItem: WorkerInfo,
        newItem: WorkerInfo,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(
        oldItem: WorkerInfo,
        newItem: WorkerInfo,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
