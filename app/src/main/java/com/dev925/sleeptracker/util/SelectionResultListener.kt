package com.dev925.sleeptracker.util

/**
 * Created by robertgross on 4/16/18.
 */
interface SelectionResultListener<T> {
    fun onSelection(data: T)
}