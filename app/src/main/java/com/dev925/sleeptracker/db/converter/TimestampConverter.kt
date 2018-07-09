package com.dev925.sleeptracker.db.converter

import android.arch.persistence.room.TypeConverter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robertgross on 4/14/18.
 */
object TimestampConverter {
    private val format: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): Date? {
        return try {
            format.parse(value)
        } catch (e: Exception) {
            null
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: Date?): String? {
        return try {
            format.format(date)
        } catch (e: Exception) {
            null
        }
    }
}