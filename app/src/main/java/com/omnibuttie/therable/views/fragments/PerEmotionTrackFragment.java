package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
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


public class PerEmotionTrackFragment extends Fragment {

    JournalEntry.EntryType appMode;

    String[] emotionSubStrings;
    TypedArray emotionColors;
    int[] parsedColors;
    private OnFragmentInteractionListener mListener;
    private ListView listView;

    public PerEmotionTrackFragment() {
    }

    // TODO: Rename and change types of parameters
    public static PerEmotionTrackFragment newInstance() {
        PerEmotionTrackFragment fragment = new PerEmotionTrackFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emotionSubStrings = getResources().getStringArray(R.array.moodSubStrings);
        emotionColors = getResources().obtainTypedArray(R.array.emotiveColors);

        List<Integer> emColors = new ArrayList<Integer>();
        for (int i = 0; i < emotionColors.length(); i++) {
            emColors.add(emotionColors.getColor(i, Color.WHITE));
        }
        parsedColors = ArrayUtils.toPrimitive(emColors.toArray(new Integer[0]));

        appMode = ((TherableApp) getActivity().getApplication()).getAppMode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_chart, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);

        List<JournalChartData> periods = ChartLoader.getMonths(appMode);

        ArrayList<PieData> bars = new ArrayList<PieData>();

        for (JournalChartData period : periods) {
            bars.add(processDataIntoPie(period.getWeekstart(), period.getWeekend()));
        }

        ChartDataAdapter adapter = new ChartDataAdapter(getActivity().getApplicationContext(), bars);
        listView.setAdapter(adapter);

        return view;
    }

    private PieData processDataIntoPie(Date start, Date end) {
        DateTime simpleStart = new DateTime(start);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM YYYY");


        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < emotionSubStrings.length; i++) {
            entries.add(new Entry(0, i));
        }

        List<JournalChartData> chartDatas = ChartLoader.getPeriodData(start, end, appMode);

        for (int i = 0; i < chartDatas.size(); i++) {
            JournalChartData chartData = chartDatas.get(i);
            entries.set(chartData.getMood_index(), new Entry(chartData.getMoodcount(), chartData.getMood_index()));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "Mood Ratio:\n" + fmt.print(simpleStart));
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setColors(ColorTemplate.createColors(parsedColors));

        PieData pieData = new PieData(emotionSubStrings, pieDataSet);
        return pieData;

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

    private class ChartDataAdapter extends ArrayAdapter<PieData> {
        private ChartDataAdapter(Context context, List<PieData> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PieData c = getItem(position);

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_piechart, null);
                holder.chart = (PieChart) convertView.findViewById(R.id.chart);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.chart.setDrawLegend(true);
            holder.chart.setDescription("");
            holder.chart.setValueTextColor(Color.WHITE);

            holder.chart.setData(c);
            holder.chart.setCenterText(c.getDataSet().getLabel());
            holder.chart.setHoleRadius(60f);
            holder.chart.setDrawHoleEnabled(true);

            holder.chart.invalidate();
            holder.chart.animateXY(700, 300);
            holder.chart.setDrawYValues(false);
            holder.chart.setDrawXValues(false);


            Legend l = holder.chart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

            return convertView;
        }

        private class ViewHolder {
            PieChart chart;
        }
    }

}
