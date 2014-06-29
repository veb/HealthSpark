package com.omnibuttie.therable.views.cards;

import android.content.Context;
import android.content.Intent;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.views.Composer;

import java.util.Date;
import java.util.regex.Pattern;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by rayarvin on 6/17/14.
 */
public class EntryCard extends Card {
    final public static int VIEW_ARCHIVE = 101;
    final public static int VIEW_ALL = 102;


    protected TextView cardTitle;
    protected TextView cardStatus;
    protected TextView cardEntryDate;
    protected ImageView cardEmote;

    protected Date entryDate;
    protected String content;
    protected String title;
    protected int emoteResource;

    protected long journalID;

    protected OnCardClickListener cardClickListener;
    protected OnSwipeListener swipeListener;
    protected OnUndoSwipeListListener undoSwipeListListener;


    public EntryCard(Context context) {
        this(context, R.layout.journal_card_row);
    }



    public EntryCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {
        emoteResource = -1;
        this.setSwipeable(true);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        cardEmote = (ImageView) parent.findViewById(R.id.card_icon);
        cardTitle = (TextView) parent.findViewById(R.id.card_title);
        cardStatus = (TextView) parent.findViewById(R.id.card_status);
        cardEntryDate = (TextView) parent.findViewById(R.id.card_entry_date);

        if (entryDate != null)
            cardEntryDate.setText(entryDate.toString());
        if (content != null) {
            cardStatus.setText(content);

            Pattern tagMatcher = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");
            String newActivityURL = "content://com.omnibuttie.therable.views.TagDetailsActivity/";
            Linkify.addLinks(cardStatus, tagMatcher, newActivityURL);
        }
        if (title != null)
            cardTitle.setText(title);
        if (emoteResource != -1)
            cardEmote.setImageResource(emoteResource);
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public void setContent(String content) {
        this.content = content;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getJournalID() {
        return journalID;
    }

    public void setJournalID(long journalID) {
        this.setId(String.valueOf(journalID));
        this.journalID = journalID;
    }

    public void setEmoteResource(int emoteResource) {
        this.emoteResource = emoteResource;
    }

    public OnCardClickListener getCardClickListener() {
        return cardClickListener;
    }

    public void setCardClickListener(OnCardClickListener cardClickListener) {
        this.cardClickListener = cardClickListener;
        setOnClickListener(cardClickListener);
    }

    public OnSwipeListener getSwipeListener() {
        return swipeListener;
    }

    public void setSwipeListener(OnSwipeListener swipeListener) {
        this.swipeListener = swipeListener;
        setOnSwipeListener(swipeListener);
    }

    public OnUndoSwipeListListener getUndoSwipeListListener() {
        return undoSwipeListListener;
    }

    public void setUndoSwipeListListener(OnUndoSwipeListListener undoSwipeListListener) {
        this.undoSwipeListListener = undoSwipeListListener;
        setOnUndoSwipeListListener(undoSwipeListListener);
    }
}
