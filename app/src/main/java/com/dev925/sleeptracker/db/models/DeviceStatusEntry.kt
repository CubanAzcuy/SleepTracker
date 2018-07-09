package com.dev925.sleeptracker.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by robertgross on 4/14/18.
 */

@Entity(tableName = "device_status_entry",
        foreignKeys = arrayOf(ForeignKey(entity = SleepEntry::class,
                parentColumns = arrayOf("uid"),
                childColumns = arrayOf("sleep_entry"),
                onDelete = ForeignKey.CASCADE)))

data class DeviceStatusEntry(
        @PrimaryKey(autoGenerate = true)
        var uid: Long = 0,

        @ColumnInfo(name = "sleep_entry")
        val sleepEntry: Long,

        @ColumnInfo(name = "device_on")
        var deviceOn: Date? = null,

        @ColumnInfo(name = "device_off")
        var deviceOff: Date? = null
)
