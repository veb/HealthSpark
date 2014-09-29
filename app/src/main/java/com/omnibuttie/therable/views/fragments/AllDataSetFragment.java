package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.dataLoaders.ChartLoader;
import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.model.JournalEntry;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AllDataSetFragment extends Fragment {
    JournalEntry.EntryType appMode;
    String[] emotionSubStrings;
    TypedArray emotionColors;
    private LineChart lineChart;
    private OnFragmentInteractionListener mListener;
    private int[] mColors = new int[]{R.color.vordiplom_1, R.color.vordiplom_2, R.color.vordiplom_3, R.color.vordiplom_4, R.color.vordiplom_5};

    public AllDataSetFragment() {
        // Required empty public constructor
    }

    public static AllDataSetFragment newInstance() {
        AllDataSetFragment fragment = new AllDataSetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_data_set, container, false);
        lineChart = (LineChart) view.findViewById(R.id.chart1);
        lineChart.setDrawYValues(true);
        lineChart.setHighlightEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.setDragScaleEnabled(true);
        lineChart.setPinchZoom(false);

        stubGraph();

        Legend l = lineChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);


        appMode = ((TherableApp) getActivity().getApplication()).getAppMode();

        return view;
    }

    void stubGraph() {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            xVals.add((i) + "");
        }

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

        for (int z = 0; z < 3; z++) {

            ArrayList<Entry> values = new ArrayList<Entry>();

            for (int i = 0; i < 1000; i++) {
                double val = (Math.random() * 100) + 3;
                values.add(new Entry((float) val, i));
            }

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
            d.setLineWidth(2.5f);
            d.setCircleSize(4f);

            int color = getResources().getColor(mColors[z % mColors.length]);
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        // make the first DataSet dashed
        dataSets.get(0).enableDashedLine(10, 10, 0);

        LineData data = new LineData(xVals, dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    void setupGraph() {
        List<JournalChartData> aggregateData = ChartLoader.getEntireDataset(appMode);

        int startdateAsInt = 0;
        int endDateAsInt = 0;

        ArrayList<Integer> datapointCountPerSet = new ArrayList<Integer>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

        ArrayList<Entry> dataset = new ArrayList<Entry>();

        int currentDataSet = -1;
        if (aggregateData.size() > 1) {
            currentDataSet = aggregateData.get(0).getMood_index();
            endDateAsInt = aggregateData.get(0).getWeeknumber();
            startdateAsInt = aggregateData.get(0).getWeeknumber();
        }

        Random rand = new Random();
        for (JournalChartData dataPoint : aggregateData) {
            if (currentDataSet != dataPoint.getMood_index()) {
                datapointCountPerSet.add(dataset.size());
                LineDataSet d = new LineDataSet(dataset, emotionSubStrings[currentDataSet]);

                int color = getResources().getColor(mColors[currentDataSet % mColors.length]);
                d.setColor(color);
                d.setCircleColor(color);
                d.setDrawFilled(true);
                d.setLineWidth(2.0f);
                d.setCircleSize(3f);
                dataSets.add(d);
                dataset = new ArrayList<Entry>();
                currentDataSet = dataPoint.getMood_index();

            }

            if (startdateAsInt > dataPoint.getWeeknumber()) {
                startdateAsInt = dataPoint.getWeeknumber();
            }
            if (endDateAsInt < dataPoint.getWeeknumber()) {
                endDateAsInt = dataPoint.getWeeknumber();
            }
//            dataset.add(new Entry(dataPoint.getMoodcount(), dataPoint.getWeeknumber()));
            dataset.add(new Entry((float) rand.nextInt(10), dataPoint.getWeeknumber()));
        }


        LocalDate startDate = dateFromElements(startdateAsInt);
        LocalDate endDate = dateFromElements(endDateAsInt);
//        int days = Days.daysBetween(startDate, endDate).getDays();
//        for (int i=0; i < days; i++) {
//            LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);
//            xVals.add(d.toString());
//        }

        for (int i = startdateAsInt; i <= endDateAsInt; i++) {
            xVals.add(String.valueOf(i));
        }

        LineData data = new LineData(xVals, dataSets);
        lineChart.setData(data);
        lineChart.invalidate();


    }

    private LocalDate dateFromElements(int dateInt) {
        DateTime date;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
        date = formatter.parseDateTime(String.valueOf(dateInt));

        LocalDate localDate = new LocalDate(date);

        return localDate;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        public void onFragmentInteraction(Uri uri);
    }

}
