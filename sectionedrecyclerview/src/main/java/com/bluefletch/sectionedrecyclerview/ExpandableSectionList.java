package com.bluefletch.sectionedrecyclerview;

/**
 * Created by robertgross on 7/20/17.
 */

public class ExpandableSectionList extends SectionList {

    public ExpandableSectionList(SectionList.ListObserver listObserver) {
        super(listObserver);
    }

    public boolean selectSection(Object object) {
        return expandSection(object);
    }

    public void selectSection(final int section) {
        expandSection(section);
    }

    public boolean expandSection(Object object) {
        int index = -1;
        for (Section section: mSections) {
            if(section.isHeaderData(object)) {
                index = mSections.indexOf(section);
                break;
            }
        }
        if(index != -1) {
            expandSection(index);
            return true;
        } else {
            return false;
        }

    }

    public void expandSection(final int position) {

        if(mSections.get(position) instanceof ExpandableSection) {
            ExpandableSection expandableSection = (ExpandableSection) mSections.get(position);
            int headerOffset = expandableSection.hasHeader() ? 1 : 0;

            if(!expandableSection.isExpanded()) {
                int relativePosition = getRelativePosition(position) + headerOffset;

                expandableSection.setExpanded(true);

                int rowsToAdd = expandableSection.getSize() - headerOffset;

                if(mIsBatchUpdating) {
                    mChangeList.add(new Change(relativePosition, rowsToAdd, ChangeType.INSERT));
                } else {
                    mObserver.onInserted(relativePosition, rowsToAdd);
                }

            } else {
                collapseSection(position);
            }
        }

    }

    public void collapseSection(int position) {

        if(mSections.get(position) instanceof ExpandableSection) {
            ExpandableSection expandableSection = (ExpandableSection) mSections.get(position);
            int headerOffset = expandableSection.hasHeader() ? 1 : 0;

            if (expandableSection.isExpanded()) {

                int relativePosition = getRelativePosition(position) + headerOffset;
                int rowsToRemove = expandableSection.getSize() - headerOffset;

                expandableSection.setExpanded(false);

                if (mIsBatchUpdating) {
                    mChangeList.add(new Change(relativePosition, rowsToRemove, ChangeType.REMOVE));
                } else {
                    mObserver.onRemoved(relativePosition, rowsToRemove);
                }
            } else {
                expandSection(position);
            }
        }
    }
}
