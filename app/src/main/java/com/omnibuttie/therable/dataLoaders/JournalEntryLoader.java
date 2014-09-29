package com.omnibuttie.therable.dataLoaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.views.cards.EntryCard;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;


/**
 * Created by rayarvin on 6/18/14.
 */

public class JournalEntryLoader extends AsyncTaskLoader<List<EntryCard>> {
    protected Card.OnCardClickListener cardClickListener;
    protected Card.OnSwipeListener swipeListener;
    protected Card.OnUndoSwipeListListener undoSwipeListListener;

    protected int CardViewType;
    protected String contentFilter;



    public JournalEntryLoader(Context context) {
        super(context);
    }

    public JournalEntryLoader(Context context, Card.OnCardClickListener cardClickListener) {
        this(context, cardClickListener, EntryCard.VIEW_ALL, null);
    }

    public JournalEntryLoader(Context context, Card.OnCardClickListener cardClickListener, int cardViewType, String contentFilter) {
        super(context);
        this.cardClickListener = cardClickListener;
        CardViewType = cardViewType;
        this.contentFilter = contentFilter;
    }

    @Override
    public List<EntryCard> loadInBackground() {
        List<EntryCard> cards = new ArrayList<EntryCard>();

        List<JournalEntry> journalList = null;

        Condition modes = null;
        switch (((TherableApp) getContext().getApplicationContext()).getAppMode()) {
            case MOOD:
                modes = Condition.prop("entry_type").eq(0);
                break;
            case FITNESS:
                modes = Condition.prop("entry_type").eq(1);
                break;
            case HEALTH:
                modes = Condition.prop("entry_type").eq(2);
                break;
            case PAIN:
                modes = Condition.prop("entry_type").eq(3);
                break;
        }

        switch (CardViewType) {
            case EntryCard.VIEW_ALL:
                journalList = Select.from(JournalEntry.class).where(Condition.prop("is_archived").eq(0), modes).orderBy("date_modified desc").list();
                break;
            case EntryCard.VIEW_ARCHIVE:
                journalList = Select.from(JournalEntry.class).where(Condition.prop("is_archived").eq(1), modes).orderBy("date_modified desc").list();
                break;
            case EntryCard.VIEW_BY_DATE:
                journalList = Select.from(JournalEntry.class).where(Condition.prop("simpledate").eq(contentFilter), modes).orderBy("date_modified desc").list();
                break;
            default:
                if (contentFilter != null) {
                    journalList = Select.from(JournalEntry.class).where(Condition.prop("content").like("%" + contentFilter + "%"), modes).orderBy("date_modified desc").list();
                } else {
                    journalList = Select.from(JournalEntry.class).where(modes).orderBy("date_modified desc").list();
                }
        }

        for (JournalEntry entry : journalList) {
            EntryCard card;

            switch (entry.getEntryType()) {
                case MOOD:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_cbt);
                    break;
                case FITNESS:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_fitness);
                    break;
                case HEALTH:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_health);
                    break;
                default:
                    card = new EntryCard(getContext(), R.layout.journal_card_row);
            }

            card.setJournalID(entry.getId());
            card.setEntryDate(entry.getDateModified());
            card.setContent(entry.getContent());
            card.setTitle(entry.getMood());
            card.setIntensity(entry.getIntensity());
            card.setMoodIndex(entry.getMoodIndex());
//            card.setEmoteResource(emoticonIcons.getResourceId(entry.getMood(), -1));
            card.setCardClickListener(cardClickListener);

            if (swipeListener != null) {
                card.setSwipeListener(swipeListener);
            }
            if (undoSwipeListListener != null) {
                card.setUndoSwipeListListener(undoSwipeListListener);
            }
            cards.add(card);

        }

        return cards;
    }

    public Card.OnCardClickListener getCardClickListener() {
        return cardClickListener;
    }

    public void setCardClickListener(Card.OnCardClickListener cardClickListener) {
        this.cardClickListener = cardClickListener;
    }

    public Card.OnSwipeListener getSwipeListener() {
        return swipeListener;
    }

    public void setSwipeListener(Card.OnSwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    public Card.OnUndoSwipeListListener getUndoSwipeListListener() {
        return undoSwipeListListener;
    }

    public void setUndoSwipeListListener(Card.OnUndoSwipeListListener undoSwipeListListener) {
        this.undoSwipeListListener = undoSwipeListListener;
    }

    public String getContentFilter() {
        return contentFilter;
    }

    public void setContentFilter(String contentFilter) {
        this.contentFilter = contentFilter;
    }
}