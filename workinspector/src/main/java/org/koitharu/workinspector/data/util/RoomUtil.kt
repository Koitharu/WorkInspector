package org.koitharu.workinspector.data.util

import androidx.room.InvalidationTracker
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal fun InvalidationTracker.observeAsFlow(tables: Array<String>): Flow<Set<String>> =
    callbackFlow {
        val observer = FlowInvalidationObserver(tables, this)
        addObserver(observer)
        awaitClose {
            removeObserver(observer)
        }
    }

private class FlowInvalidationObserver(
    tables: Array<String>,
    private val producerScope: ProducerScope<Set<String>>,
) : InvalidationTracker.Observer(tables) {
    override fun onInvalidated(tables: Set<String>) {
        producerScope.trySendBlocking(tables)
    }
}
