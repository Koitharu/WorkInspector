package org.koitharu.kotatsu.core.ui.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup

internal class ChipsView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = com.google.android.material.R.attr.chipGroupStyle,
    ) : ChipGroup(context, attrs, defStyleAttr) {
        private var isLayoutSuppressedCompat = false
        private var isLayoutCalledOnSuppressed = false
        private val chipStyle = com.google.android.material.R.style.Widget_Material3_Chip_Assist

        init {
            if (isInEditMode) {
                setChips(
                    List(5) {
                        ChipModel("Chip $it", 0)
                    },
                )
            }
        }

        override fun requestLayout() {
            if (isLayoutSuppressedCompat) {
                isLayoutCalledOnSuppressed = true
            } else {
                super.requestLayout()
            }
        }

        fun setChips(items: Collection<ChipModel>) {
            suppressLayoutCompat(true)
            try {
                for ((i, model) in items.withIndex()) {
                    val chip = getChildAt(i) as Chip? ?: addChip()
                    bindChip(chip, model)
                }
                if (childCount > items.size) {
                    removeViews(items.size, childCount - items.size)
                }
            } finally {
                suppressLayoutCompat(false)
            }
        }

        private fun bindChip(
            chip: Chip,
            model: ChipModel,
        ) {
            chip.text = model.title
            chip.isClickable = false
            if (model.icon == 0) {
                chip.chipIcon = null
                chip.isChipIconVisible = false
            } else {
                chip.setChipIconResource(model.icon)
                chip.isChipIconVisible = true
            }
        }

        private fun addChip(): Chip {
            val chip = Chip(context)
            val drawable = ChipDrawable.createFromAttributes(context, null, 0, chipStyle)
            chip.setChipDrawable(drawable)
            chip.isCheckedIconVisible = true
            chip.isChipIconVisible = false
            chip.isCloseIconVisible = false
            chip.setEnsureMinTouchTargetSize(false)
            addView(chip)
            return chip
        }

        private fun suppressLayoutCompat(suppress: Boolean) {
            isLayoutSuppressedCompat = suppress
            if (!suppress) {
                if (isLayoutCalledOnSuppressed) {
                    requestLayout()
                    isLayoutCalledOnSuppressed = false
                }
            }
        }

        data class ChipModel(
            val title: CharSequence,
            @DrawableRes val icon: Int,
        )
    }
