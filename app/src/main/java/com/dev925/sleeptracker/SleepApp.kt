package com.dev925.sleeptracker

import android.app.Application
import android.arch.persistence.room.Room
import com.dev925.sleeptracker.db.SleepDatabase
import kotlinx.coroutines.experimental.newFixedThreadPoolContext

/**
 * Created by robertgross on 4/14/18.
 */
class SleepApp : Application() {
    companion object {
        lateinit var database: SleepDatabase
    }

    override fun onCreate() {
        super.onCreate()
        SleepApp.database =  Room.databaseBuilder(this, SleepDatabase::class.java, "sleepDB").build()
    }
}