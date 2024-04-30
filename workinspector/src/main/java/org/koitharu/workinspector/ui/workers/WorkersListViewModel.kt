package org.koitharu.workinspector.ui.workers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import org.koin.android.annotation.KoinViewModel
import org.koitharu.workinspector.data.workers.WorkersRepository

@KoinViewModel
internal class WorkersListViewModel(
    private val repository: WorkersRepository,
) : ViewModel() {
    val onError = MutableSharedFlow<Throwable>()
    val workers =
        repository.observeWorkers()
            .catch { e ->
                onError.emit(e)
            }.stateIn(
                scope = viewModelScope + Dispatchers.Default,
                started = SharingStarted.Eagerly,
                initialValue = emptyList(),
            )
}
