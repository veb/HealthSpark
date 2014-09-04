package com.omnibuttie.therable.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.dataLoaders.JournalEntryLoader;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.views.Composer_alternate;
import com.omnibuttie.therable.views.cards.EntryCard;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.UndoBarController;

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
    int CARD_VIEW_TYPE;

    String contentFilter;
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
    public static JournalCards newInstance(int viewType, String contentFilter) {
        JournalCards fragment = new JournalCards();
        fragment.CARD_VIEW_TYPE = viewType;
        fragment.contentFilter = contentFilter;
        return fragment;
    }

    public JournalCards() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cardLoader = new JournalEntryLoader(getActivity(), cardClickListener, CARD_VIEW_TYPE, null);

    }

    public void filterDateByDateString(String filterDateString) {
        contentFilter = filterDateString;
        getLoaderManager().restartLoader(0, null, this);
        cardLoader.forceLoad();
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

        cardArrayAdapter = new CardArrayAdapter(context, cards);
        cardListView = (CardListView)view.findViewById(R.id.cardList);

        if (cardListView != null) {
            cardListView.setAdapter(cardArrayAdapter);
        }

        getLoaderManager().initLoader(0, null, this);

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
        cardLoader = new JournalEntryLoader(getActivity(), cardClickListener, CARD_VIEW_TYPE, contentFilter);
        cardLoader.setUndoSwipeListListener(undoSwipeListListener);
        cardLoader.setSwipeListener(swipeListener);
        return cardLoader;
    }


    @Override
    public void onLoadFinished(Loader<List<EntryCard>> loader, List<EntryCard> data) {
        cards = new ArrayList<Card>(data);
        cardArrayAdapter = new CardArrayAdapter(context, cards);
        cardArrayAdapter.setUndoBarUIElements(undoController);

        cardListView.setAdapter(cardArrayAdapter);

        cardArrayAdapter.setEnableUndo(true);
        cardArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<EntryCard>> loader) {
        cards.clear();
        cardArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        cardLoader.forceLoad();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    Card.OnCardClickListener cardClickListener = new Card.OnCardClickListener() {
        @Override
        public void onClick(Card card, View view) {
            Intent intent = new Intent(context, Composer_alternate.class);
            intent.putExtra("JournalID", ((EntryCard)card).getJournalID());
            startActivity(intent);
        }
    };

    Card.OnSwipeListener swipeListener = new Card.OnSwipeListener() {
        @Override
        public void onSwipe(Card card) {
            EntryCard eCard = (EntryCard) card;
            JournalEntry entryForCard = JournalEntry.findById(JournalEntry.class, eCard.getJournalID());

            switch (CARD_VIEW_TYPE) {
                case EntryCard.VIEW_ALL:
                    entryForCard.setArchived(true);
                    break;
                case EntryCard.VIEW_ARCHIVE:
                    entryForCard.setArchived(false);
                    break;
            }

            entryForCard.save();
            Log.i("journalcard", "swipe:" + eCard.getJournalID());
        }
    };

    Card.OnUndoSwipeListListener undoSwipeListListener = new Card.OnUndoSwipeListListener() {
        @Override
        public void onUndoSwipe(Card card) {
            EntryCard eCard = (EntryCard) card;
            JournalEntry entryForCard = JournalEntry.findById(JournalEntry.class, eCard.getJournalID());
            switch (CARD_VIEW_TYPE) {
                case EntryCard.VIEW_ALL:
                    entryForCard.setArchived(false);
                    break;
                case EntryCard.VIEW_ARCHIVE:
                    entryForCard.setArchived(true);
                    break;
            }
            entryForCard.save();
            Log.i("journalcard", "Undo: " + eCard.getJournalID());
        }
    };

    UndoBarController.UndoBarUIElements undoController = new UndoBarController.DefaultUndoBarUIElements() {
        @Override
        public int getUndoBarId() {
            return R.id.my_undobar;
        }

        @Override
        public int getUndoBarMessageId() {
            return R.id.my_undobar_message;
        }

        @Override
        public int getUndoBarButtonId() {
            return R.id.my_undobar_button;
        }

        @Override
        public String getMessageUndo(CardArrayAdapter cardArrayAdapter, String[] itemIds, int[] itemPositions) {
            String returns = "";
            switch (CARD_VIEW_TYPE) {
                case EntryCard.VIEW_ALL:
                    returns = "Moved card to Read Later";
                    break;
                case EntryCard.VIEW_ARCHIVE:
                    returns = "Moved card to Feed";
                    break;
            }
            return returns;
        }
    };


}
