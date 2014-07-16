package com.omnibuttie.therable.views.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.views.Composer_alternate;

/**
 * Created by rayarvin on 7/14/14.
 */
public class EmoteGridFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private RadioGroup radioGroup;
    View superView;

    int defaultIntensity;


    private static final String DEF_INTENSITY_PARAM = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Composer_alternate activity = (Composer_alternate)getActivity();
        int radioID = radioGroup.getCheckedRadioButtonId();
        RadioButton butt = (RadioButton) superView.findViewById(radioID);

        int idx = radioGroup.indexOfChild(butt);
        activity.appendMood(parent.getItemAtPosition(position).toString(), idx);

        this.dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            defaultIntensity = getArguments().getInt(DEF_INTENSITY_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_emotegrid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        superView = view;

        radioGroup = (RadioGroup)view.findViewById(R.id.toolRadio);
        ((RadioButton)radioGroup.getChildAt(defaultIntensity)).setChecked(true);

        gridView = (GridView)view.findViewById(R.id.asset_grid);
        gridView.setOnItemClickListener(this);

        gridView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.grid_item,
                getResources().getStringArray(R.array.moodSubStrings)));

        gridView.setOnItemClickListener(this);

    }

    public static EmoteGridFragment newInstance(int param1) {
        EmoteGridFragment fragment = new EmoteGridFragment();
        Bundle args = new Bundle();
        args.putInt(DEF_INTENSITY_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public EmoteGridFragment() {
        // Required empty public constructor
    }
}
