package com.dev925.sleeptracker.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.dev925.sleeptracker.db.JournalState
import java.util.*

/**
 * Created by robertgross on 4/14/18.
 */

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction {
        add(frameId, fragment)
    }
}

fun FragmentActivity.removeFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction {
        remove(fragment)
    }
}

fun FragmentActivity.popBackStack(){
    supportFragmentManager.popBackStack()
}


fun FragmentActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction{
        replace(frameId, fragment)
        addToBackStack(null)
    }
}

fun Fragment.addFragment(fragment: Fragment, frameId: Int){
    (activity as FragmentActivity).addFragment(fragment, frameId)
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    (activity as FragmentActivity).replaceFragment(fragment, frameId)
}

fun JournalState.buildResult(value: Date): SelectionResult<JournalState, Date> {
    return SelectionResult<JournalState, Date>(this, value)
}