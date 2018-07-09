package com.bluefletch.sectionedrecyclerview;

import java.util.ArrayList;

/**
 * Created by robertgross on 7/19/17.
 */

public abstract class Section {

    protected TypedItem mHeader;
    protected ArrayList<TypedItem> mItems = new ArrayList<>();

    public int getSize() {
        int headerCount = 0;
        if(mHeader != null) headerCount = 1;
        return mItems.size() + headerCount;
    }

    public TypedItem get(int i) {
        int headerCount = 0;
        if(mHeader != null) headerCount = 1;

        if(headerCount == 1 && i == 0) return mHeader;
        return mItems.get(i - headerCount);
    }

    public void updateItem(int index, TypedItem item) {
        mItems.remove(index);
        mItems.add(index, item);
    }

    public boolean isHeaderData(Object object) {
        if(mHeader == null) return false;
        if(mHeader.getData() == null) return false;
        return mHeader.getData().equals(object);
    }

    public boolean is(Object object) {
        if(object instanceof Section) {
            Section section = (Section) object;
            if(mHeader != null && mItems.size() > 0) {
                boolean sameHeader = mHeader.getData() == null && object == null || mHeader.getData().equals(section.mHeader.getData());
                boolean rowsAreSame = sampleTest(section);

                return sameHeader && rowsAreSame;
            } else if (mHeader == null && mItems.size() > 0) {
                return sampleTest(section);
            } else if (mHeader != null && mItems.size() == 0) {
                return mHeader.getData() == null && object == null || mHeader.getData().equals(section.mHeader.getData());
            } else  {
                return false;
            }
        } else {
            return false;
        }
    }



    //test every 3rd object
    public boolean sampleTest(Section section) {
        if((mItems.size() > 0 && !(section.mItems.size() > 0)) ||
                (!(mItems.size() > 0) && (section.mItems.size() > 0))) return false;

        //Can't not compare
        if(mItems.size() == 0 && mItems.size() == 0) return false;

        for (int i = 0; i < mItems.size(); i=i+3) {
            if((mItems.get(i) != null && section.mItems.get(i) != null) && (mItems.get(i).getData() != null && section.mItems.get(i).getData() != null)) {
                if (!mItems.get(i).getData().equals(section.mItems.get(i).getData())) {
                    return false;
                }
            }

            if((i+3) < mItems.size()) break;
        }

        return true;
    }

}
