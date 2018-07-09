package com.dev925.sleeptracker.features.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev925.daynighttoolbar.CustomToolbarView
import com.dev925.sleeptracker.R
import com.dev925.sleeptracker.SleepApp
import com.dev925.sleeptracker.db.*
import com.dev925.sleeptracker.db.models.DeviceStatusEntry
import com.dev925.sleeptracker.db.models.NapEntry
import com.dev925.sleeptracker.db.models.SleepEntry
import com.dev925.sleeptracker.features.selectweek.WeekSelectionFragment
import com.dev925.sleeptracker.util.replaceFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.*

/**
 * Created by robertgross on 4/14/18.
 */
class HomeScreenFragment: Fragment(), CustomToolbarView.CustomListener {

    private lateinit var sleepEntry: SleepEntry
    private var deviceStatusEntry: DeviceStatusEntry? = null
    private var napEntry: NapEntry? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkDBStatus()

        customView2.setCustomListener(this)
        blurringView.setBlurredView(blurableView)

        confirmButton.setOnClickListener {
            val state = SleepApp.getState(sleepEntry, deviceStatusEntry)
            updateData(state)
        }

        editDayButton.setOnClickListener {
            val fragment = WeekSelectionFragment()
            replaceFragment(fragment, R.id.layout)
        }

        deviceStatusButton.setOnClickListener {
            deviceStatusEntry = DeviceStatusEntry(sleepEntry = sleepEntry.uid, deviceOff = Date())
            deviceStatusEntry?.let { deviceStatusEntry ->
                launch(UI) {
                    SleepApp.insertDeviceStatusEntryForSleepEntity(deviceStatusEntry).await()
                    updateUI()
                }
            }
        }

        napButton.setOnClickListener {
            napEntry = NapEntry(sleepEntry = sleepEntry.uid, napStart = Date())
            napEntry?.let { nap ->
                launch(UI) {
                    SleepApp.insertNapEntryForSleepEntity(nap).await()
                    updateUI()
                }
            }
        }
    }

    private fun updateData(state: JournalState) {
        if(napEntry != null) {
            napEntry?.napStop = Date()
            if(napEntry?.napStart == null) napEntry?.napStart = Date()
            napEntry?.let { nap ->
                launch(UI) {
                    SleepApp.insertNapEntryForSleepEntity(nap).await()
                    napEntry = null
                    updateUI()
                }
            }

        } else {
            when (state) {
                JournalState.AWOKEN -> {
                    sleepEntry.timeAwoken = Date()
                    launch(UI) {
                        SleepApp.insertSleepEntry(sleepEntry).await()
                        updateUI()
                    }
                }
                JournalState.DAYLIGHTSON -> {
                    sleepEntry.timeAwokenLightOn = Date()
                    launch(UI) {
                        SleepApp.insertSleepEntry(sleepEntry).await()
                        updateUI()
                    }
                }
                JournalState.DAYOUTOFBED -> {
                    sleepEntry.timeAwokenOutOfBed = Date()
                    launch(UI) {
                        SleepApp.insertSleepEntry(sleepEntry).await()
                        updateUI()
                    }
                }
                JournalState.DEVICEOFF -> {
                    deviceStatusEntry = DeviceStatusEntry(sleepEntry = sleepEntry.uid, deviceOff = Date())
                    deviceStatusEntry?.let { deviceStatusEntry ->
                        launch(UI) {
                            SleepApp.insertDeviceStatusEntryForSleepEntity(deviceStatusEntry).await()
                            updateUI()
                        }
                    }
                }
                JournalState.DEVICEON -> {
                    deviceStatusEntry?.deviceOn = Date()
                    deviceStatusEntry?.let { deviceStatusEntry ->
                        launch(UI) {
                            SleepApp.insertDeviceStatusEntryForSleepEntity(deviceStatusEntry).await()
                            updateUI()
                        }
                    }
                }
                JournalState.BEDTIME -> {
                    sleepEntry.bedTime = Date()
                    launch(UI) {
                        SleepApp.insertSleepEntry(sleepEntry).await()
                        updateUI()
                    }
                }
                JournalState.NIGHTLIGHTSOUT -> {
                    sleepEntry.bedTimeLightsOut = Date()
                    launch(UI) {
                        SleepApp.insertSleepEntry(sleepEntry).await()
                        updateUI()
                    }
                }
            }
        }
    }

    private fun checkDBStatus() {
        launch(UI) {
            var result = SleepApp.fetchSleepEntry(Date()).await()

            if (result == null) {
                result = SleepEntry(entryDate = Date())
                SleepApp.insertSleepEntry(result)
                setState(result, null, null)
            } else {
                setState(result, SleepApp.fetchDeviceStatus(result).await(), SleepApp.fetchNap(result).await())
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customView2.setShouldRound(false)
    }

    private fun setState(result: SleepEntry, deviceStatus: DeviceStatusEntry?, nap: NapEntry?) {
        sleepEntry = result
        deviceStatusEntry = deviceStatus
        napEntry = nap
        updateUI()
    }

    private fun updateUI() {
        if(napEntry != null && (napEntry?.napStart == null || napEntry?.napStop == null)) {
            title.text = "Did you have a good nap?"
        } else {
            val state = SleepApp.getState(sleepEntry, deviceStatusEntry)
            confirmButton.visibility = View.VISIBLE
            multipleActionsFAB.collapse()
            when (state) {
                JournalState.AWOKEN -> {
                    title.text = "Did you just wake up?"
                }
                JournalState.DAYLIGHTSON -> {
                    title.text = "Have you turned on the lights?"
                }
                JournalState.DAYOUTOFBED -> {
                    title.text = "Did you get out of Bed Yet?"
                }
                JournalState.DEVICEOFF -> {
                    title.text = "Are you about to take a shower?"
                }
                JournalState.DEVICEON -> {
                    title.text = "Are you out of the shower?"
                }
                JournalState.BEDTIME -> {
                    title.text = "Ready for Bed?"
                }
                JournalState.NIGHTLIGHTSOUT -> {
                    title.text = "Lights out?."
                }
                JournalState.SLEEPING -> {
                    title.text = "Night, Night"
                    confirmButton.visibility = View.GONE
                }
            }
        }
    }

    override fun onValuesUpdated(isNight: Boolean, timeScale: Float) {
        blurringView.invalidate()
    }
}