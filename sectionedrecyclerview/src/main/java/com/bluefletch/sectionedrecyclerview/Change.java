package com.bluefletch.sectionedrecyclerview;

/**
 * Created by robertgross on 7/19/17.
 */

class Change {

    private int mStart;
    private int mStop;
    private ChangeType mType;

    public Change(int start, int stop, ChangeType type) {
        mStart = start;
        mStop = stop;
        mType = type;
    }

    public int getPosition() {
        return mStart;
    }

    public int getCount() {
        return mStop;
    }

    public int getFromPosition() {
        return mStart;
    }

    public int getToPosition() {
        return mStop;
    }

    public ChangeType getType() {
        return mType;
    }

}
