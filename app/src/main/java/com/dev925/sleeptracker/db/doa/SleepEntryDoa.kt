package com.dev925.sleeptracker.db.doa

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev925.sleeptracker.db.models.SleepEntry
import java.util.*

/**
 * Created by robertgross on 4/14/18.
 */
@Dao
interface SleepEntryDoa {

    @Query("SELECT * FROM sleep_entry WHERE entry_date BETWEEN :date AND :date2 LIMIT 1")
    fun getEntry(date: Date, date2: Date): SleepEntry

    @Query("SELECT * FROM sleep_entry")
    fun getEntries(): List<SleepEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sleepEntry: SleepEntry): Long
}