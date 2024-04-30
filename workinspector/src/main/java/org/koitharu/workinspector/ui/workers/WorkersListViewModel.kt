package org.koitharu.workinspector.ui.workers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import org.koin.android.annotation.KoinViewModel
import org.koitharu.workinspector.data.workers.WorkersRepository

@KoinViewModel
internal class WorkersListViewModel(
    private val repository: WorkersRepository,
) : ViewModel() {
    val workers =
        repository.observeWorkers().stateIn(
            scope = viewModelScope + Dispatchers.Default,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )
}
