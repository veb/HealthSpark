package com.omnibuttie.therable.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.avwave.looperPager.PageFadeTransformer;
import com.omnibuttie.therable.R;

public class Composer extends Activity implements AdapterView.OnItemSelectedListener  {
    ImageAdapter adapter;
    ViewPager g;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composer);

        adapter = new ImageAdapter(this);

        g = (ViewPager) findViewById(R.id.emotePager);
        g.setAdapter(adapter);
        g.setPageTransformer(true, new PageFadeTransformer());

        g.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.composer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class ImageAdapter extends PagerAdapter {
        private Context ctx;

        public ImageAdapter(Context c) {
            ctx = c;
        }

        public int getCount() {
            return emoteIcons.length;
        }

        public Object getItem(int pos) {
            return pos;
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            ImageView image = new ImageView(ctx);

            // Now the layout parameters, these are a little tricky at first
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);

            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setTag(position);
            image.setImageResource(emoteIcons[position]);

            viewGroup.addView(image);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }
    }
    Integer[] emoteIcons = {
            R.drawable.emoticons01,
            R.drawable.emoticons02,
            R.drawable.emoticons03,
            R.drawable.emoticons04,
            R.drawable.emoticons05,
            R.drawable.emoticons06,
            R.drawable.emoticons07,
            R.drawable.emoticons08,
            R.drawable.emoticons09,
    };
}
