package org.koitharu.workinspector.data.workers

import androidx.room.withTransaction
import androidx.work.NetworkType
import androidx.work.WorkInfo
import androidx.work.impl.WorkDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Singleton
import org.koitharu.workinspector.data.util.WorkTables.WORK_SPEC
import org.koitharu.workinspector.data.util.getBoolean
import org.koitharu.workinspector.data.util.mapAndClose
import org.koitharu.workinspector.data.util.observeAsFlow
import org.koitharu.workinspector.data.workers.model.WorkerInfo

@Singleton
internal class WorkersRepository(
    private val db: WorkDatabase,
) {
    suspend fun getWorkers(): List<WorkerInfo> =
        db.withTransaction {
            val workers =
                db.openHelper.readableDatabase.query(
                    "SELECT DISTINCT worker_class_name FROM WorkSpec ORDER BY last_enqueue_time",
                ).mapAndClose { it.getString(0) }
            workers.map { className ->
                db.openHelper.readableDatabase.query(
                    "SELECT state, interval_duration, required_network_type, " +
                        "requires_battery_not_low, requires_charging, requires_device_idle, " +
                        "requires_storage_not_low, last_enqueue_time FROM $WORK_SPEC " +
                        "WHERE worker_class_name = ? ORDER BY last_enqueue_time DESC LIMIT 1",
                    arrayOf(className),
                ).use { cursor ->
                    cursor.moveToFirst()
                    WorkerInfo(
                        state = WorkInfo.State.entries.getOrNull(cursor.getInt(0)),
                        workerClassName = className,
                        isPeriodic = cursor.getInt(1) != 0,
                        requiredNetworkType = NetworkType.entries.getOrNull(cursor.getInt(2)),
                        requiresBatteryNotLow = cursor.getBoolean(3),
                        requiresCharging = cursor.getBoolean(4),
                        requiresDeviceIdle = cursor.getBoolean(5),
                        requiresStorageNotLow = cursor.getBoolean(6),
                        lastEnqueueTime = cursor.getLong(7),
                    )
                }
            }
        }

    fun observeWorkers(): Flow<List<WorkerInfo>> {
        return db.invalidationTracker.observeAsFlow(
            arrayOf(WORK_SPEC),
        ).mapLatest {
            getWorkers()
        }
    }
}
