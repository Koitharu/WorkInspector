package org.koitharu.workinspector.ui.details

import android.os.Build
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.work.NetworkType.CONNECTED
import androidx.work.NetworkType.METERED
import androidx.work.NetworkType.NOT_ROAMING
import androidx.work.NetworkType.TEMPORARILY_UNMETERED
import androidx.work.NetworkType.UNMETERED
import org.koitharu.workinspector.R
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
        binding.textViewAttempt.text =
            itemView.context.getString(
                R.string.attempt_count,
                item.runAttemptCount,
            )

        binding.imageViewConstraintIdle.isActivated = item.requiresDeviceIdle
        binding.imageViewConstraintBattery.isActivated = item.requiresBatteryNotLow
        binding.imageViewConstraintStorage.isActivated = item.requiresStorageNotLow
        binding.imageViewConstraintCharging.isActivated = item.requiresCharging
        binding.imageViewConstraintNetwork.isActivated =
            item.requiredNetworkType.let {
                it == CONNECTED || it == METERED || it == NOT_ROAMING
            }
        binding.imageViewConstraintNetworkUnmetered.isActivated =
            item.requiredNetworkType.let {
                it == UNMETERED || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && it == TEMPORARILY_UNMETERED)
            }
    }
}
