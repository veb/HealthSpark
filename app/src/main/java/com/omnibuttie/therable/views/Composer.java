package com.omnibuttie.therable.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.avwave.looperPager.PageFadeTransformer;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.JournalEntry;

public class Composer extends Activity implements AdapterView.OnItemSelectedListener {

    TypedArray emoticons;
    TypedArray emoticonThumbs;
    TypedArray emotiveColors;

    String emotionStrings;
    View v;

    View statusEditor;
    ImageView statusThumbnail;
    EditText statusText;

    View emoteSelector;
    ImageAdapter adapter;
    ViewPager statusPager;
    SeekBar statusSeekBar;

    JournalEntry entry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long journalID = getIntent().getLongExtra("JournalID", -1);
        if (journalID != -1); {
            entry = JournalEntry.findById(JournalEntry.class, journalID);
        }

        setContentView(R.layout.activity_composer);

        emoticons = getResources().obtainTypedArray(R.array.emoticons);
        emoticonThumbs = getResources().obtainTypedArray(R.array.emoticonthumbs);
        emotiveColors = getResources().obtainTypedArray(R.array.emotiveColors);

        statusEditor = findViewById(R.id.statusEditor);
        statusThumbnail = (ImageView) findViewById(R.id.statusThumbnail);
        statusText = (EditText) findViewById(R.id.statusEditText);


        emoteSelector = findViewById(R.id.emoteSelector);
        statusSeekBar = (SeekBar) findViewById(R.id.seekBar);
        adapter = new ImageAdapter(this);

        statusPager = (ViewPager) findViewById(R.id.emotePager);
        statusPager.setAdapter(adapter);
        statusPager.setPageTransformer(true, new PageFadeTransformer());

        statusPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                statusSeekBar.getProgressDrawable().setColorFilter(getResources().getColor(colors[position]), PorterDuff.Mode.SRC_IN);
//                statusSeekBar.getThumb().setColorFilter(getResources().getColor(colors[position]), PorterDuff.Mode.SRC_IN);

                setStatusColors(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        fillCard();
    }

    public void setStatusColors(int position) {
        statusSeekBar.getProgressDrawable().setColorFilter(emotiveColors.getColor(position, Color.BLACK), PorterDuff.Mode.SRC_IN);
        statusSeekBar.getThumb().setColorFilter(emotiveColors.getColor(position, Color.BLACK), PorterDuff.Mode.SRC_IN);
        statusThumbnail.setImageResource(emoticons.getResourceId(position, -1));
        statusPager.setCurrentItem(position);
    }

    public void fillCard() {
        onEditTextClick(null);
        if (entry == null){
            setStatusColors(8);
            return;
        }
        statusText.setText(entry.getContent());
        setStatusColors(entry.getMood());
        statusSeekBar.setProgress(entry.getIntensity());
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
            doPost();
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

    public void onThumbnailClick (View v) {
        statusEditor.setVisibility(View.GONE);
        emoteSelector.setVisibility(View.VISIBLE);

        statusText.clearFocus();
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(statusText.getWindowToken(), 0);
    }

    public void onEditTextClick(View v) {
        statusEditor.setVisibility(View.VISIBLE);
        emoteSelector.setVisibility(View.GONE);

    }

    public void doPost() {
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setMood(statusPager.getCurrentItem());
        journalEntry.setContent(statusText.getText().toString());
        journalEntry.setIntensity(statusSeekBar.getProgress());
        journalEntry.setArchived(false);
        journalEntry.save();
        finish();
    }

    public class ImageAdapter extends PagerAdapter {
        private Context ctx;

        public ImageAdapter(Context c) {
            ctx = c;
        }

        public int getCount() {
            return emotiveColors.length();
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
            image.setImageResource(emoticons.getResourceId(position, -1));

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditTextClick(v);
                    statusText.setFocusableInTouchMode(true);
                    statusText.setFocusable(true);
                    statusText.requestFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(statusText, InputMethodManager.SHOW_IMPLICIT);
                }
            });


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

}
