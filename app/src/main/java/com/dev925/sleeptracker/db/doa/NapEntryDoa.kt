package com.dev925.sleeptracker.db.doa

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev925.sleeptracker.db.models.NapEntry

/**
 * Created by robertgross on 4/14/18.
 */
@Dao
interface NapEntryDoa {
    @Query("SELECT * FROM nap_entry WHERE sleep_entry = :sleepId")
    fun getEntry(sleepId: Long): List<NapEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(napEntry: NapEntry): Long
}