package org.koitharu.workinspector.ui.details

import android.graphics.Typeface.BOLD
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.koitharu.workinspector.R
import org.koitharu.workinspector.databinding.WiItemWorkDetailsBinding
import org.koitharu.workinspector.ui.WorkConstraint
import org.koitharu.workinspector.ui.util.DurationFormat

internal class WorkDetailsHolder(
    private val binding: WiItemWorkDetailsBinding,
) : ViewHolder(binding.root) {
    fun onBind(item: WorkDetailsItem) {
        val context = itemView.context
        binding.textViewId.text = item.id
        binding.imageViewStatus.setImageResource(item.stateIcon)
        binding.imageViewStatus.contentDescription = context.getString(item.stateTitle)
        binding.textViewStatus.setText(item.stateTitle)
        binding.textViewAttempt.text =
            if (item.periodicInterval == 0L) {
                context.getString(R.string.wi_attempt_count, item.runAttemptCount)
            } else {
                context.getString(R.string.wi_period_count, item.periodCount)
            }
        binding.chipsTags.setChips(item.tags)
        binding.scrollViewTags.isGone = item.tags.isEmpty()

        binding.textViewDetails.text =
            buildSpannedString {
                title { appendLine(context.getString(R.string.wi_enqueued_at)) }
                append('\t')
                append(
                    DateUtils.formatDateTime(
                        context,
                        item.lastEnqueueTime,
                        DateUtils.FORMAT_SHOW_TIME,
                    ),
                )
                append(" (")
                append(DateUtils.getRelativeTimeSpanString(item.lastEnqueueTime))
                append(')').appendLine()
                if (item.periodicInterval != 0L) {
                    title { appendLine(context.getString(R.string.wi_repeat_interval)) }
                    append('\t').append(
                        DurationFormat(context).format(
                            item.periodicInterval,
                            DurationFormat.Unit.SECOND,
                        ),
                    )
                    appendLine()
                }
                if (item.stopReasonText != 0) {
                    title { appendLine(context.getString(R.string.wi_stop_reason)) }
                    append('\t').appendLine(context.getString(item.stopReasonText))
                }
                if (item.inputData.isNotEmpty()) {
                    title { appendLine(context.getString(R.string.wi_input_data)) }
                    item.inputData.forEach { (k, v) ->
                        append('\t').append(k).append(": ").append(v.toString())
                        appendLine()
                    }
                }
                if (item.outputData.isNotEmpty()) {
                    title { appendLine(context.getString(R.string.wi_output_data)) }
                    item.inputData.forEach { (k, v) ->
                        append('\t').append(k).append(": ").append(v.toString())
                        appendLine()
                    }
                }
            }

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

    private inline fun SpannableStringBuilder.title(builderAction: SpannableStringBuilder.() -> Unit): SpannableStringBuilder =
        inSpans(RelativeSizeSpan(1.1f), StyleSpan(BOLD), builderAction = builderAction)
}
