package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.dataLoaders.ChartLoader;
import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.model.JournalEntry;

import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WeeklyChartFragment extends Fragment {

    String[] emotionSubStrings;
    TypedArray emotionColors;
    int[] parsedColors;
    JournalEntry.EntryType appMode;
    private OnFragmentInteractionListener mListener;
    private ListView listView;

    public WeeklyChartFragment() {
    }

    // TODO: Rename and change types of parameters
    public static WeeklyChartFragment newInstance() {
        WeeklyChartFragment fragment = new WeeklyChartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appMode = ((TherableApp) getActivity().getApplication()).getAppMode();

        switch (appMode) {
            case MOOD:
                emotionSubStrings = getResources().getStringArray(R.array.moodSubStrings);
                emotionColors = getResources().obtainTypedArray(R.array.emotiveColors);
                break;
            case FITNESS:
                emotionSubStrings = getResources().getStringArray(R.array.fitnessActivityStrings);
                emotionColors = getResources().obtainTypedArray(R.array.emotiveColors);
                break;
            case HEALTH:
                emotionSubStrings = getResources().getStringArray(R.array.effectivenessString);
                emotionColors = getResources().obtainTypedArray(R.array.emotiveColors);
                break;
            case PAIN:
                emotionSubStrings = getResources().getStringArray(R.array.painStrings);
                emotionColors = getResources().obtainTypedArray(R.array.emotiveColors);
                break;
        }


        List<Integer> emColors = new ArrayList<Integer>();
        for (int i = 0; i < emotionColors.length(); i++) {
            emColors.add(emotionColors.getColor(i, Color.WHITE));
        }
        parsedColors = ArrayUtils.toPrimitive(emColors.toArray(new Integer[0]));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_chart, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);

        List<JournalChartData> weeks = ChartLoader.getWeeks(appMode);

        ArrayList<BarData> bars = new ArrayList<BarData>();

        for (JournalChartData week : weeks) {
            bars.add(processDataFromWeek(week.getWeekstart(), week.getWeekend()));
        }

        ChartDataAdapter adapter = new ChartDataAdapter(getActivity().getApplicationContext(), bars);
        listView.setAdapter(adapter);

        return view;
    }

    private BarData processDataFromWeek(Date start, Date end) {
        DateTime simpleStart = new DateTime(start);
        DateTime simpleEnd = new DateTime(end);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd, YYYY");

        Log.e("BARCHART", "processing week " + fmt.print(simpleStart));

        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        for (int i = 0; i < emotionSubStrings.length; i++) {
            barEntries.add(new BarEntry(0, i));
        }

        List<JournalChartData> chartDatas = ChartLoader.getPeriodData(start, end, appMode);

        for (int i = 0; i < chartDatas.size(); i++) {
            JournalChartData chartData = chartDatas.get(i);
            barEntries.set(chartData.getMood_index(), new BarEntry(chartData.getMoodcount(), chartData.getMood_index()));
        }

        BarDataSet dataSet = new BarDataSet(barEntries, fmt.print(simpleStart) + " to " + fmt.print(simpleEnd));
        dataSet.setBarSpacePercent(15f);

        switch (((TherableApp) getActivity().getApplication()).getAppMode()) {
            case MOOD:
                dataSet.setColors(ColorTemplate.createColors(parsedColors));
                break;
            case FITNESS:
            case HEALTH:
            case PAIN:
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS, getActivity().getApplicationContext());
                break;
        }

        dataSet.setBarShadowColor(Color.rgb(203, 203, 203));
        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
        sets.add(dataSet);

        BarData cd = new BarData(emotionSubStrings, sets);

        Log.e("BARCHART", "end processing week " + fmt.print(simpleStart));
        return cd;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private class ChartDataAdapter extends ArrayAdapter<BarData> {
        private ChartDataAdapter(Context context, List<BarData> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BarData c = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.chart);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.chart.setDrawLegend(true);
            holder.chart.setDescription("");
            holder.chart.setDrawVerticalGrid(false);
            holder.chart.setDrawGridBackground(false);
            holder.chart.setValueTextColor(Color.WHITE);

            XLabels xl = holder.chart.getXLabels();
            xl.setCenterXLabelText(true);
            xl.setTextSize(5.0f);
            xl.setAdjustXLabels(false);
            xl.setPosition(XLabels.XLabelPosition.BOTTOM);
            xl.setAvoidFirstLastClipping(false);

            YLabels yl = holder.chart.getYLabels();


            holder.chart.setData(c);

            holder.chart.invalidate();
            holder.chart.animateY(700);

            return convertView;
        }

        private class ViewHolder {
            BarChart chart;
        }
    }

}
