package org.koitharu.workinspector.ui.workers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.flow.FlowCollector
import org.koitharu.workinspector.databinding.ItemWorkerBinding
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class WorkersAdapter(
    private val clickListener: OnWorkerClickListener,
) : ListAdapter<WorkerItem, WorkerViewHolder>(WorkerItemCallback()),
    FlowCollector<List<WorkerItem>> {
    override suspend fun emit(value: List<WorkerItem>) {
        suspendCoroutine { cont ->
            submitList(value) { cont.resume(Unit) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = WorkerViewHolder(
        ItemWorkerBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        clickListener,
    )

    override fun onBindViewHolder(
        holder: WorkerViewHolder,
        position: Int,
    ) = holder.onBind(getItem(position))
}
