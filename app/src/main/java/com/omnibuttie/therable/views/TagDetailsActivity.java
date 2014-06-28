package com.omnibuttie.therable.views;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.views.cards.EntryCard;
import com.omnibuttie.therable.views.fragments.JournalCards;

import java.util.ArrayList;


public class TagDetailsActivity extends Activity implements  JournalCards.OnFragmentInteractionListener{
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_details);
        if (savedInstanceState == null) {
            Uri uri = getIntent().getData();
            //strip off hashtag from the URI
            String tag=uri.toString().split("/")[3];
            Fragment newFragment = JournalCards.newInstance(EntryCard.VIEW_ALL, tag);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, newFragment)
                    .commit();
        }

    }
}
