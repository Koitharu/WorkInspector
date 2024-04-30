package org.koitharu.workinspector.data.workers

import androidx.room.withTransaction
import androidx.work.WorkInfo
import androidx.work.impl.WorkManagerImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Singleton
import org.koitharu.workinspector.data.util.WorkTables.WORK_NAME
import org.koitharu.workinspector.data.util.WorkTables.WORK_SPEC
import org.koitharu.workinspector.data.util.observeAsFlow
import org.koitharu.workinspector.data.workers.model.WorkerInfo
import java.util.ArrayList

@Singleton
internal class WorkersRepository(
    private val workManager: WorkManagerImpl,
) {
    private val db = workManager.workDatabase

    suspend fun getWorkers(): List<WorkerInfo> =
        db.withTransaction {
            db.openHelper.readableDatabase.query(
                "SELECT id, name, worker_class_name, state FROM $WORK_NAME LEFT JOIN $WORK_SPEC ON $WORK_NAME.work_spec_id = $WORK_SPEC.id",
            ).use { cursor ->
                val result = ArrayList<WorkerInfo>(cursor.count)
                result.add(
                    WorkerInfo(
                        id = cursor.getString(0),
                        name = cursor.getString(1),
                        workerClassName = cursor.getString(2),
                        state = WorkInfo.State.entries.getOrNull(cursor.getInt(3)),
                    ),
                )
                result
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
