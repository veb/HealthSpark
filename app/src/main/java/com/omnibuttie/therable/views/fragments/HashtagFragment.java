package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.omnibuttie.therable.R;

import com.omnibuttie.therable.views.fragments.dummy.DummyContent;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class HashtagFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public static ArrayList<String> messagesList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView messagesListView;
    private EditText messageEditText;
    private Button addButton;


    // TODO: Rename and change types of parameters
    public static HashtagFragment newInstance() {
        HashtagFragment fragment = new HashtagFragment();
        return fragment;
    }

    public HashtagFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hashtag, container, false);
        messageEditText = (EditText)view.findViewById(R.id.messageEditText);
        messagesListView = (ListView)view.findViewById(R.id.messagesListView);
        addButton = (Button)view.findViewById(R.id.addButton);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, messagesList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView hashView;
                if (convertView == null) {
                    hashView = new TextView(getActivity());
                } else {
                    hashView = (TextView)convertView;
                }

                String message = messagesList.get(position);
                hashView.setText(message);

                Pattern tagMatcher = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");

                String newActivityURL = "content://com.omnibuttie.therable.views.TagDetailsActivity/";

                Linkify.addLinks(hashView, tagMatcher, newActivityURL);
                return hashView;
            }
        };

        messagesListView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                if (message.trim() != "") {
                    messagesList.add(0, message);
                    adapter.notifyDataSetChanged();
                    messageEditText.setText("");
                }
            }
        });

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
