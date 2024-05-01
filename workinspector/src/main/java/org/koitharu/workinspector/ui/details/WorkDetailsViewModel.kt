package org.koitharu.workinspector.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import org.koin.android.annotation.KoinViewModel
import org.koitharu.workinspector.data.details.WorkerDetailsRepository
import org.koitharu.workinspector.ui.details.WorkDetailsFragment.Companion.ARG_WORKER_CLASS_NAME
import org.koitharu.workinspector.ui.util.mapItems
import org.koitharu.workinspector.ui.util.toWorkDetailsItem

@KoinViewModel
internal class WorkDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: WorkerDetailsRepository,
) : ViewModel() {
    val workerClassName = requireNotNull(savedStateHandle.get<String>(ARG_WORKER_CLASS_NAME))
    val onError = MutableSharedFlow<Throwable>()
    val workers =
        repository.observeWorkDetails(workerClassName)
            .mapItems { it.toWorkDetailsItem() }
            .catch { e ->
                onError.emit(e)
            }.stateIn(
                scope = viewModelScope + Dispatchers.Default,
                started = SharingStarted.Eagerly,
                initialValue = emptyList(),
            )
}
