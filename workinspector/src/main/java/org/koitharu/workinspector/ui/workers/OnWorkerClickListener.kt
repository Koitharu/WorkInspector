package org.koitharu.workinspector.ui.workers

import android.view.View
import org.koitharu.workinspector.data.workers.model.WorkerInfo

internal interface OnWorkerClickListener {
    fun onWorkerClick(
        view: View,
        worker: WorkerInfo,
    )
}
