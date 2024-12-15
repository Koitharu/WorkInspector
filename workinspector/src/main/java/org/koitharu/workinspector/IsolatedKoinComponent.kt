package org.koitharu.workinspector

import org.koin.core.Koin
import org.koin.core.component.KoinComponent

internal interface IsolatedKoinComponent : KoinComponent {

    override fun getKoin(): Koin = WorkInspectorInitProvider.koin
}