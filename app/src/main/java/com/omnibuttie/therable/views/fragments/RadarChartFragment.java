package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.dataLoaders.ChartLoader;
import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.views.drawables.MyMarkerView;

import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayarvin on 9/12/14.
 */
public class RadarChartFragment extends Fragment {

    String[] emotionSubStrings;
    TypedArray emotionColors;
    int[] parsedColors;
    private OnFragmentInteractionListener mListener;
    private RadarChart mChart;
    private String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    public RadarChartFragment() {
    }

    // TODO: Rename and change types of parameters
    public static RadarChartFragment newInstance() {
        RadarChartFragment fragment = new RadarChartFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_journal_radar_chart, container, false);
        mChart = (RadarChart) view.findViewById(R.id.chart1);

        mChart.setWebLineWidth(1.5f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);

        mChart.setDrawYValues(false);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.chart_marker_view);
        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        mChart.setMarkerView(mv);

        XLabels xl = mChart.getXLabels();
        xl.setTextSize(9f);

        YLabels yl = mChart.getYLabels();
        yl.setLabelCount(5);
        yl.setTextSize(9f);

        mChart.animateXY(1500, 1500);

//        Legend l = mChart.getLegend();
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(5f);

        createRadarChartData();

        return view;
    }

    private void createRadarChartData() {
        List<JournalChartData> aggregateData = ChartLoader.getAggregateForYear(new DateTime().getYear());


        ArrayList<Entry> entries = new ArrayList<Entry>(12);
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry(0, i));
        }
        ArrayList<Entry> baseEntries = entries;

        ArrayList<RadarDataSet> sets = new ArrayList<RadarDataSet>();

        int currentDataGroupID = -1;
        if (aggregateData.size() > 1) {
            currentDataGroupID = aggregateData.get(0).getWeeknumber();
        }
        for (JournalChartData chartData1 : aggregateData) {
            if (currentDataGroupID != chartData1.getWeeknumber()) {
                RadarDataSet set = new RadarDataSet(entries, emotionSubStrings[currentDataGroupID]);
                set.setColor(emotionColors.getColor(currentDataGroupID, Color.DKGRAY));
                set.setDrawFilled(true);
                set.setLineWidth(2f);
                sets.add(set);
                currentDataGroupID = chartData1.getWeeknumber();
                entries = new ArrayList<Entry>(12);
                for (int i = 0; i < 12; i++) {
                    entries.add(new Entry(0, i));
                }
            }
//             entries.set(chartData1.getWeeknumber() - 1, new Entry(chartData1.getMoodcount(), chartData1.getWeeknumber() - 1));
//            entries.set(chartData1.getWeeknumber() - 1, new Entry(chartData1.getMoodcount(), chartData1.getMood_index()));
            entries.set(chartData1.getMood_index(), new Entry(chartData1.getMoodcount(), chartData1.getMood_index()));

        }

        RadarData data = new RadarData(mMonths, sets);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
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


}
