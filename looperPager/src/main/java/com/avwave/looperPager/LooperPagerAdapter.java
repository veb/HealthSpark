package com.avwave.looperPager;

import android.os.Parcelable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rayarvin on 6/21/14.
 */
public class LooperPagerAdapter extends PagerAdapter {
    private PagerAdapter adapter;
    private SparseArray<ToDestroy> toDestroySparseArray = new SparseArray<ToDestroy>();
    private boolean isBoundaryCaching;

    void setBoundaryCaching(boolean flag) {
        isBoundaryCaching = flag;
    }

    LooperPagerAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getCount() {
        return adapter.getCount() + 2;
    }

    public int getRealCount() {
        return adapter.getCount();
    }

    public PagerAdapter getRealAdapter() {
        return adapter;
    }

    @Override
    public void notifyDataSetChanged() {
        toDestroySparseArray = new SparseArray<ToDestroy>();
        super.notifyDataSetChanged();
    }

    int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = (position-1) % realCount;
        if (realPosition < 0)
            realPosition += realCount;

        return realPosition;
    }

    public int toInnerPosition(int realPosition) {
        return realPosition + 1;
    }

    private int getRealFirstposition() {
        return 1;
    }

    private int getRealLastPosition() {
        return getRealFirstposition() + getRealCount() - 1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = (adapter instanceof FragmentPagerAdapter || adapter instanceof FragmentStatePagerAdapter) ? position : toRealPosition(position);

        if (isBoundaryCaching) {
            ToDestroy toDestroy = toDestroySparseArray.get(position);
            if (toDestroy != null) {
                toDestroySparseArray.remove(position);
                return toDestroy.object;
            }
        }
        return adapter.instantiateItem(container, realPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realFirst = getRealFirstposition();
        int realLast = getRealLastPosition();
        int realPosition = (adapter instanceof FragmentPagerAdapter || adapter instanceof FragmentStatePagerAdapter) ? position : toRealPosition(position);

        if (isBoundaryCaching && (position == realFirst || position == realLast)) {
            toDestroySparseArray.put(position, new ToDestroy(container, realPosition, object));
        } else {
            adapter.destroyItem(container, realPosition, object);
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        adapter.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        adapter.setPrimaryItem(container, position, object);
    }

    static class ToDestroy {
        ViewGroup container;
        int position;
        Object object;

        ToDestroy(ViewGroup container, int position, Object object) {
            this.container = container;
            this.position = position;
            this.object = object;
        }
    }
}
