package org.koitharu.workinspector.ui.details

import androidx.recyclerview.widget.DiffUtil
import org.koitharu.workinspector.data.details.model.WorkDetails

internal class WorkDetailsCallback : DiffUtil.ItemCallback<WorkDetails>() {
    override fun areItemsTheSame(
        oldItem: WorkDetails,
        newItem: WorkDetails,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: WorkDetails,
        newItem: WorkDetails,
    ): Boolean = oldItem == newItem
}
