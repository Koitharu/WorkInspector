package org.koitharu.workinspector.ui.workers

import android.view.View

internal interface OnWorkerClickListener {
    fun onWorkerClick(
        view: View,
        worker: WorkerItem,
    )
}
