package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.dataLoaders.ChartLoader;
import com.omnibuttie.therable.dataLoaders.StatusLoader;
import com.omnibuttie.therable.provider.journalentry.EntryType;
import com.omnibuttie.therable.views.controls.MultiSpinner;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class AllDataSetFragment extends Fragment {
    public static final String MULTI_SPINNER_SELECTED = "multiSpinnerSelected";
    public static final String RANGE_SPINNER_SELECTED = "rangeSpinnerSelected";
    EntryType appMode;
    String[] emotionSubStrings;
    TypedArray emotionColors;

    MultiSpinner multiSpinner;
    Spinner rangeSpinner;

    List<String> statusnames;


    boolean[] multiSpinnerSelected = new boolean[]{};
    int rangeSpinnerSelected;

    private LineChart lineChart;
    private OnFragmentInteractionListener mListener;
    private int[] mColors = new int[]{
            R.color.colorful_1,
            R.color.colorful_2,
            R.color.colorful_3,
            R.color.colorful_4,
            R.color.colorful_5,
    };

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


        appMode = ((TherableApp) getActivity().getApplication()).getAppMode();

        switch (appMode) {
            case CBT:
                emotionSubStrings = getResources().getStringArray(R.array.moodSubStrings);
                emotionColors = getResources().obtainTypedArray(R.array.emotiveColors);
                break;
            case FITNESS:
                emotionSubStrings = getResources().getStringArray(R.array.fitnessActivityStrings);
                emotionColors = getResources().obtainTypedArray(R.array.emotiveColors);
                break;
            case MEDICAL:
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
        lineChart.setDrawYValues(false);
        lineChart.setHighlightEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.setDragScaleEnabled(true);
        lineChart.setPinchZoom(false);

        multiSpinner = (MultiSpinner) view.findViewById(R.id.entryTypeSpinner);
        rangeSpinner = (Spinner) view.findViewById(R.id.rangeSpinner);


        appMode = ((TherableApp) getActivity().getApplication()).getAppMode();

        setupFilterSpinners();

        if (savedInstanceState != null) {
            rangeSpinnerSelected = savedInstanceState.getInt(RANGE_SPINNER_SELECTED);
            multiSpinnerSelected = savedInstanceState.getBooleanArray(MULTI_SPINNER_SELECTED);
            setupGraph(multiSpinnerSelected, rangeSpinnerSelected);
        }

        return view;
    }

    void setupFilterSpinners() {
        statusnames = new StatusLoader(getActivity()).getStatusesForEntryType(null);
        multiSpinner.setItems(statusnames, "All Moods", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                multiSpinnerSelected = selected;
                setupGraph(multiSpinnerSelected, rangeSpinnerSelected);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.rangeFilters, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        rangeSpinner.setAdapter(adapter);

        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rangeSpinnerSelected = rangeSpinner.getSelectedItemPosition();
                setupGraph(multiSpinnerSelected, rangeSpinnerSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void setupGraph(boolean[] selected, int rangeposition) {
        ChartLoader chartLoader = new ChartLoader(this.getActivity().getApplicationContext());


        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM-dd-YY");
        DateTimeFormatter dtfIn = DateTimeFormat.forPattern("YYYY-MM-dd");
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        List<LocalDate> lDateRange = chartLoader.getMaxMinDate(null, null);

        List<LocalDate> dates = new ArrayList<LocalDate>();
        int days = Days.daysBetween(lDateRange.get(0), lDateRange.get(1)).getDays();

        for (int ds = 0; ds < days; ds++) {
            LocalDate d = lDateRange.get(0).withFieldAdded(DurationFieldType.days(), ds);
            dates.add(d);
            xVals.add(dtfOut.print(d));
        }

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

        int j = 0;
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                yVals = new ArrayList<Entry>(days);

                Cursor c = chartLoader.getDatasetCursor((long) i, null, null, rangeposition);
                if (rangeposition == 0) {
                    for (int ds1 = 0; ds1 < days; ds1++) {
                        yVals.add(new Entry(0f, ds1));
                    }
                }
                while (c.moveToNext()) {
                    int idx = dates.indexOf(new LocalDate(dtfIn.parseLocalDate(c.getString(4))));
                    if (idx >= 0) {
                        if (rangeposition == 0) {
                            yVals.set(idx, new Entry(c.getInt(3), idx));
                        } else {
                            yVals.add(new Entry(c.getInt(3), idx));
                        }
                    }

                }
                LineDataSet dataSet = new LineDataSet(yVals, statusnames.get(i));


                int incol = getResources().getColor(mColors[j % mColors.length]);

                Log.e("idxs", String.valueOf(incol));
                dataSet.setDrawFilled(true);
                dataSet.setFillColor(incol);
                dataSet.setFillAlpha(10);
                dataSet.setColor(incol);
                dataSet.setCircleColor(incol);

                dataSets.add(dataSet);
                j++;
            }
        }

        if (dataSets.size() > 0) {
            LineData data = new LineData(xVals, dataSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }


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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBooleanArray(MULTI_SPINNER_SELECTED, multiSpinnerSelected);
        outState.putInt(RANGE_SPINNER_SELECTED, rangeSpinnerSelected);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
