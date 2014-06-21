package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.dataLoaders.JournalEntryLoader;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.model.User;
import com.omnibuttie.therable.views.cards.EntryCard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JournalCards.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JournalCards#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class JournalCards extends Fragment implements LoaderManager.LoaderCallbacks<List<EntryCard>>{
    Context context;
    CardListView cardListView;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Card> cards;

    private ArrayList<JournalEntry> journalEntries;

    String[] emoticonString;
    TypedArray emoticonIcons;

    CardArrayAdapter cardArrayAdapter;

    JournalEntryLoader cardLoader;

    // TODO: Rename and change types and number of parameters
    public static JournalCards newInstance() {
        JournalCards fragment = new JournalCards();
        return fragment;
    }
    public JournalCards() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cardLoader = new JournalEntryLoader(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = this.getActivity();
        emoticonString = context.getResources().getStringArray(R.array.emotionLabels);
        emoticonIcons = context.getResources().obtainTypedArray(R.array.emoticonthumbs);

        cards = new ArrayList<Card>();
        View view = inflater.inflate(R.layout.fragment_journal_cards, container, false);

//        for (JournalEntry entry:JournalEntry.listAll(JournalEntry.class)) {
//            EntryCard card = new EntryCard(context, entry.getDateModified(), entry.getContent(), emoticonString[entry.getMood()], emoticonIcons.getResourceId(entry.getMood(), -1));
//            cards.add(card);
//        }

        cardArrayAdapter = new CardArrayAdapter(context, cards);
        cardListView = (CardListView)view.findViewById(R.id.cardList);

        if (cardListView != null) {
            cardListView.setAdapter(cardArrayAdapter);
        }

        getLoaderManager().initLoader(0, null, this);
//
        cardLoader.forceLoad();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public Loader<List<EntryCard>> onCreateLoader(int id, Bundle args) {
        return cardLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<EntryCard>> loader, List<EntryCard> data) {
        cards = new ArrayList<Card>(data);
        cardArrayAdapter = new CardArrayAdapter(context, cards);
        cardListView.setAdapter(cardArrayAdapter);
        cardArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<EntryCard>> loader) {
        cards.clear();
        cardArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
