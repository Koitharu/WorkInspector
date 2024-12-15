package org.koitharu.workinspector

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.work.impl.WorkDatabase
import androidx.work.impl.WorkManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.ksp.generated.module

public class WorkInspectorInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        koinApp = koinApplication {
            androidLogger()
            androidContext(checkNotNull(context))
            modules(coreModule(), LibModule().module)
        }
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

    private fun coreModule() =
        module {
            factory<WorkManagerImpl> { WorkManagerImpl.getInstance(androidContext()) }
            factory<WorkDatabase> { get<WorkManagerImpl>().workDatabase }
        }

    internal companion object {

        lateinit var koinApp: KoinApplication
            private set

        val koin get() = koinApp.koin
    }
}
