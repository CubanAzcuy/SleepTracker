package com.dev925.sleeptracker.features.edit.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluefletch.sectionedrecyclerview.BaseViewHolder
import com.dev925.sleeptracker.R
import com.dev925.sleeptracker.db.JournalState
import com.dev925.sleeptracker.db.util.toString
import com.dev925.sleeptracker.features.edit.util.EditEntrySelectionResult
import com.dev925.sleeptracker.util.SelectionResultListener
import kotlinx.android.synthetic.main.row_single_date.view.*

/**
 * Created by robertgross on 4/16/18.
 */

class SingleDateViewHolder(view: View) : BaseViewHolder<EditEntrySelectionResult>(view) {

    companion object Factory {
        fun newInstance(parent: ViewGroup): SingleDateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_single_date, parent, false)
            return SingleDateViewHolder(view)
        }
    }

    override fun bind(data: EditEntrySelectionResult) {
        _data = data
        itemView.timeText.setText(_data.date.toString("hh:mm a"))
        when(_data.state) {

            JournalState.AWOKEN -> itemView.singleTimeLabel.setText("Woke Up:")
            JournalState.DAYLIGHTSON -> itemView.singleTimeLabel.setText("Woke Up and Lights On:")
            JournalState.DAYOUTOFBED -> itemView.singleTimeLabel.setText("Woke Up and Out Of Bed:")
            JournalState.BEDTIME -> itemView.singleTimeLabel.setText("Got in Bed:")
            JournalState.NIGHTLIGHTSOUT -> itemView.singleTimeLabel.setText("Got in Bed and Turned Lights Out:")
            else -> {
                itemView.singleTimeLabel.setText("Somethings Wrong:")
            }
        }
    }

    fun selectedRow(selectionResultListener: SelectionResultListener<EditEntrySelectionResult>) {
        selectionResultListener.onSelection(_data)
    }

    //Not Used in Kotlin
    override fun bindViews(`object`: Any?, view: View?) {

    }
}