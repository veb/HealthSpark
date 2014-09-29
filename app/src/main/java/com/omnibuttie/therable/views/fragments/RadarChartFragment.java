package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.dataLoaders.ChartLoader;
import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.views.controls.MultiSpinner;

import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayarvin on 9/12/14.
 */
public class RadarChartFragment extends Fragment {

    JournalEntry.EntryType appMode;

    MultiSpinner spinner;
    String[] emotionSubStrings;
    TypedArray emotionColors;
    int[] parsedColors;
    ArrayList<RadarDataSet> allMoodDataSets;
    ArrayList<String> existingMoods;
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
        View view = inflater.inflate(R.layout.fragmen_journal_radar_chart, container, false);
        spinner = (MultiSpinner) view.findViewById(R.id.spinner);


        mChart = (RadarChart) view.findViewById(R.id.chart1);

        mChart.setWebLineWidth(1.5f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);

        mChart.setDrawYValues(false);
        mChart.setHighlightEnabled(false);

//        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.chart_marker_view);
//        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());
//
//        mChart.setMarkerView(mv);

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
        List<JournalChartData> aggregateData = ChartLoader.getAggregateForYear(new DateTime().getYear(), appMode);

        ArrayList<Entry> entries = new ArrayList<Entry>(12);
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry(0, i));
        }
        ArrayList<Entry> baseEntries = entries;

        allMoodDataSets = new ArrayList<RadarDataSet>();

        int currentMoodIndex = -1;
        if (aggregateData.size() > 1) {
            currentMoodIndex = aggregateData.get(0).getMood_index();
        }

        existingMoods = new ArrayList<String>();
        for (JournalChartData chartData1 : aggregateData) {
            if (currentMoodIndex != chartData1.getMood_index()) {
                existingMoods.add(emotionSubStrings[currentMoodIndex]);
                RadarDataSet set = new RadarDataSet(entries, emotionSubStrings[currentMoodIndex]);
                set.setColor(emotionColors.getColor(currentMoodIndex, Color.DKGRAY));
                set.setDrawFilled(true);
                set.setLineWidth(2f);
                allMoodDataSets.add(set);
                currentMoodIndex = chartData1.getMood_index();
                entries = new ArrayList<Entry>(12);
                for (int i = 0; i < 12; i++) {
                    entries.add(new Entry(0, i));
                }
            }
            entries.set(chartData1.getWeeknumber() - 1, new Entry(chartData1.getMoodcount(), chartData1.getMood_index()));
        }


        // quickhack
        existingMoods.add(emotionSubStrings[currentMoodIndex]);
        RadarDataSet set = new RadarDataSet(entries, emotionSubStrings[currentMoodIndex]);
        set.setColor(emotionColors.getColor(currentMoodIndex, Color.DKGRAY));
        set.setDrawFilled(true);
        set.setLineWidth(2f);
        allMoodDataSets.add(set);
        entries = new ArrayList<Entry>(12);
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry(0, i));
        }

        RadarData data = new RadarData(mMonths, allMoodDataSets);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, existingMoods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setItems(existingMoods, "All Moods", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                displayDataSet(selected);
            }
        });
    }

    public void displayDataSet(boolean[] displayArray) {
        ArrayList<RadarDataSet> displayDataSets = new ArrayList<RadarDataSet>();
        for (int i = 0; i < displayArray.length; i++) {
            if (displayArray[i]) {
                displayDataSets.add(allMoodDataSets.get(i));
            }
        }
        RadarData data = new RadarData(mMonths, displayDataSets);
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
