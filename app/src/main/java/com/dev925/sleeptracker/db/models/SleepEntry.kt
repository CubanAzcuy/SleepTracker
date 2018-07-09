package com.dev925.sleeptracker.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "sleep_entry")

data class SleepEntry (

    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,

    @ColumnInfo(name = "entry_date")
    var entryDate: Date? = null,

    @ColumnInfo(name = "time_awoken")
    var timeAwoken: Date? = null,

    @ColumnInfo(name = "time_awoken_lights_on")
    var timeAwokenLightOn: Date? = null,

    @ColumnInfo(name = "time_awoken_out_of_bed")
    var timeAwokenOutOfBed: Date? = null,

    @ColumnInfo(name = "bed_time")
    var bedTime: Date? = null,

    @ColumnInfo(name = "bed_time_lights_out")
    var bedTimeLightsOut: Date? = null
)