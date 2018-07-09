package com.bluefletch.sectionedrecyclerview;

/**
 * Created by robertgross on 11/3/17.
 */

public class TypedItem {

    private int mType;
    private Object mData;

    public TypedItem(int type, Object data) {
        mType = type;
        mData = data;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }
}
