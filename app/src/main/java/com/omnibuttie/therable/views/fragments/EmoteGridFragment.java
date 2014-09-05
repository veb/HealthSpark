package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.views.Composer_alternate;
import com.omnibuttie.therable.views.drawables.LeftBorderDrawable;

/**
 * Created by rayarvin on 7/14/14.
 */
public class EmoteGridFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private static final String DEF_INTENSITY_PARAM = "param1";
    View superView;
    int defaultIntensity;
    private ListView listView;
    private RadioGroup radioGroup;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public EmoteGridFragment() {
        // Required empty public constructor
    }

    public static EmoteGridFragment newInstance(int param1) {
        EmoteGridFragment fragment = new EmoteGridFragment();
        Bundle args = new Bundle();
        args.putInt(DEF_INTENSITY_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Composer_alternate activity = (Composer_alternate) getActivity();
        int radioID = radioGroup.getCheckedRadioButtonId();
        RadioButton butt = (RadioButton) superView.findViewById(radioID);

        int idx = radioGroup.indexOfChild(butt);
        activity.appendMood(parent.getItemAtPosition(position).toString(), idx, position);

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

        radioGroup = (RadioGroup) view.findViewById(R.id.toolRadio);
        ((RadioButton) radioGroup.getChildAt(defaultIntensity)).setChecked(true);

        listView = (ListView) view.findViewById(R.id.asset_grid);
        listView.setOnItemClickListener(this);

        listView.setAdapter(new EmoteSelectAdapter(getActivity(), getResources().getStringArray(R.array.moodQuestion), getResources().getStringArray(R.array.moodSubStrings)));

        listView.setOnItemClickListener(this);

    }
}

class EmoteSelectAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] questions;
    private final String[] values;
    TypedArray cardBorderColors;
    TypedArray cardBackgroundColors;

    public EmoteSelectAdapter(Context context, String[] questions, String[] values) {
        super(context, R.layout.emote_select_row, values);
        this.context = context;
        this.values = values;
        this.questions = questions;
        cardBorderColors = getContext().getResources().obtainTypedArray(R.array.emotiveColors);
        cardBackgroundColors = getContext().getResources().obtainTypedArray(R.array.lightEmotiveColors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.emote_select_row, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.tvQuestion = (TextView) convertView.findViewById(R.id.emoteQuestion);
            viewHolder.tvMoodText = (TextView) convertView.findViewById(R.id.emoteSubText);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        viewHolder.tvQuestion.setText(questions[position]);
        viewHolder.tvMoodText.setText(values[position]);

        convertView.setBackground(new LeftBorderDrawable(cardBorderColors.getColor(position, Color.WHITE), cardBackgroundColors.getColor(position, Color.WHITE)));
        return convertView;

    }

    static class ViewHolderItem {
        TextView tvQuestion;
        TextView tvMoodText;
    }

}
