package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.views.cards.EntryCard;
import com.tyczj.extendedcalendarview.Day;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements ExtendedCalendarView.OnDayClickListener {
    private OnFragmentInteractionListener mListener;

    private ExtendedCalendarView calendarView;
    private JournalCursorFragment journalFragment;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        DateTime simplifiedDate = new DateTime(new Date());
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
        journalFragment = JournalCursorFragment.newInstance(EntryCard.VIEW_BY_DATE, fmt.print(simplifiedDate), true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = (ExtendedCalendarView) view.findViewById(R.id.calendar);
        calendarView.setGesture(ExtendedCalendarView.LEFT_RIGHT_GESTURE);
        calendarView.setOnDayClickListener(this);

        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.listView, journalFragment);
        t.commit();
        return view;
    }

    @Override
    public void onDayClicked(AdapterView<?> adapter, View view, int position, long id, Day day) {
        Log.e("DATEPICK", day.getDate());
        journalFragment.filterDateByDateString(day.getDate());
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
