package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
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
import com.omnibuttie.therable.dataLoaders.StatusLoader;
import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.provider.journalentry.EntryType;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


public class WeeklyChartFragment extends Fragment {

    List<StatusLoader.StatusMap> statusMaps;
    ArrayList<Long> statusIDS;
    ArrayList<String> statusNames;
    TypedArray emotionColors;
    int[] parsedColors;
    EntryType appMode;
    private OnFragmentInteractionListener mListener;
    private ListView listView;
    ChartLoader loader;

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

        statusMaps = new StatusLoader(getActivity()).getStatusesForEntryType(appMode);
        statusNames = new ArrayList<String>();
        statusIDS = new ArrayList<Long>();
        for (StatusLoader.StatusMap stats:statusMaps) {
            statusNames.add(stats.getStatusName());
            statusIDS.add(stats.getStatusID());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_chart, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);

        setupGraphs();
        return view;

    }

    private void setupGraphs() {
        loader = new ChartLoader(getActivity());
        Cursor weekCursor = loader.getWeeksCursor(appMode);

        ArrayList<BarData> barDataSets = new ArrayList<BarData>();
        while(weekCursor.moveToNext()) {
            barDataSets.add(processDataFromWeek(new LocalDate(weekCursor.getLong(2)), new LocalDate(weekCursor.getLong(3))));
        }
        ChartDataAdapter adapter = new ChartDataAdapter(getActivity().getApplicationContext(), barDataSets);
        listView.setAdapter(adapter);
    }

    private BarData processDataFromWeek(LocalDate start, LocalDate end) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd, YYYY");

        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

        for (int i = 0; i < statusMaps.size(); i++) {
            barEntries.add(new BarEntry(0, i));
        }

        Cursor cursor = loader.getPeriodDataCursor(start, end, appMode);
        while(cursor.moveToNext()) {
            int idx = statusIDS.indexOf(cursor.getLong(1));
            barEntries.set(idx, new BarEntry(cursor.getInt(2), idx));
        }

        BarDataSet dataSet = new BarDataSet(barEntries, fmt.print(start) + " to " + fmt.print(end));
        dataSet.setBarSpacePercent(15f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS, getActivity());

        dataSet.setBarShadowColor(Color.rgb(203, 203, 203));
        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
        sets.add(dataSet);


        BarData cd = new BarData(statusNames, sets);

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
