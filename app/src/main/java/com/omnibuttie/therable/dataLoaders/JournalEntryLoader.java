package com.omnibuttie.therable.dataLoaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.res.TypedArray;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.views.cards.EntryCard;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;


/**
 * Created by rayarvin on 6/18/14.
 */

public class JournalEntryLoader extends AsyncTaskLoader<List<EntryCard>> {
    protected  Card.OnCardClickListener listener;

    protected int CardViewType;

    @Override
    public List<EntryCard> loadInBackground() {
        String [] emoticonString = getContext().getResources().getStringArray(R.array.emotionLabels);
        TypedArray emoticonIcons = getContext().getResources().obtainTypedArray(R.array.emoticons);
        List<EntryCard> cards = new ArrayList<EntryCard>();

        List<JournalEntry> journalList = null;


        switch (CardViewType) {
            case EntryCard.VIEW_ALL:
                journalList = Select.from(JournalEntry.class).orderBy("date_modified desc").list();
                break;
            case EntryCard.VIEW_ARCHIVE:
                journalList = Select.from(JournalEntry.class).orderBy("date_modified desc").list();
                break;
            default:
                journalList = Select.from(JournalEntry.class).orderBy("date_modified desc").list();
        }

        for (JournalEntry entry:journalList) {
            EntryCard card = new EntryCard(getContext());
            card.setJournalID(entry.getId());
            card.setEntryDate(entry.getDateModified());
            card.setContent(entry.getContent());
            card.setTitle(emoticonString[entry.getMood()]);
            card.setEmoteResource(emoticonIcons.getResourceId(entry.getMood(), -1));
            card.setCardClickListener(listener);
            cards.add(card);

        }

        return cards;
    }

    public JournalEntryLoader(Context context) {
        super(context);
    }

    public JournalEntryLoader(Context context, Card.OnCardClickListener listener) {
        this(context, listener, EntryCard.VIEW_ALL);
    }

    public JournalEntryLoader(Context context, Card.OnCardClickListener listener, int cardViewType) {
        super(context);
        this.listener = listener;
        CardViewType = cardViewType;
    }
}