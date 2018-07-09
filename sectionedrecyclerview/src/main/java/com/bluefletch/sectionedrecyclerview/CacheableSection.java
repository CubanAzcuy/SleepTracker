package com.bluefletch.sectionedrecyclerview;

import java.util.concurrent.Callable;

/**
 * Created by robertgross on 7/20/17.
 */

public abstract class CacheableSection extends ExpandableSection {

    public enum Cache { NEW, LOADING_FOREGROUND, LOADING_BACKGROUND, PAUSE, CACHED_BACKGROUND, CACHED_FOREGROUND}

    protected TypedItem mLoadingBody;
    protected Cache mCache = Cache.NEW;

    public void setForeground() {
        switch (mCache) {
            case LOADING_BACKGROUND:
                mCache = Cache.LOADING_FOREGROUND;
                break;
            case CACHED_BACKGROUND:
                mCache = Cache.CACHED_FOREGROUND;
                setExpanded(true);
                break;
        }
    }

    public void setBackground() {
        switch (mCache) {
            case LOADING_FOREGROUND:
                mCache = Cache.LOADING_BACKGROUND;
                break;
            case CACHED_FOREGROUND:
                mCache = Cache.CACHED_BACKGROUND;
                break;
        }

        setExpanded(false);
    }

    public void hideLoading() {
        mCache = Cache.CACHED_FOREGROUND;

        setExpanded(true);
    }

    public void pause() {
        mCache = Cache.PAUSE;
        setExpanded(false);
    }

    public void unpause() {
        mCache = Cache.LOADING_FOREGROUND;
    }

    public void loadData(Callable callable) {
        mCache = Cache.LOADING_FOREGROUND;
    }

    protected void loadingComplete(final Callable callable) {

        if(mCache != Cache.PAUSE) {
            try {
                mCache = Cache.LOADING_BACKGROUND;
                callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mCache = Cache.CACHED_BACKGROUND;
        }

    }

    public Cache getCacheState() {
        return mCache;
    }

    public void clearCache() {
        mItems.clear();
        mIsExpanded = false;
        mCache = Cache.NEW;
    }

    @Override
    public void expand() {
        expand(null);
    }

    public void expand(final Callable callable) {

        if(mItems.size() > 0) {
            mCache = Cache.CACHED_FOREGROUND;
            setExpanded(true);
        }

        switch (mCache) {
            case NEW:
                mCache = Cache.LOADING_FOREGROUND;
                loadData(callable);
                break;
            case LOADING_BACKGROUND:
                mCache = Cache.LOADING_FOREGROUND;
                break;
        }
    }

    @Override
    public int getSize() {
        if(mCache == Cache.LOADING_FOREGROUND) {
            return 2;
        } else {
            return super.getSize();
        }
    }

    @Override
    public TypedItem get(int i) {
        if((mCache == Cache.LOADING_FOREGROUND) && i != 0) {
            return mLoadingBody;
        } else {
            return super.get(i);
        }
    }
}
