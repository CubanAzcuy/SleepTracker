package com.bluefletch.sectionedrecyclerview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertgross on 11/24/17.
 */

public abstract class UpdatableSection extends Section {
    protected Updatable<List<ChangeRequest>> updateSectionObservable;

    @Override
    public void updateItem(int index, TypedItem item) {
        super.updateItem(index, item);
        if(updateSectionObservable != null) {

            ChangeRequest changeRequest = new ChangeRequest(this, ChangeType.CHANGE, index, 1);
            ArrayList<ChangeRequest> list = new ArrayList(1);
            list.add(changeRequest);
            updateSectionObservable.call(list);
        }
    }

    protected void updateSection(ArrayList<ChangeRequest> changeRequests) {
        if(updateSectionObservable != null) {
            updateSectionObservable.call(changeRequests);
        }
    }

    protected void setCallable(Updatable<List<ChangeRequest>> callable) {
        updateSectionObservable = callable;
    }
}
