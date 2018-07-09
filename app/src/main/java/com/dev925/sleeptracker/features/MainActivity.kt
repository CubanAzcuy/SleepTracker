package com.dev925.sleeptracker.features

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev925.sleeptracker.R
import com.dev925.sleeptracker.features.home.HomeScreenFragment
import com.dev925.sleeptracker.util.addFragment


class MainActivity : AppCompatActivity() {

    private var scrollRange = -1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(HomeScreenFragment(), R.id.layout)
    }
}
