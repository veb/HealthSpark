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

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.views.Composer_alternate;

/**
 * Created by rayarvin on 7/14/14.
 */
public class EmoteGridFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private GridView mGridView;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Composer_alternate activity = (Composer_alternate)getActivity();
        activity.appendMood(parent.getItemAtPosition(position).toString());
        this.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_emotegrid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGridView = (GridView)view.findViewById(R.id.asset_grid);
        mGridView.setOnItemClickListener(this);

        mGridView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.grid_item,
                getResources().getStringArray(R.array.moodSubStrings)));

        mGridView.setOnItemClickListener(this);

    }
}
