package org.koitharu.workinspector.ui.workers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.flow.FlowCollector
import org.koitharu.workinspector.data.workers.model.WorkerInfo
import org.koitharu.workinspector.databinding.ItemWorkerBinding
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class WorkersAdapter(
    private val clickListener: OnWorkerClickListener,
) : ListAdapter<WorkerInfo, WorkerViewHolder>(WorkerItemCallback()),
    FlowCollector<List<WorkerInfo>> {
    override suspend fun emit(value: List<WorkerInfo>) {
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
