package com.dev925.sleeptracker.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by robertgross on 4/14/18.
 */

@Entity(tableName = "nap_entry",
        foreignKeys = arrayOf(ForeignKey(entity = SleepEntry::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("sleep_entry"),
        onDelete = ForeignKey.CASCADE)))

data class NapEntry(
        @PrimaryKey(autoGenerate = true)
        var uid: Long = 0,

        @ColumnInfo(name = "sleep_entry")
        var sleepEntry: Long,

        @ColumnInfo(name = "nap_start")
        var napStart: Date? = null,

        @ColumnInfo(name = "nap_stop")
        var napStop: Date? = null
)