package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.views.cards.EntryCard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class JournalCards extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ArrayList<Card> cards;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Context context = this.getActivity();
        cards = new ArrayList<Card>();
        View view = inflater.inflate(R.layout.fragment_journal_cards, container, false);

        String[] emoticonString = context.getResources().getStringArray(R.array.emotionLabels);
        TypedArray emoticonIcons = context.getResources().obtainTypedArray(R.array.emoticonthumbs);

        for (int i=0; i<emoticonIcons.length(); i++) {
            EntryCard card = new EntryCard(context,
                    new Date(),
                    "Lorem Bitches",
                    emoticonString[i],
                    emoticonIcons.getResourceId(i, -1));

            cards.add(card);
        }

        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(context, cards);
        CardListView cardListView = (CardListView)view.findViewById(R.id.cardList);

        if (cardListView != null) {
            cardListView.setAdapter(cardArrayAdapter);
        }
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

}
