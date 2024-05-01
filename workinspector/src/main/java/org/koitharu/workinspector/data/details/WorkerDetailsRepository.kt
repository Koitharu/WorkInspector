package org.koitharu.workinspector.data.details

import androidx.room.withTransaction
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.WorkInfo
import androidx.work.impl.WorkDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Singleton
import org.koitharu.workinspector.data.details.model.WorkDetails
import org.koitharu.workinspector.data.util.WorkTables.WORK_SPEC
import org.koitharu.workinspector.data.util.WorkTables.WORK_TAG
import org.koitharu.workinspector.data.util.getBoolean
import org.koitharu.workinspector.data.util.mapAndClose
import org.koitharu.workinspector.data.util.observeAsFlow

@Singleton
internal class WorkerDetailsRepository(
    private val db: WorkDatabase,
) {
    suspend fun getWorkDetails(workerClassName: String): List<WorkDetails> =
        db.withTransaction {
            db.openHelper.readableDatabase.query(
                "SELECT id, state, input, output, initial_delay, interval_duration, period_count, " +
                    "run_attempt_count, last_enqueue_time, schedule_requested_at, stop_reason, " +
                    "required_network_type, requires_battery_not_low, requires_charging, " +
                    "requires_device_idle, requires_storage_not_low, (SELECT GROUP_CONCAT(tag) " +
                    "FROM $WORK_TAG WHERE work_spec_id = id) AS tags, worker_class_name " +
                    "FROM $WORK_SPEC WHERE worker_class_name = ? ORDER BY last_enqueue_time DESC",
                arrayOf(workerClassName),
            ).mapAndClose { cursor ->
                WorkDetails(
                    id = cursor.getString(0),
                    state = WorkInfo.State.entries.getOrNull(cursor.getInt(1)),
                    input = Data.fromByteArray(cursor.getBlob(2)),
                    output = Data.fromByteArray(cursor.getBlob(3)),
                    initialDelay = cursor.getLong(4),
                    intervalDuration = cursor.getLong(5),
                    periodCount = cursor.getInt(6),
                    runAttemptCount = cursor.getInt(7),
                    lastEnqueueTime = cursor.getLong(8),
                    scheduleRequestedAt = cursor.getLong(9),
                    stopReason = cursor.getInt(10),
                    requiredNetworkType = NetworkType.entries.getOrNull(cursor.getInt(11)),
                    requiresBatteryNotLow = cursor.getBoolean(12),
                    requiresCharging = cursor.getBoolean(13),
                    requiresDeviceIdle = cursor.getBoolean(14),
                    requiresStorageNotLow = cursor.getBoolean(15),
                    tags = cursor.getString(16).split(',').toSet(),
                    className = cursor.getString(17),
                )
            }
        }

    fun observeWorkDetails(workerClassName: String): Flow<List<WorkDetails>> {
        return db.invalidationTracker.observeAsFlow(
            arrayOf(WORK_SPEC, WORK_TAG),
        ).mapLatest {
            getWorkDetails(workerClassName)
        }
    }
}
