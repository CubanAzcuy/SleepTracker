package com.bluefletch.sectionedrecyclerview;

/**
 * Created by robertgross on 7/20/17.
 */

public abstract class ExpandableSection extends Section {

    protected boolean mIsExpanded;

    public void expand() {
        mIsExpanded = true;
    }

    public void setExpanded(boolean expanded) {
        mIsExpanded = expanded;
    }

    public boolean isExpanded() {
        return mIsExpanded;
    }

    //Assumes a header
    @Override
    public int getSize() {

        if(mIsExpanded) {
            return super.getSize();
        } else {
            return 1;
        }
    }

    public boolean hasHeader() {
        return mHeader != null;
    }
}
