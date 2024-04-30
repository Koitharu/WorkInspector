package org.koitharu.workinspector.ui.workers

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.koitharu.workinspector.data.workers.model.WorkerInfo
import org.koitharu.workinspector.databinding.ItemWorkerBinding
import org.koitharu.workinspector.ui.util.iconResId

internal class WorkerViewHolder(
    private val binding: ItemWorkerBinding,
    private val clickListener: OnWorkerClickListener,
) : ViewHolder(binding.root), View.OnClickListener {
    private var item: WorkerInfo? = null

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        clickListener.onWorkerClick(v, item ?: return)
    }

    fun onBind(item: WorkerInfo) {
        this.item = item
        binding.textViewId.text = item.id
        binding.textViewClass.text = item.workerClassName
        binding.textViewName.text = item.name
        binding.imageViewStatus.setImageResource(item.state.iconResId)
    }
}
