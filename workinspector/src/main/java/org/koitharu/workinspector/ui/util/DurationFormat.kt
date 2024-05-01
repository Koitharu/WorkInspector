package org.koitharu.workinspector.ui.util

import android.content.Context
import org.koitharu.workinspector.R
import java.text.NumberFormat
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * https://gist.github.com/Jeehut/78534c27b24d78f14a3cbd3eebead861
 */
internal class DurationFormat(private val context: Context) {
    enum class Unit {
        DAY,
        HOUR,
        MINUTE,
        SECOND,
        MILLISECOND,
    }

    fun format(
        durationMillis: Long,
        smallestUnit: Unit = Unit.SECOND,
    ): String {
        var formattedStringComponents = mutableListOf<String>()
        var remainder = durationMillis.milliseconds

        for (unit in Unit.entries) {
            val component = calculateComponent(unit, remainder)

            remainder =
                when (unit) {
                    Unit.DAY -> remainder - component.days
                    Unit.HOUR -> remainder - component.hours
                    Unit.MINUTE -> remainder - component.minutes
                    Unit.SECOND -> remainder - component.seconds
                    Unit.MILLISECOND -> remainder - component.milliseconds
                }

            if (component > 0) {
                formattedStringComponents.add(formatUnit(unit, component.toInt()))
            }

            if (unit == smallestUnit) {
                if (formattedStringComponents.isEmpty()) {
                    formattedStringComponents.add(
                        formatUnit(smallestUnit, 0),
                    )
                }
                break
            }
        }

        return formattedStringComponents.joinToString(" ")
    }

    private fun calculateComponent(
        unit: Unit,
        remainder: Duration,
    ) = when (unit) {
        Unit.DAY -> remainder.inWholeDays.toLong()
        Unit.HOUR -> remainder.inWholeHours.toLong()
        Unit.MINUTE -> remainder.inWholeMinutes.toLong()
        Unit.SECOND -> remainder.inWholeSeconds.toLong()
        Unit.MILLISECOND -> remainder.inWholeMilliseconds.toLong()
    }

    private fun formatUnit(
        unit: Unit,
        amount: Int,
    ): String {
        val plural =
            when (unit) {
                Unit.DAY -> R.plurals.days
                Unit.HOUR -> R.plurals.hours
                Unit.MINUTE -> R.plurals.minutes
                Unit.SECOND -> R.plurals.seconds
                Unit.MILLISECOND -> R.plurals.millis
            }
        return context.resources.getQuantityString(
            plural,
            amount,
            NumberFormat.getInstance().format(amount),
        )
    }
}
