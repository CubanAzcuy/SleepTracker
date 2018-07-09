package com.bluefletch.sectionedrecyclerview;
/**
 * Created by robertgross on 7/19/17.
 */

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by robertgross on 7/18/17.
 */

public class SectionList implements Collection<Section> {

    protected ArrayList<Section> mSections = new ArrayList();
    protected ListObserver mObserver;
    protected ArrayList<Change> mChangeList = new ArrayList<>();
    protected boolean mIsBatchUpdating;
    protected final ChangeTypeSort sort = new ChangeTypeSort();

    public SectionList(ListObserver listObserver) {
        mObserver = listObserver;
    }

    public void beginBatchedUpdates() {
        mIsBatchUpdating = true;
    }

    public void addBatchedUpdate(List<Change> changeList) {
        mChangeList.addAll(changeList);
    }

    public void endBatchedUpdates() {

//        Collections.sort(mChangeList, sort);

//        for (int i = mChangeList.size()-1; i >= 0 ; i--) {
//            Change change = mChangeList.get(i);
//            switch(change.getType()) {
//                case INSERT:
//                    mObserver.onInserted(change.getPosition(), change.getCount());
//                    break;
//                case REMOVE:
//                    mObserver.onRemoved(change.getPosition(), change.getCount());
//                    break;
//                case MOVE:
//                    mObserver.onMoved(change.getFromPosition(), change.getToPosition());
//                    break;
//                case CHANGE:
//                    mObserver.onChanged(change.getPosition(), change.getCount());
//                    break;
//            }
//        }

        mObserver.onDataSetChange();
        mChangeList.clear();
        mIsBatchUpdating = false;
    }

    public TypedItem get(int position) {

        int size = 0;

        for (Section section : mSections) {
            if((size + section.getSize()) <= position) {
                size += section.getSize();
            } else {
                int index = position - size;
                return section.get(index);
            }
        }

        return null;
    }

    public int maxSize() {
        int size = 0;

        for (Section section : mSections) {
            size += section.getSize();
        }

        return size;
    }

    @Override
    public boolean add(Section section) {
        int size = maxSize();

        boolean result =  mSections.add(section);

        if(section instanceof UpdatableSection) {
            UpdatableSection updatableSection = (UpdatableSection) section;
            updatableSection.setCallable(new Updatable<List<ChangeRequest>>() {
                @Override
                public void call(List<ChangeRequest> value) {
                    if(value.size() == 1) {
                        boolean didUpdate = updateItem(value.get(0));
                    } else {
                        beginBatchedUpdates();
                        for (ChangeRequest request: value) {
                            boolean didUpdate = updateItem(request);
                        }
//                        endBatchedUpdates();
                    }
                }
            });
        }

        if(result) {
            if(mIsBatchUpdating) {
                mChangeList.add(new Change(size, section.getSize(), ChangeType.INSERT));
            } else {
                mObserver.onInserted(size, section.getSize());
            }
        }
        return result;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Section> collection) {
        for (Section section: collection) {
            boolean result = add(section);

            if(!result) return false;
        }

        return true;
    }

    protected int getRelativePosition(int position) {
        int relativePosition = 0;

        for(int i = 0; i < position; i++) {
            relativePosition += mSections.get(i).getSize();
        }
        return relativePosition;
    }

    @Override
    public boolean remove(Object o) {
        if(o instanceof Section) {
            Section section = (Section) o;
            int index = mSections.indexOf(section);
            int relativePosition = getRelativePosition(index);


            boolean result = mSections.remove(o);

            if(result) {
                if(mIsBatchUpdating) {
                    mChangeList.add(new Change(relativePosition, section.getSize(), ChangeType.REMOVE));
                } else {
                    mObserver.onRemoved(relativePosition, section.getSize());
                }
            }

            return result;
        }
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        for (Object section: collection) {
            boolean result = remove(section);

            if(!result) return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        for (Object section: mSections) {
            if(!collection.contains(section)) {
                boolean result = remove(section);
                if(!result) return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return mSections.contains(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        for (Object section: collection) {
            boolean result = contains(section);

            if(!result) return false;
        }
        return true;
    }


    public boolean updateItem(ChangeRequest changeRequest) {
        int relativePositionOfSection = 0;

        for (Section section : mSections) {
            if(!section.is(changeRequest.getSection())) {
                relativePositionOfSection += section.getSize();
            } else {
                break;
            }
        }

        int relativeIndex = changeRequest.getStartIndex() + relativePositionOfSection;

        switch (changeRequest.getAction()) {
            case INSERT:
                if(mIsBatchUpdating) {
                    mChangeList.add(new Change(relativeIndex,
                            changeRequest.getRange(),
                            ChangeType.INSERT));
                } else {
                    mObserver.onInserted(changeRequest.getStartIndex() + relativePositionOfSection, changeRequest.getRange());
                }
                break;
            case REMOVE:
                if(mIsBatchUpdating) {
                    mChangeList.add(new Change(changeRequest.getStartIndex() + relativePositionOfSection,
                            changeRequest.getRange(),
                            ChangeType.REMOVE));
                } else {
                    mObserver.onRemoved(changeRequest.getStartIndex() + relativePositionOfSection, changeRequest.getRange());
                }
                break;
            case MOVE:
                if(mIsBatchUpdating) {
                    mChangeList.add(new Change(changeRequest.getStartIndex() + relativePositionOfSection,
                            changeRequest.getRange(),
                            ChangeType.MOVE));
                } else {
                    mObserver.onMoved(changeRequest.getStartIndex() + relativePositionOfSection, changeRequest.getRange());
                }
                break;
            case CHANGE:
                if(mIsBatchUpdating) {
                    mChangeList.add(new Change(changeRequest.getStartIndex() + relativePositionOfSection,
                            changeRequest.getRange(),
                            ChangeType.CHANGE));
                } else {
                    mObserver.onChanged(changeRequest.getStartIndex() + relativePositionOfSection, changeRequest.getRange());
                }
                break;
        }
        return true;
    }

    public boolean updateItemAt(int position, TypedItem item) {
        int size = 0;

        for (Section section : mSections) {
            if((size + section.getSize()) <= position) {
                size += section.getSize();
            } else {
                int index = position - size;
                mObserver.onChanged(position, 1);
                section.updateItem(index, item);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return mSections.isEmpty();
    }

    @Override
    public void clear() {
        int maxSize = maxSize();
        mSections.clear();
        mObserver.onRemoved(0, maxSize);
    }

    @Override
    public int size() {
        int size = 0;

        for (Section section : mSections) {
            size += section.getSize();
        }

        return size;
    }

    @NonNull
    @Override
    public Iterator<Section> iterator() {
        Iterator<Section> it = new Iterator<Section>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < mSections.size() && mSections.get(currentIndex) != null;
            }

            @Override
            public Section next() {
                return mSections.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        return it;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return mSections.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return (T[]) mSections.toArray();
    }

    public int getSectionIndex(int position) {

        int size = 0;

        Section section = getSection(position);
        return mSections.indexOf(section);
    }

    public Section getSection(int position) {

        int size = 0;

        for (Section section : mSections) {
            if((size + section.getSize()) <= position) {
                size += section.getSize();
            } else {
                return section;
            }
        }

        return null;
    }

    public interface ListObserver {
        void onChanged(int position, int count);
        void onInserted(int position, int count);
        void onRemoved(int position, int count);
        void onMoved(int fromPosition, int toPosition);
        void onDataSetChange();

    }
}
