package com.dev925.sleeptracker.features.edit.sections

import com.bluefletch.sectionedrecyclerview.ChangeRequest
import com.bluefletch.sectionedrecyclerview.ChangeType
import com.bluefletch.sectionedrecyclerview.TypedItem
import com.bluefletch.sectionedrecyclerview.UpdatableSection
import com.dev925.sleeptracker.db.JournalState
import com.dev925.sleeptracker.db.models.SleepEntry
import com.dev925.sleeptracker.features.edit.util.EditEntrySelectionResult
import com.dev925.sleeptracker.features.edit.util.RowTypes
import java.util.*

/**
 * Created by robertgross on 4/16/18.
 */

class SleepEntrySection(sleepEntry: SleepEntry) : UpdatableSection() {

    init {
        mHeader = TypedItem(RowTypes.HEADER.ordinal, "Sleep Data")
        addItem(EditEntrySelectionResult(JournalState.AWOKEN, sleepEntry.timeAwoken))
        addItem(EditEntrySelectionResult(JournalState.DAYLIGHTSON, sleepEntry.timeAwokenLightOn))
        addItem(EditEntrySelectionResult(JournalState.DAYOUTOFBED, sleepEntry.timeAwokenOutOfBed))
        addItem(EditEntrySelectionResult(JournalState.BEDTIME, sleepEntry.bedTime))
        addItem(EditEntrySelectionResult(JournalState.DAYOUTOFBED, sleepEntry.bedTimeLightsOut))
    }

    fun addItem(editEntrySelectionResult: EditEntrySelectionResult) {
        mItems.add(TypedItem(RowTypes.SINGLE.ordinal, editEntrySelectionResult))
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