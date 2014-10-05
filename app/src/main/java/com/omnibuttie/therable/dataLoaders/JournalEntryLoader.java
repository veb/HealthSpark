package com.omnibuttie.therable.dataLoaders;


import android.content.Context;

import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.provider.journalentry.EntryType;
import com.omnibuttie.therable.provider.journalentry.JournalentryColumns;
import com.omnibuttie.therable.provider.journalentry.JournalentryCursor;
import com.omnibuttie.therable.provider.journalentry.JournalentrySelection;
import com.omnibuttie.therable.provider.status.StatusColumns;
import com.omnibuttie.therable.views.cards.EntryCard;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;


/**
 * Created by rayarvin on 6/18/14.
 */

public class JournalEntryLoader {
    protected Card.OnCardClickListener cardClickListener;
    protected Card.OnSwipeListener swipeListener;
    protected Card.OnUndoSwipeListListener undoSwipeListListener;

    protected Context context;
    protected int CardViewType;
    protected String contentFilter;
    protected boolean showAllCards;

    public JournalEntryLoader(Context context, String contentFilter) {
        this.contentFilter = contentFilter;
        this.context = context;
        this.CardViewType = EntryCard.VIEW_ALL;
        this.showAllCards = true;
    }

    public JournalEntryLoader(Context context) {
        this(context, EntryCard.VIEW_ALL, null, false);
    }

    public JournalEntryLoader(Context context, int cardViewType, String contentFilter, boolean showAllCards) {
        this.context = context;
        CardViewType = cardViewType;
        this.contentFilter = contentFilter;
        this.showAllCards = showAllCards;
    }

    public JournalentryCursor entryWithID(long entryID) {
        JournalentrySelection selection = new JournalentrySelection();
        selection.id(entryID);
        String[] joinedProjection = ArrayUtils.addAll(JournalentryColumns.FULL_PROJECTION, StatusColumns.JOIN_PROJECTION);

        JournalentryCursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, null);
        return jcur;

    }

    public List<String> getCauses(EntryType entryType) {
        JournalentrySelection selection = new JournalentrySelection();
        selection.entryType(entryType);

        String[] joinedProjection = {"DISTINCT " + JournalentryColumns.CAUSE};
        JournalentryCursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, null);

        ArrayList<String> causes = new ArrayList<String>();
        while (jcur.moveToNext()) {
            causes.add(jcur.getCause());
        }

        return causes;
    }

    public JournalentryCursor getListLoader() {
        JournalentrySelection where_modes = new JournalentrySelection();
        switch (CardViewType) {
            case EntryCard.VIEW_ALL:
                where_modes.isArchived(false);
                break;
            case EntryCard.VIEW_ARCHIVE:
                where_modes.isArchived(true);
                break;
            case EntryCard.VIEW_BY_DATE:
                where_modes.simpledate(contentFilter);
                break;
            default:
                if (contentFilter != null) {
                    where_modes.contentLike("%" + contentFilter + "%");
                }
        }

        if (!this.showAllCards) {
            switch (((TherableApp) this.context.getApplicationContext()).getAppMode()) {
                case CBT:
                    where_modes.and().entryType(EntryType.CBT);
                    break;
                case FITNESS:
                    where_modes.and().entryType(EntryType.FITNESS);
                    break;
                case MEDICAL:
                    where_modes.and().entryType(EntryType.MEDICAL);
                    break;
                case PAIN:
                    where_modes.and().entryType(EntryType.PAIN);
                    break;
                default:
                    where_modes.entryType(EntryType.OTHER);
                    break;
            }
        }

        String[] joinedProjection = ArrayUtils.addAll(JournalentryColumns.FULL_PROJECTION, StatusColumns.JOIN_PROJECTION);

        JournalentryCursor jcur = where_modes.query(this.context.getContentResolver(), joinedProjection, "date_modified desc");
        return jcur;
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