package org.koitharu.workinspector.ui.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal fun <T> Flow<T>.collectInLifecycle(
    lifecycleOwner: LifecycleOwner,
    collector: FlowCollector<T>,
): Job =
    lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect(collector)
    }

internal inline fun <T, R> Flow<List<T>>.mapItems(crossinline mapper: (T) -> R): Flow<List<R>> =
    map {
        it.map(mapper)
    }
