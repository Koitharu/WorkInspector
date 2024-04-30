package org.koitharu.workinspector.ui.details

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.koitharu.workinspector.data.details.model.WorkDetails
import org.koitharu.workinspector.databinding.ItemWorkDetailsBinding
import org.koitharu.workinspector.ui.util.iconResId
import org.koitharu.workinspector.ui.util.titleResId

internal class WorkDetailsHolder(
    private val binding: ItemWorkDetailsBinding,
) : ViewHolder(binding.root) {
    fun onBind(item: WorkDetails) {
        binding.textViewId.text = item.id
        binding.imageViewStatus.setImageResource(item.state.iconResId)
        binding.imageViewStatus.contentDescription =
            itemView.context.getString(item.state.titleResId)
        binding.textViewStatus.setText(item.state.titleResId)
    }
}
