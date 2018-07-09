package com.dev925.sleeptracker.features.edit.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluefletch.sectionedrecyclerview.BaseViewHolder
import com.dev925.sleeptracker.R
import com.dev925.sleeptracker.db.JournalState
import com.dev925.sleeptracker.db.util.toString
import com.dev925.sleeptracker.features.edit.util.DoubleEditEntrySelectionResult
import com.dev925.sleeptracker.features.edit.util.EditEntrySelectionResult
import com.dev925.sleeptracker.util.SelectionResultListener
import kotlinx.android.synthetic.main.row_combined_date.view.*

/**
 * Created by robertgross on 4/16/18.
 */

class CombinedDateViewHolder(view: View) : BaseViewHolder<DoubleEditEntrySelectionResult>(view) {

    companion object Factory {
        fun newInstance(parent: ViewGroup): CombinedDateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_combined_date, parent, false)
            return CombinedDateViewHolder(view)
        }
    }

    private lateinit var listener: SelectionResultListener<EditEntrySelectionResult>

    override fun bind(data: DoubleEditEntrySelectionResult) {
        _data = data
        itemView.combinedTimeText.setText(_data.dateStart.toString("hh:mm a"))
        itemView.combinedTimeText2.setText(_data.dateStart.toString("hh:mm a"))

        itemView.combinedTimeText.setOnClickListener {
            val state = if(_data.state == JournalState.NAPSTART) JournalState.NAPSTART else JournalState.DEVICEON
            listener.onSelection(EditEntrySelectionResult(state, _data.dateStart))
        }

        itemView.combinedTimeText2.setOnClickListener {
            val state = if(_data.state == JournalState.NAPSTART) JournalState.NAPEND else JournalState.DEVICEOFF
            listener.onSelection(EditEntrySelectionResult(state, _data.dateEnd))
        }
    }

    fun setSelectionResultListener(selectionResultListener : SelectionResultListener<EditEntrySelectionResult>) {
        listener = selectionResultListener
    }

    //Not Used in Kotlin
    override fun bindViews(`object`: Any?, view: View?) {

    }
}