package com.omnibuttie.therable.dataLoaders;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.provider.journalentry.EntryType;
import com.omnibuttie.therable.provider.journalentry.JournalentryColumns;
import com.omnibuttie.therable.provider.journalentry.JournalentryCursor;
import com.omnibuttie.therable.provider.journalentry.JournalentrySelection;
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
    public JournalentryCursor loadInBackground() {
        List<EntryCard> cards = new ArrayList<EntryCard>();

        List<JournalEntry> journalList = null;

        Cursor cursor;

        JournalentrySelection where_modes = new JournalentrySelection();
        switch (((TherableApp) getContext().getApplicationContext()).getAppMode()) {
            case CBT:
                where_modes.entryType(EntryType.CBT);
                break;
            case FITNESS:
                where_modes.entryType(EntryType.FITNESS);
                break;
            case MEDICAL:
                where_modes.entryType(EntryType.MEDICAL);
                break;
            case PAIN:
                where_modes.entryType(EntryType.PAIN);
                break;
            default:
                where_modes.entryType(EntryType.OTHER);
                break;
        }

        switch (CardViewType) {
            case EntryCard.VIEW_ALL:
                where_modes.or().isArchived(false);
                journalList = Select.from(JournalEntry.class).where(Condition.prop("is_archived").eq(0), where_modes).orderBy("date_modified desc").list();
                break;
            case EntryCard.VIEW_ARCHIVE:
                where_modes.or().isArchived(true);
                journalList = Select.from(JournalEntry.class).where(Condition.prop("is_archived").eq(1), where_modes).orderBy("date_modified desc").list();
                break;
            case EntryCard.VIEW_BY_DATE:
                where_modes.or().simpledate(contentFilter);
                journalList = Select.from(JournalEntry.class).where(Condition.prop("simpledate").eq(contentFilter), where_modes).orderBy("date_modified desc").list();
                break;
            default:
                if (contentFilter != null) {
                    where_modes.or().contentLike("%" + contentFilter + "%");
                    journalList = Select.from(JournalEntry.class).where(Condition.prop("content").like("%" + contentFilter + "%"), where_modes).orderBy("date_modified desc").list();
                } else {
                    journalList = Select.from(JournalEntry.class).where(where_modes).orderBy("date_modified desc").list();
                }
        }

        Cursor c = getContext().getContentResolver().query(JournalentryColumns.CONTENT_URI, null, where_modes.sel(), where_modes.args(), null);
        JournalentryCursor jCursor = new JournalentryCursor(c);

        return jCursor;

        for (JournalEntry entry : journalList) {
            EntryCard card;

            switch (entry.getEntryType()) {
                case CBT:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_cbt);
                    break;
                case FITNESS:
                    card = new EntryCard(getContext(), R.layout.journal_card_row_fitness);
                    break;
                case MEDICAL:
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