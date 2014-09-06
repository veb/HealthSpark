package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.JournalEntry;

import java.util.List;


public class WeeklyChartFragment extends Fragment {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_chart, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);

        List<JournalEntry> journalEntryList;
        journalEntryList = JournalEntry.findWithQuery(JournalEntry.class, "select \n" +
                "    strftime('%W', simpledate) WeekNumber,\n" +
                "    max(date(simpledate, 'weekday 0', '-7 day')) WeekStart,\n" +
                "    max(date(simpledate, 'weekday 0', '-1 day')) WeekEnd,\n" +
                "    count(mood_index) as moodcount,\n" +
                "    mood\n" +
                "from JOURNAL_ENTRY\n" +
                "\n" +
                "group by mood_index, WeekNumber\n" +
                "order by date_modified desc");

        Log.e("RRR", "herp" + journalEntryList.size());

        return view;
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
