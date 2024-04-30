package org.koitharu.workinspector.data.workers

import androidx.core.database.getStringOrNull
import androidx.room.withTransaction
import androidx.work.WorkInfo
import androidx.work.impl.WorkDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Singleton
import org.koitharu.workinspector.data.util.WorkTables.WORK_NAME
import org.koitharu.workinspector.data.util.WorkTables.WORK_SPEC
import org.koitharu.workinspector.data.util.mapAndClose
import org.koitharu.workinspector.data.util.observeAsFlow
import org.koitharu.workinspector.data.workers.model.WorkerInfo

@Singleton
internal class WorkersRepository(
    private val db: WorkDatabase,
) {
    suspend fun getWorkers(): List<WorkerInfo> =
        db.withTransaction {
            db.openHelper.readableDatabase.query(
                "SELECT id, state, worker_class_name, interval_duration, (SELECT name FROM $WORK_NAME WHERE $WORK_NAME.work_spec_id = $WORK_SPEC.id) AS name FROM $WORK_SPEC GROUP BY worker_class_name ORDER BY last_enqueue_time DESC",
            ).mapAndClose { cursor ->
                WorkerInfo(
                    id = cursor.getString(0),
                    state = WorkInfo.State.entries.getOrNull(cursor.getInt(1)),
                    workerClassName = cursor.getString(2),
                    isPeriodic = cursor.getInt(3) != 0,
                    name = cursor.getStringOrNull(4),
                )
            }
        }

    fun observeWorkers(): Flow<List<WorkerInfo>> {
        return db.invalidationTracker.observeAsFlow(
            arrayOf(WORK_NAME, WORK_SPEC),
        ).mapLatest {
            getWorkers()
        }
    }
}
