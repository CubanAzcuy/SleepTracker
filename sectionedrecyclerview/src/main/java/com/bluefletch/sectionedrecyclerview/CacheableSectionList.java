package com.bluefletch.sectionedrecyclerview;

import java.util.concurrent.Callable;

/**
 * Created by robertgross on 7/20/17.
 */

public class CacheableSectionList extends ExpandableSectionList {

    public CacheableSectionList(SectionList.ListObserver listObserver) {
        super(listObserver);
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
        if (mSections.get(position) instanceof CacheableSection) {
            CacheableSection section = (CacheableSection) mSections.get(position);
            int relativePosition = getRelativePosition(position) + 1;

            switch (section.getCacheState()) {
                case NEW:
                    attemptToGetCache(relativePosition, section, position);
                    break;
                case LOADING_BACKGROUND:
                    expandFromLoading(relativePosition, section);
                    break;
                case CACHED_BACKGROUND:
                    expandFromCache(relativePosition, section);
                    break;
                case PAUSE:
                    expandFromPause(relativePosition, section);
                    break;
                default:
                    collapseSection(position);
                    break;
            }
        } else {
            super.expandSection(position);
        }
    }

    public void collapseSection(int position) {
        if (mSections.get(position) instanceof CacheableSection) {
            CacheableSection section = (CacheableSection) mSections.get(position);
            int relativePosition = getRelativePosition(position)+1;
            switch (section.getCacheState()) {
                case LOADING_FOREGROUND:
                    collapseFromLoading(relativePosition, section);
                    break;
                case CACHED_FOREGROUND:
                    collapseFromDefault(relativePosition, section);
                    break;
                default:
                    expandSection(position);
                    break;
            }
        } else {
            super.collapseSection(position);
        }
    }

    private void expandFromLoading(int relativePosition, CacheableSection section) {

        section.hideLoading();

        int rowsToAdd = section.getSize() - 1;

        if(mIsBatchUpdating) {
            mChangeList.add(new Change(relativePosition, rowsToAdd, ChangeType.INSERT));
        } else {
            mObserver.onChanged(relativePosition, 1);
            if(rowsToAdd > 1) {
                mObserver.onInserted(relativePosition + 1, rowsToAdd - 1);
            }
        }
    }

    private void attemptToGetCache(int relativePosition, CacheableSection section, final int orginalPostition) {

        section.expand(new Callable() {
            @Override
            public Object call() throws Exception {
                expandSection(orginalPostition);
                return null;
            }
        });

        int rowsToAdd = section.getSize() - 1;

        if(mIsBatchUpdating) {
            mChangeList.add(new Change(relativePosition, rowsToAdd, ChangeType.INSERT));
        } else {
            mObserver.onInserted(relativePosition, rowsToAdd);
        }
    }

    private void expandFromPause(int relativePosition, CacheableSection section) {

        section.unpause();

        int rowsToAdd = section.getSize() - 1;

        if(mIsBatchUpdating) {
            mChangeList.add(new Change(relativePosition, rowsToAdd, ChangeType.INSERT));
        } else {
            mObserver.onInserted(relativePosition, rowsToAdd);
        }
    }

    private void expandFromCache(int relativePosition, CacheableSection section) {
        section.setForeground();

        int rowsToAdd = section.getSize() - 1;

        if (mIsBatchUpdating) {
            mChangeList.add(new Change(relativePosition, rowsToAdd, ChangeType.INSERT));
        } else {
            mObserver.onInserted(relativePosition, rowsToAdd);
        }

    }


    private void collapseFromDefault(int relativePosition, CacheableSection section) {
        int rowsToRemove = section.getSize() - 1;

        section.setBackground();

        if(mIsBatchUpdating) {
            mChangeList.add(new Change(relativePosition, rowsToRemove, ChangeType.REMOVE));
        } else {
            mObserver.onRemoved(relativePosition, rowsToRemove);
        }

    }

    private void collapseFromLoading(int relativePosition, CacheableSection section) {
        int rowsToRemove = section.getSize() - 1;

        section.pause();

        if(mIsBatchUpdating) {
            mChangeList.add(new Change(relativePosition, rowsToRemove, ChangeType.REMOVE));
        } else {
            mObserver.onRemoved(relativePosition, rowsToRemove);
        }

    }

    public void clearCache() {
        for (Section section : mSections) {
            if (section instanceof CacheableSection) {
                CacheableSection cacheableSection = (CacheableSection) section;
                cacheableSection.clearCache();
            }
        }

        mObserver.onDataSetChange();
    }
}
