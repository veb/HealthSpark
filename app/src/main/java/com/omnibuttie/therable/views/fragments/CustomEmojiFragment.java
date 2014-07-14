package com.omnibuttie.therable.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnibuttie.therable.R;
import com.rockerhieu.emojicon.EmojiconsFragment;

/**
 * Created by rayarvin on 7/12/14.
 */
public class CustomEmojiFragment extends EmojiconsFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        v.findViewById(R.id.emojis_tab).setVisibility(View.GONE);
        return v;
    }
}
