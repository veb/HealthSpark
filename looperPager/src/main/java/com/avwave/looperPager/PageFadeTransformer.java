package com.avwave.looperPager;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by rayarvin on 6/21/14.
 */
public class PageFadeTransformer  implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left
        } else if (position <= 0) { // [-1,0]
            // This page is moving out to the left

            // Counteract the default swipe
            view.setTranslationX(pageWidth * -position);
//            view.setTranslationX(pageWidth * position);
            view.setAlpha(1 + position);

        } else if (position <= 1) { // (0,1]
            // This page is moving in from the right

            // Counteract the default swipe
            view.setTranslationX(pageWidth * -position);
//            view.setTranslationX(pageWidth * position);
            view.setAlpha(1 - position);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right
        }
    }


}
