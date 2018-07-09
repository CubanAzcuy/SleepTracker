package com.dev925.sleeptracker.features.edit.sections

import com.bluefletch.sectionedrecyclerview.ChangeRequest
import com.bluefletch.sectionedrecyclerview.ChangeType
import com.bluefletch.sectionedrecyclerview.TypedItem
import com.bluefletch.sectionedrecyclerview.UpdatableSection
import com.dev925.sleeptracker.db.JournalState
import com.dev925.sleeptracker.db.models.NapEntry
import com.dev925.sleeptracker.features.edit.util.DoubleEditEntrySelectionResult
import com.dev925.sleeptracker.features.edit.util.RowTypes
import java.util.*

/**
 * Created by robertgross on 4/16/18.
 */
class NapSection(napEntry: List<NapEntry>) : UpdatableSection() {

    init {
        mHeader = TypedItem(RowTypes.HEADER.ordinal, "Naps")
        napEntry.forEach {
            addItem(DoubleEditEntrySelectionResult(JournalState.NAPSTART, it.napStart, it.napStop))
        }
    }

    fun addItem(editEntrySelectionResult: DoubleEditEntrySelectionResult) {
        mItems.add(TypedItem(RowTypes.COMBINED.ordinal, editEntrySelectionResult))
    }

    fun updateSection() {
        if (updateSectionObservable != null) {
            val changeRequest = ChangeRequest(this, ChangeType.CHANGE, 0, mItems.size - 1)
            val list = ArrayList<ChangeRequest>(1)
            list.add(changeRequest)
            updateSectionObservable.call(list)
        }
    }
}