package org.koitharu.workinspector.ui.workers

import androidx.recyclerview.widget.DiffUtil

internal class WorkerItemCallback : DiffUtil.ItemCallback<WorkerItem>() {
    override fun areItemsTheSame(
        oldItem: WorkerItem,
        newItem: WorkerItem,
    ): Boolean = oldItem.className == newItem.className

    override fun areContentsTheSame(
        oldItem: WorkerItem,
        newItem: WorkerItem,
    ): Boolean = oldItem == newItem
}
