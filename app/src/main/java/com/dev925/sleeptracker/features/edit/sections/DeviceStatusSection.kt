package com.dev925.sleeptracker.features.edit.sections

import com.bluefletch.sectionedrecyclerview.ChangeRequest
import com.bluefletch.sectionedrecyclerview.ChangeType
import com.bluefletch.sectionedrecyclerview.TypedItem
import com.bluefletch.sectionedrecyclerview.UpdatableSection
import com.dev925.sleeptracker.db.JournalState
import com.dev925.sleeptracker.db.models.DeviceStatusEntry
import com.dev925.sleeptracker.features.edit.util.DoubleEditEntrySelectionResult
import com.dev925.sleeptracker.features.edit.util.RowTypes
import java.util.*

/**
 * Created by robertgross on 4/16/18.
 */
class DeviceStatusSection(deviceEntry: List<DeviceStatusEntry>) : UpdatableSection() {

    init {
        mHeader = TypedItem(RowTypes.HEADER.ordinal, "Device Status")
        deviceEntry.forEach {
            addItem(DoubleEditEntrySelectionResult(JournalState.DEVICEON, it.deviceOff, it.deviceOn))
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