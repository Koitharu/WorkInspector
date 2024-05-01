package org.koitharu.workinspector.ui.details

import androidx.recyclerview.widget.DiffUtil

internal class WorkDetailsCallback : DiffUtil.ItemCallback<WorkDetailsItem>() {
    override fun areItemsTheSame(
        oldItem: WorkDetailsItem,
        newItem: WorkDetailsItem,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: WorkDetailsItem,
        newItem: WorkDetailsItem,
    ): Boolean = oldItem == newItem
}
