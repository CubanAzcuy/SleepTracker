package com.dev925.sleeptracker.features.edit

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluefletch.sectionedrecyclerview.SectionRecyclerViewAdapter
import com.dev925.sleeptracker.R
import com.dev925.sleeptracker.db.JournalState
import com.dev925.sleeptracker.db.models.DeviceStatusEntry
import com.dev925.sleeptracker.db.models.NapEntry
import com.dev925.sleeptracker.db.models.SleepEntry
import com.dev925.sleeptracker.features.BaseFragment
import com.dev925.sleeptracker.features.edit.sections.DeviceStatusSection
import com.dev925.sleeptracker.features.edit.sections.NapSection
import com.dev925.sleeptracker.features.edit.sections.SleepEntrySection
import com.dev925.sleeptracker.features.edit.util.EditEntrySelectionResult
import com.dev925.sleeptracker.util.SelectionResultListener
import kotlinx.android.synthetic.main.fragment_recycler.*

/**
 * Created by robertgross on 4/14/18.
 */

class EditEntryFragment : BaseFragment(), SelectionResultListener<EditEntrySelectionResult> {

    private lateinit var adapter: SectionRecyclerViewAdapter
    lateinit var sleepEntry: SleepEntry
    lateinit var napEntries: List<NapEntry>
    lateinit var deviceEntries: List<DeviceStatusEntry>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        adapter = EditEntrySectionRecyclerViewAdapter(this)
        recyclerView.adapter = adapter

        adapter.addSection(SleepEntrySection(sleepEntry))
        adapter.addSection(DeviceStatusSection(deviceEntries))
        adapter.addSection(NapSection(napEntries))
    }

    override fun onSelection(selectionResult: EditEntrySelectionResult) {
        when(selectionResult.state) {
            JournalState.AWOKEN -> {
                
            }
            JournalState.DAYLIGHTSON -> {
                
            }
            JournalState.DAYOUTOFBED -> {
                
            }
            JournalState.DEVICEON -> {
                
            }
            JournalState.DEVICEOFF -> {
                
            }
            JournalState.BEDTIME -> {
                
            }
            JournalState.NIGHTLIGHTSOUT -> {
                
            }
            JournalState.NAPSTART -> {
                
            }
            JournalState.NAPEND -> {
                
            }
            JournalState.SLEEPING -> {
                
            }
        }
    }

}