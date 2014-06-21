package com.avwave.looperPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by rayarvin on 6/21/14.
 */
public class LooperViewPager extends ViewPager {

    private static final boolean DEFAULT_BOUNDARY_CACHING = true;
    OnPageChangeListener outerPageChangeListener;

    private LooperPagerAdapter adapter;
    private boolean isBoundaryCaching = DEFAULT_BOUNDARY_CACHING;

    public static int toRealPosition(int position, int count) {
        position = position - 1;
        if (position < 0) {
            position += count;
        } else {
            position = position % count;
        }
        return position;
    }

    public void setBoundaryCaching(boolean flag) {
        isBoundaryCaching = flag;
        if (adapter != null) {
            adapter.setBoundaryCaching(flag);
        }
    }

    @Override
    public void setAdapter(PagerAdapter _adapter) {
        this.adapter = new LooperPagerAdapter(_adapter);
        this.adapter.setBoundaryCaching(isBoundaryCaching);
        super.setAdapter(this.adapter);
        setCurrentItem(0, false);
    }

    @Override
    public PagerAdapter getAdapter() {
        return adapter != null ? adapter.getRealAdapter() : adapter;
    }

    @Override
    public int getCurrentItem() {
        return adapter != null ? adapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        int realItem = adapter.toInnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        outerPageChangeListener = listener;
    }

    public LooperViewPager(Context context) {
        super(context);
        init();
    }

    public LooperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(onPageChangeListener);
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float previousOffset = -1;
        private float previousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = adapter.toRealPosition(position);
            if (previousPosition != realPosition) {
                previousPosition = realPosition;
                if (outerPageChangeListener != null) {
                    outerPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition = position;
            if (adapter != null) {
                realPosition = adapter.toRealPosition(position);
                if (positionOffset == 0
                        && previousOffset == 0
                        && (position == 0 ||position == adapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }

            previousOffset = positionOffset;
            if (outerPageChangeListener != null) {
                if (realPosition != adapter.getRealCount() - 1) {
                    outerPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        outerPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        outerPageChangeListener.onPageScrolled(realPosition, 0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (adapter != null) {
                int position = LooperViewPager.super.getCurrentItem();
                int realPosition = adapter.toRealPosition(position);
                if (state == ViewPager.SCROLL_STATE_IDLE
                        && (position == 0 || position == adapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }
            if (outerPageChangeListener != null) {
                outerPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };
}
