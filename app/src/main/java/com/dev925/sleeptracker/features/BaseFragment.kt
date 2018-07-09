package com.dev925.sleeptracker.features

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.dev925.daynighttoolbar.CustomToolbarView
import kotlinx.android.synthetic.main.fragment_week.*

/**
 * Created by robertgross on 4/16/18.
 */

open class BaseFragment: Fragment(), AppBarLayout.OnOffsetChangedListener, CustomToolbarView.CustomListener {

    private var scrollRange = -1f

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        appBarLayout?.addOnOffsetChangedListener(this)
        customToolbar?.setCustomListener(this)
    }

    override fun onValuesUpdated(isNight: Boolean, timeScale: Float) {

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        /*
         * Since the totalScrollRange is not going to change in runtime,
         * we getTotalScrollRange() only if the totalScrollRange equals -1
         * which is the initial value, if its not then it means that we already called it
         * and therefore we don't need to keep doing it.
         */

        appBarLayout?.let {
            if (scrollRange == -1f) scrollRange = appBarLayout.totalScrollRange.toFloat()

            /*
            * Simple Maths here, we divide verticalOffset we get from the listener
            * by the scrollRange and we add 1 to it to prevent the scale from being -1
            * when the AppBar is completely collapsed.
            */
            val scale = 1 + verticalOffset / scrollRange

            //We add the elevation to the AppBarLayout only if its collapsed.
            if (scale <= 0.0f)
                appBarLayout.elevation = 10f
            else
                appBarLayout.elevation = 0f

            if (scrollRange == -1f) scrollRange = appBarLayout.totalScrollRange.toFloat()

            //We update the scale in our custom view
            customToolbar.setScale(scale)

        }
    }

}