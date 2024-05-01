package org.koitharu.workinspector.ui.details

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.koitharu.kotatsu.core.ui.widgets.ChipsView.ChipModel
import org.koitharu.workinspector.ui.WorkConstraint

internal data class WorkDetailsItem(
    val id: String,
    @DrawableRes val stateIcon: Int,
    @StringRes val stateTitle: Int,
    val runAttemptCount: Int,
    val periodCount: Int,
    val constraints: Set<WorkConstraint>,
    val tags: List<ChipModel>,
    val lastEnqueueTime: Long,
    val periodicInterval: Long,
    @StringRes val stopReasonText: Int,
    val inputData: Map<String, Any>,
    val outputData: Map<String, Any>,
)
