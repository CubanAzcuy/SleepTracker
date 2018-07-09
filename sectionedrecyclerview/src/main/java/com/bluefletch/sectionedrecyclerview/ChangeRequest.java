package com.bluefletch.sectionedrecyclerview;

/**
 * Created by robertgross on 12/2/17.
 */

public class ChangeRequest {
    Section section;
    ChangeType action;
    int startIndex;
    int range;
    boolean shouldBatch = false;

    public ChangeRequest(Section section, ChangeType action, int startIndex, int range) {
        this.section = section;
        this.action = action;
        this.startIndex = startIndex;
        this.range = range;
    }

    public ChangeRequest(Section section, ChangeType action, int startIndex, int range, boolean shouldBatch) {
        this.section = section;
        this.action = action;
        this.startIndex = startIndex;
        this.range = range;
        this.shouldBatch = shouldBatch;
    }

    public boolean isShouldBatch() {
        return shouldBatch;
    }

    public Section getSection() {
        return section;
    }

    public ChangeType getAction() {
        return action;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getRange() {
        return range;
    }
}
