package com.dev925.sleeptracker.features.edit.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluefletch.sectionedrecyclerview.BaseViewHolder
import com.dev925.sleeptracker.R
import kotlinx.android.synthetic.main.row_header.view.*

/**
 * Created by robertgross on 4/16/18.
 */

class HeaderViewHolder(view: View) : BaseViewHolder<String>(view) {

    companion object Factory {
        fun newInstance(parent: ViewGroup): HeaderViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false)
            return HeaderViewHolder(view)
        }
    }

    override fun bind(data: String) {
        itemView.title.text = data
    }

    //Not Used in Kotlin
    override fun bindViews(`object`: Any?, view: View?) {

    }

}