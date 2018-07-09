package com.dev925.sleeptracker.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.dev925.sleeptracker.db.converter.TimestampConverter
import com.dev925.sleeptracker.db.doa.DeviceStatusEntryDao
import com.dev925.sleeptracker.db.doa.NapEntryDoa
import com.dev925.sleeptracker.db.doa.SleepEntryDoa
import com.dev925.sleeptracker.db.models.DeviceStatusEntry
import com.dev925.sleeptracker.db.models.NapEntry
import com.dev925.sleeptracker.db.models.SleepEntry


/**
 * Created by robertgross on 4/14/18.
 */
@Database(entities = arrayOf(SleepEntry::class, NapEntry::class, DeviceStatusEntry::class), version = 1)
@TypeConverters(TimestampConverter::class)
abstract class SleepDatabase : RoomDatabase() {
    abstract fun sleepEntryDoa(): SleepEntryDoa
    abstract fun napEntryDoa(): NapEntryDoa
    abstract fun deviceStatusDoa(): DeviceStatusEntryDao
}
