package org.koitharu.workinspector.ui.workers

import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.koitharu.workinspector.R
import org.koitharu.workinspector.databinding.WiItemWorkerBinding
import org.koitharu.workinspector.ui.WorkConstraint

internal class WorkerViewHolder(
    private val binding: WiItemWorkerBinding,
    private val clickListener: OnWorkerClickListener,
) : ViewHolder(binding.root), View.OnClickListener {
    private var item: WorkerItem? = null

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        clickListener.onWorkerClick(v, item ?: return)
    }

    fun onBind(item: WorkerItem) {
        this.item = item
        binding.textViewClass.text = item.className
        binding.textViewName.text = item.name
        binding.imageViewStatus.setImageResource(item.stateIcon)
        binding.imageViewStatus.contentDescription = itemView.context.getString(item.stateTitle)
        val icon = if (item.isPeriodic) R.drawable.wi_ic_repeat else 0
        binding.textViewName.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0)
        binding.textViewTime.text = DateUtils.getRelativeTimeSpanString(item.lastEnqueueTime)

        binding.imageViewConstraintIdle.isActivated = WorkConstraint.IDLE in item.constraints
        binding.imageViewConstraintBattery.isActivated = WorkConstraint.BATTERY in item.constraints
        binding.imageViewConstraintStorage.isActivated = WorkConstraint.STORAGE in item.constraints
        binding.imageViewConstraintCharging.isActivated =
            WorkConstraint.CHARGING in item.constraints
        binding.imageViewConstraintNetwork.isActivated =
            WorkConstraint.NETWORK_METERED in item.constraints
        binding.imageViewConstraintNetworkUnmetered.isActivated =
            WorkConstraint.NETWORK_UNMETERED in item.constraints
    }
}
