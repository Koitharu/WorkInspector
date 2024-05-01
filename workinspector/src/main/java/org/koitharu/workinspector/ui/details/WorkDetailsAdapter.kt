package org.koitharu.workinspector.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.flow.FlowCollector
import org.koitharu.workinspector.databinding.WiItemWorkDetailsBinding
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class WorkDetailsAdapter :
    ListAdapter<WorkDetailsItem, WorkDetailsHolder>(WorkDetailsCallback()),
    FlowCollector<List<WorkDetailsItem>> {
    override suspend fun emit(value: List<WorkDetailsItem>) {
        suspendCoroutine { cont ->
            submitList(value) { cont.resume(Unit) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = WorkDetailsHolder(
        WiItemWorkDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    override fun onBindViewHolder(
        holder: WorkDetailsHolder,
        position: Int,
    ) = holder.onBind(getItem(position))
}
