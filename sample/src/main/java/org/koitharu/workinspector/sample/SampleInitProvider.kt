package org.koitharu.workinspector.sample

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class SampleInitProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        val configuration = Configuration.Builder().build()
        WorkManager.initialize(checkNotNull(context), configuration)
        val workManager = WorkManager.getInstance(checkNotNull(context))
        val periodicReqest =
            PeriodicWorkRequestBuilder<TestPeriodicWorker>(20, TimeUnit.MINUTES)
                .setInputData(workDataOf("inputString" to "test", "inputInt" to Random.nextInt()))
                .setConstraints(
                    Constraints.Builder().setRequiresCharging(true).setRequiresStorageNotLow(true)
                        .build(),
                )
                .addTag("TAG1")
                .addTag("Tag2_sample")
                .keepResultsForAtLeast(20, TimeUnit.DAYS)
                .build()
        workManager.enqueueUniquePeriodicWork(
            "uniquePeriodicWorkName",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicReqest,
        )

        val oneTimeRequest =
            OneTimeWorkRequestBuilder<TestSingleWorker>()
                .setInitialDelay(1, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.UNMETERED)
                        .build(),
                ).keepResultsForAtLeast(20, TimeUnit.DAYS)
                .build()
        workManager.enqueue(oneTimeRequest)
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(
        uri: Uri,
        values: ContentValues?,
    ): Uri? = null

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int = 0
}
