package com.omnibuttie.therable.views.CalendarGrid;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.avwave.colorcalendar.MonthFragment;
import com.avwave.colorcalendar.MyCalendarProperties;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by rayarvin on 6/29/14.
 */
public class MyMonthFragment extends MonthFragment {
    TypedArray emotiveColors;
    List<JournalEntry> journalEntries;

    public MyMonthFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public MyMonthFragment(int year, int month) {
        super(year, month);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridAdapter = new MyMonthAdapter();

        int count = mGridAdapter.getCount();
        Date startDate = getDateAtPosition(0);
        String query = "select e.* from dates d left outer join journal_entry e on e.simpledate = d.date where strftime('%Y-%m-%d', d.date) >= ? limit ?;";
        String dateformat = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
        journalEntries = JournalEntry.findWithQuery(JournalEntry.class, query, dateformat, String.valueOf(count));

        mGridView.setAdapter(mGridAdapter);
        emotiveColors = getResources().obtainTypedArray(R.array.emotiveColors);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyCalendarProperties prop = new MyCalendarProperties(getActivity());


        prop.setOutOfBoundsNumberFontColor(getResources().getColor(R.color.LightSlateGray));
        prop.setNumberFontColor(getResources().getColor(R.color.DarkSlateGray));
        prop.setDayNamesVisible(false);
        super.setProperties(prop);
        super.onCreate(savedInstanceState);
    }

    class MyMonthAdapter extends MonthAdapter {
        @Override
        public int getCount() {
            int count;
            if (mProperties.isDayNamesVisible()) {
                count = (getHowManyWeeksInMonth() + 1) * DAYS_IN_WEEK;
            } else {
                count = (getHowManyWeeksInMonth()) * DAYS_IN_WEEK;
            }
            return count;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("cal", getDateAtPosition(position).toString());
            if (mProperties.isDayNamesVisible() && position < 7) {
                return getWeekDayName(position, convertView);
            } else {
                convertView = getCurrentView(position, convertView);
            }
            if (mProperties.isDayNamesVisible()) {
                convertView = getCurrentView(position - 7, convertView);
            }
            setViewWidthHeight(convertView);

//            TODO: query all group by date
            JournalEntry entry = journalEntries.get(position);

            int emotiveColor = Color.WHITE;

            if (entry.getDateModified() != null) {
                emotiveColor = emotiveColors.getColor(entry.getMood(), Color.WHITE);
            }

            Drawable backColor = new ColorDrawable(emotiveColor);

            Drawable shadow = new ColorDrawable(Color.parseColor("#333333"));

            Drawable[] layers = new Drawable[]{shadow, backColor};

            LayerDrawable drawable = new LayerDrawable(layers);
            drawable.setLayerInset(0,   0,0,0,0);
            drawable.setLayerInset(1,   1,1,1,1);
            convertView.setBackground(drawable);

            return convertView;
        }
    }

}
