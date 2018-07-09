package com.bluefletch.sectionedrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by robertgross on 11/3/17.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected T _data;

    public BaseViewHolder(View view) {
        super(view);
        bindViews(this, view);
    }

    public BaseViewHolder(View view, boolean inject) {
        super(view);

        if(inject) {
            bindViews(this, view);
        }
    }

    abstract public void bind(T data);
    abstract public void bindViews(Object object, View view);
}
