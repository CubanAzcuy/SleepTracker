package com.dev925.sleeptracker.features.selectweek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dev925.sleeptracker.R
import com.dev925.sleeptracker.SleepApp
import com.dev925.sleeptracker.db.fetchDeviceStatuses
import com.dev925.sleeptracker.db.fetchNaps
import com.dev925.sleeptracker.db.fetchSleepEntry
import com.dev925.sleeptracker.features.BaseFragment
import com.dev925.sleeptracker.features.edit.EditEntryFragment
import com.dev925.sleeptracker.util.replaceFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_week.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

/**
 * Created by robertgross on 4/14/18.
 */
class WeekSelectionFragment: BaseFragment(), OnDateSelectedListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_week, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        calendarView.setOnDateChangedListener(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        launch(UI) {
            date.date
            val sleepEntry = SleepApp.Companion.fetchSleepEntry(date.date).await()
            if(sleepEntry != null) {
                val napEntries = SleepApp.Companion.fetchNaps(sleepEntry).await()
                val deviceStatusEntries = SleepApp.Companion.fetchDeviceStatuses(sleepEntry).await()

                val editEntry = EditEntryFragment()
                editEntry.sleepEntry = sleepEntry
                editEntry.napEntries = napEntries
                editEntry.deviceEntries = deviceStatusEntries
                replaceFragment(editEntry, R.id.layout)
            } else {
                val toast = Toast.makeText(activity, "No Entry Found", Toast.LENGTH_LONG)
                toast.show()
            }

        }
    }
}
