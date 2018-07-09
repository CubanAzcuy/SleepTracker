package com.bluefletch.sectionedrecyclerview;

import android.support.v7.widget.RecyclerView;


/**
 * Created by robertgross on 7/19/17.
 */

public abstract class SectionRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public CacheableSectionList mSections = new CacheableSectionList(new SectionList.ListObserver() {
        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onDataSetChange() {
            notifyDataSetChanged();
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

    });

    @Override
    public int getItemCount() {
        return mSections.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mSections.get(position).getType();
    }

    public void addSection(Section section) {
        mSections.add(section);
    }

    public void selectSection(Object section) {
        mSections.selectSection(section);
    }

    public  void selectionSection(int section) {
        mSections.selectSection(section);
    }

    public void expandSection(int section) {
        mSections.expandSection(section);
    }

    public void collapseSection(int section) {
        mSections.collapseSection(section);
    }

    public void endBatchUpdates() {
        mSections.endBatchedUpdates();
    }

    public void clearSections() {
        mSections.clear();
    }
}
