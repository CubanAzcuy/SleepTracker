package com.dev925.sleeptracker.features.edit

import android.view.ViewGroup
import com.bluefletch.sectionedrecyclerview.BaseViewHolder
import com.bluefletch.sectionedrecyclerview.SectionRecyclerViewAdapter
import com.dev925.sleeptracker.features.edit.util.DoubleEditEntrySelectionResult
import com.dev925.sleeptracker.features.edit.util.EditEntrySelectionResult
import com.dev925.sleeptracker.features.edit.util.RowTypes
import com.dev925.sleeptracker.features.edit.viewholder.CombinedDateViewHolder
import com.dev925.sleeptracker.features.edit.viewholder.HeaderViewHolder
import com.dev925.sleeptracker.features.edit.viewholder.SingleDateViewHolder
import com.dev925.sleeptracker.util.SelectionResultListener

/**
 * Created by robertgross on 4/16/18.
 */

class EditEntrySectionRecyclerViewAdapter(private val selectionResultListener: SelectionResultListener<EditEntrySelectionResult>) : SectionRecyclerViewAdapter() {

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        mSections?.let { sections ->
            val data = sections.get(position).data
            when(holder) {
                is HeaderViewHolder -> {
                    holder.bind(data as String)
                }
                is CombinedDateViewHolder -> {
                    val double = data as DoubleEditEntrySelectionResult
                    holder.bind(double)
                    holder.setSelectionResultListener(selectionResultListener)
                }
                is SingleDateViewHolder -> {
                    holder.bind(data as EditEntrySelectionResult)
                    holder.itemView.setOnClickListener {
                        holder.selectedRow(selectionResultListener)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*>? {
        val viewHolder = RowTypes.values()[viewType]
        when (viewHolder) {
            RowTypes.HEADER -> return HeaderViewHolder.newInstance(parent)
            RowTypes.COMBINED -> return CombinedDateViewHolder.newInstance(parent)
            RowTypes.SINGLE -> return SingleDateViewHolder.newInstance(parent)
        }
    }

}