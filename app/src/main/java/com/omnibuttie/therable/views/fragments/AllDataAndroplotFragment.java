package com.omnibuttie.therable.views.fragments;


import android.database.Cursor;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.dataLoaders.ChartLoader;
import com.omnibuttie.therable.dataLoaders.StatusLoader;
import com.omnibuttie.therable.views.controls.MultiSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllDataAndroplotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllDataAndroplotFragment extends Fragment {
    MultiSpinner spinner;
    XYPlot plot1;

    public AllDataAndroplotFragment() {
        // Required empty public constructor
    }

    public static AllDataAndroplotFragment newInstance() {
        AllDataAndroplotFragment fragment = new AllDataAndroplotFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_data_androplot, container, false);
        plot1 = (XYPlot) view.findViewById(R.id.chart1);
        spinner = (MultiSpinner) view.findViewById(R.id.entryTypeSpinner);

        setupGraph(new boolean[]{true});
        setupFilterSpinners();

        return view;
    }

    void setupFilterSpinners() {
        spinner.setItems(new StatusLoader(getActivity()).getStatusesForEntryType(null), "All Moods", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                setupGraph(selected);
            }
        });
    }

    void setupGraph(boolean[] selected) {
        ChartLoader chartLoader = new ChartLoader(this.getActivity().getApplicationContext());
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                Cursor c = chartLoader.getDatasetCursor((long) i, null, null, 0);
                int j = 0;
                ArrayList<Integer> yVals = new ArrayList<Integer>();
                ArrayList<Long> xVals = new ArrayList<Long>();
                while (c.moveToNext()) {
                    xVals.add(c.getLong(1));
                    yVals.add(c.getInt(3));
                }

                XYSeries series = new SimpleXYSeries(xVals, yVals, "dataset" + i);
                LineAndPointFormatter series1Format = new LineAndPointFormatter();
                series1Format.setPointLabelFormatter(new PointLabelFormatter());
                series1Format.configure(getActivity().getApplicationContext(),
                        R.xml.line_point_formatter_plf1);

                plot1.addSeries(series, series1Format);
            }
        }

//        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
//        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
//
//        // create our series from our array of nums:
//        XYSeries series1 = new SimpleXYSeries(
//                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
//                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
//                "Series1");

        plot1.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        plot1.getGraphWidget().getDomainGridLinePaint().setColor(Color.BLACK);
        plot1.getGraphWidget().getDomainGridLinePaint().
                setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        plot1.getGraphWidget().getRangeGridLinePaint().setColor(Color.BLACK);
        plot1.getGraphWidget().getRangeGridLinePaint().
                setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        plot1.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        plot1.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);

        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 100, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                Color.rgb(100, 200, 0), null);                // fill color
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);

        lineFill.setShader(new LinearGradient(0, 0, 200, 200, Color.WHITE, Color.GREEN, Shader.TileMode.CLAMP));

        LineAndPointFormatter formatter =
                new LineAndPointFormatter(Color.rgb(0, 0, 0), Color.BLUE, Color.RED, null);
        formatter.setFillPaint(lineFill);
        plot1.getGraphWidget().setPaddingRight(2);


        // customize our domain/range labels
        plot1.setDomainLabel("Year");
        plot1.setRangeLabel("# of Sightings");

        // get rid of decimal points in our range labels:
        plot1.setRangeValueFormat(new DecimalFormat("0"));


    }
}
