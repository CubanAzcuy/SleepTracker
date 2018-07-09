package com.dev925.sleeptracker.db

import com.dev925.sleeptracker.SleepApp
import com.dev925.sleeptracker.db.models.DeviceStatusEntry
import com.dev925.sleeptracker.db.models.NapEntry
import com.dev925.sleeptracker.db.models.SleepEntry
import com.dev925.sleeptracker.db.util.atEndOfDay
import com.dev925.sleeptracker.db.util.atStartOfDay
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import java.util.*

/**
 * Created by robertgross on 4/14/18.
 */

fun SleepApp.Companion.fetchSleepEntry(date: Date): Deferred<SleepEntry?> = async(CommonPool) {
    return@async SleepApp.database.sleepEntryDoa().getEntry(date.atStartOfDay(), date.atEndOfDay())
}

fun SleepApp.Companion.insertSleepEntry(sleepEntry: SleepEntry): Deferred<SleepEntry> = async(CommonPool) {
    val uid = database.sleepEntryDoa().insert(sleepEntry)
    sleepEntry.uid = uid
    return@async sleepEntry
}

fun SleepApp.Companion.fetchNaps(sleepEntity :SleepEntry): Deferred<List<NapEntry>> = async(CommonPool) {
    return@async SleepApp.database.napEntryDoa().getEntry(sleepEntity.uid)
}

fun SleepApp.Companion.fetchNap(sleepEntity :SleepEntry): Deferred<NapEntry?> = async(CommonPool) {
    try {
        val list = SleepApp.database.napEntryDoa().getEntry(sleepEntity.uid)
        if (list.isNotEmpty()) return@async list.last() else return@async null
    } catch (e: Exception) {
        return@async null
    }
}

fun SleepApp.Companion.insertNapEntryForSleepEntity(napEntry: NapEntry): Deferred<NapEntry> = async(CommonPool) {
    val uid = database.napEntryDoa().insert(napEntry)
    napEntry.uid = uid
    return@async napEntry
}

fun SleepApp.Companion.fetchDeviceStatuses(sleepEntity :SleepEntry): Deferred<List<DeviceStatusEntry>> = async(CommonPool) {
    return@async SleepApp.database.deviceStatusDoa().getEntry(sleepEntity.uid)
}

fun SleepApp.Companion.fetchDeviceStatus(sleepEntity :SleepEntry): Deferred<DeviceStatusEntry?> = async(CommonPool) {
    try {
        val list = SleepApp.database.deviceStatusDoa().getEntry(sleepEntity.uid)
        return@async if (list.isNotEmpty()) list.last() else null
    } catch (e: Exception) {
        return@async null
    }
}

fun SleepApp.Companion.insertDeviceStatusEntryForSleepEntity(deviceStatusEntry: DeviceStatusEntry): Deferred<DeviceStatusEntry> =

        async(CommonPool) {
            val uid = database.deviceStatusDoa().insert(deviceStatusEntry)
            deviceStatusEntry.uid = uid
            return@async deviceStatusEntry
        }

fun SleepApp.Companion.getState(result: SleepEntry, deviceStatus: DeviceStatusEntry?): JournalState {
    return when {
        deviceStatus!= null && deviceStatus.deviceOn == null -> JournalState.DEVICEON
        result.timeAwoken == null -> JournalState.AWOKEN
        result.timeAwokenLightOn == null -> JournalState.DAYLIGHTSON
        result.timeAwokenOutOfBed == null -> JournalState.DAYOUTOFBED
        deviceStatus == null -> JournalState.DEVICEOFF
        result.bedTime == null -> JournalState.BEDTIME
        result.bedTimeLightsOut == null -> JournalState.NIGHTLIGHTSOUT
        else -> JournalState.SLEEPING
    }
}