package org.koitharu.workinspector.data.util

import android.database.Cursor
import androidx.room.InvalidationTracker
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

internal fun InvalidationTracker.observeAsFlow(tables: Array<String>): Flow<Set<String>> =
    callbackFlow {
        val observer = FlowInvalidationObserver(tables, this)
        addObserver(observer)
        awaitClose {
            removeObserver(observer)
        }
    }.onStart { emit(emptySet()) }

private class FlowInvalidationObserver(
    tables: Array<String>,
    private val producerScope: ProducerScope<Set<String>>,
) : InvalidationTracker.Observer(tables) {
    override fun onInvalidated(tables: Set<String>) {
        producerScope.trySendBlocking(tables)
    }
}

internal inline fun Cursor.forEachRow(block: (Cursor) -> Unit) {
    if (moveToFirst()) {
        do {
            block(this)
        } while (moveToNext())
    }
}

internal inline fun <T> Cursor.mapAndClose(mapper: (Cursor) -> T): List<T> =
    use {
        val result = ArrayList<T>(count)
        forEachRow {
            result.add(mapper(it))
        }
        result
    }
