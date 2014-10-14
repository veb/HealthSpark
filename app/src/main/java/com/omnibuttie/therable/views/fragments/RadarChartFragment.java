package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.dataLoaders.ChartLoader;
import com.omnibuttie.therable.dataLoaders.StatusLoader;
import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.provider.journalentry.EntryType;
import com.omnibuttie.therable.views.controls.MultiSpinner;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayarvin on 9/12/14.
 */
public class RadarChartFragment extends Fragment {

    EntryType appMode;

    MultiSpinner multiSpinner;
    Spinner rangeSpinner;

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

    private int[] mColors = new int[]{
            R.color.colorful_1,
            R.color.colorful_2,
            R.color.colorful_3,
            R.color.colorful_4,
            R.color.colorful_5,
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_journal_radar_chart, container, false);
        multiSpinner = (MultiSpinner) view.findViewById(R.id.entryTypeSpinner);
        rangeSpinner = (Spinner) view.findViewById(R.id.rangeSpinner);

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

        setupGraph(null, 0);
        setupFilterSpinners();

        return view;
    }

    List<StatusLoader.StatusMap> statusnames;
    boolean[] multiSpinnerSelected = new boolean[]{};
    int rangeSpinnerSelected = new LocalDate().getYear();

    void setupFilterSpinners() {
        statusnames = new StatusLoader(getActivity()).getStatusesForEntryType(appMode);
        List<String> statusStrings = new ArrayList<String>();
        for (StatusLoader.StatusMap statuses: statusnames) {
            statusStrings.add(statuses.getStatusName());
        }
        multiSpinner.setItems(statusStrings, "Select...", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                multiSpinnerSelected = selected;
                setupGraph(multiSpinnerSelected, rangeSpinnerSelected);
            }
        });


        ChartLoader chartLoader = new ChartLoader(this.getActivity());

        Cursor yearsCursor = chartLoader.getYearsCursor(appMode);
        SimpleCursorAdapter yearsAdapter = new SimpleCursorAdapter(this.getActivity(), android.R.layout.simple_spinner_item, yearsCursor, new String[]{"WEEKNUMBER"}, new int[]{android.R.id.text1},0);
        rangeSpinner.setAdapter(yearsAdapter);
        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor)parent.getSelectedItem();
                rangeSpinnerSelected = Integer.parseInt(c.getString(c.getColumnIndex("WEEKNUMBER")));
                setupGraph(multiSpinnerSelected, rangeSpinnerSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void setupGraph(boolean[] selected, int yearRange) {
        if (selected == null || yearRange == 0) {
            return;
        }
        ChartLoader chartLoader = new ChartLoader(this.getActivity());

        ArrayList<RadarDataSet> radarDataSets = new ArrayList<RadarDataSet>();
        int k = 0;
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                long statusID = statusnames.get(i).getStatusID();

                ArrayList<Entry> entries = new ArrayList<Entry>();
                for (int j = 0; j < 12; j++) {
                    entries.add(new Entry(0f, j));
                }

                Cursor monthliesCursor = chartLoader.getMonthliesForYear(yearRange, statusID);
                while (monthliesCursor.moveToNext()) {
                    entries.set(monthliesCursor.getInt(4) - 1, new Entry(monthliesCursor.getInt(3), monthliesCursor.getInt(2)));
                }

                RadarDataSet set = new RadarDataSet(entries, statusnames.get(i).getStatusName());
                int incol = getResources().getColor(mColors[k % mColors.length]);
                set.setColor(incol);
                set.setDrawFilled(true);
                set.setLineWidth(2f);

                radarDataSets.add(set);
                k ++;
            }
        }

        if (radarDataSets.size() > 0) {
            RadarData data = new RadarData(mMonths, radarDataSets);
            mChart.setData(data);
            mChart.highlightValues(null);
            mChart.invalidate();
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
        public void onFragmentInteraction(String id);
    }


}
