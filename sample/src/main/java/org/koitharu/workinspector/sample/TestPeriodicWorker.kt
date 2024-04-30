package org.koitharu.workinspector.sample

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class TestPeriodicWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Thread.sleep(Random.nextLong(1000, 5000))
        return if (Random.nextBoolean()) {
            Result.success()
        } else {
            Result.failure()
        }
    }
}
