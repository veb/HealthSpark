package com.omnibuttie.therable.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.dataLoaders.JournalEntryLoader;
import com.omnibuttie.therable.provider.journalentry.JournalentryCursor;
import com.omnibuttie.therable.views.cards.EntryCard;
import com.omnibuttie.therable.views.composers.Composer_alternate;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class JournalCursorFragment extends Fragment {
    private static final String CARDVIEWPARAM = "cardview";
    private static final String CONTENTFILTERPARAM = "filterparam";
    private static final String SHOWALLPARAM = "showallparam";
    int CARD_VIEW_TYPE;
    boolean showAllCards;
    String contentFilter;
    Context context;
    Card.OnCardClickListener cardClickListener = new Card.OnCardClickListener() {
        @Override
        public void onClick(Card card, View view) {
            Intent intent = new Intent(getActivity(), Composer_alternate.class);
            intent.putExtra("JournalID", ((EntryCard) card).getJournalID());
            startActivity(intent);
        }
    };
    CardListView cardListView;
    String[] emoticonString;
    TypedArray emoticonIcons;
    private JournalCardCursorAdapter adapter;

    public JournalCursorFragment() {
        // Required empty public constructor
    }

    public static JournalCursorFragment newInstance(int viewType, String contentFilter, boolean showAllCards) {
        JournalCursorFragment fragment = new JournalCursorFragment();

        Bundle args = new Bundle();
        args.putString(CONTENTFILTERPARAM, contentFilter);
        args.putInt(CARDVIEWPARAM, viewType);
        args.putBoolean(SHOWALLPARAM, showAllCards);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            contentFilter = getArguments().getString(CONTENTFILTERPARAM);
            CARD_VIEW_TYPE = getArguments().getInt(CARDVIEWPARAM);
            this.showAllCards = getArguments().getBoolean(SHOWALLPARAM, false);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal_cards, container, false);

        adapter = new JournalCardCursorAdapter(getActivity(), cardClickListener);

        cardListView = (CardListView) view.findViewById(R.id.cardList);
        if (cardListView != null) {
            cardListView.setAdapter(adapter);
        }

        adapter.changeCursor(new JournalEntryLoader(getActivity(), CARD_VIEW_TYPE, contentFilter, showAllCards).getListLoader());
        return view;
    }

    public void filterDateByDateString(String filterDateString) {
        contentFilter = filterDateString;
        adapter.changeCursor(new JournalEntryLoader(getActivity(), CARD_VIEW_TYPE, contentFilter, showAllCards).getListLoader());
    }


    class JournalCardCursorAdapter extends CardCursorAdapter {
        private Card.OnCardClickListener cardClickListener;

        JournalCardCursorAdapter(Context context, Card.OnCardClickListener cardClickListener) {
            super(context);
            this.cardClickListener = cardClickListener;
        }

        JournalCardCursorAdapter(Context context) {
            super(context);
        }


        @Override
        protected Card getCardFromCursor(Cursor cursor) {
            EntryCard card;
            JournalentryCursor jCursor = new JournalentryCursor(cursor);
            switch (jCursor.getEntryType()) {
                case CBT:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_cbt);
                    break;
                case FITNESS:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_fitness);
                    break;
                case MEDICAL:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_health);
                    break;
                case PAIN:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_pain);
                    break;
                default:
                    card = new EntryCard(getContext(), R.layout.journal_card_row);
            }

            setCardFromCursor(card, cursor);
            return card;
        }

        private void setCardFromCursor(EntryCard card, Cursor cursor) {
            JournalentryCursor entry = new JournalentryCursor(cursor);

            card.setJournalID(entry.getId());
            card.setEntryDate(entry.getDateModified());
            card.setContent(entry.getContent());
            card.setTitle(entry.getStatusName());
            card.setIntensity(entry.getIntensity());
            card.setMoodIndex(entry.getStatusId());
            card.setPrimaryColorString(entry.getStatusPrimaryColor());
            card.setSecondaryColorString(entry.getStatusSecondaryColor());
            card.setTertiaryColorString(entry.getStatusTertiaryColor());
            card.setQuaternaryColorString(entry.getStatusQuaternaryColor());
            card.setCardClickListener(cardClickListener);

        }
    }

}
