package org.koitharu.workinspector.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.flow.FlowCollector
import org.koitharu.workinspector.data.details.model.WorkDetails
import org.koitharu.workinspector.databinding.ItemWorkDetailsBinding
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class WorkDetailsAdapter() :
    ListAdapter<WorkDetails, WorkDetailsHolder>(WorkDetailsCallback()),
    FlowCollector<List<WorkDetails>> {
    override suspend fun emit(value: List<WorkDetails>) {
        suspendCoroutine { cont ->
            submitList(value) { cont.resume(Unit) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = WorkDetailsHolder(
        ItemWorkDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    override fun onBindViewHolder(
        holder: WorkDetailsHolder,
        position: Int,
    ) = holder.onBind(getItem(position))
}
