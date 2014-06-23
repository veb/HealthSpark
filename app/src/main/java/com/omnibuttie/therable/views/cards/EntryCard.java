package com.omnibuttie.therable.views.cards;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.views.Composer;

import java.util.Date;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by rayarvin on 6/17/14.
 */
public class EntryCard extends Card {
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

    public EntryCard(Context context, long journalID, Date entryDate, String content, String title, int emoteResource) {
        this(context);
        this.journalID = journalID;
        this.entryDate = entryDate;
        this.content = content;
        this.title = title;
        this.emoteResource = emoteResource;
    }

    public EntryCard(Context context) {
        this(context, R.layout.journal_card_row);
    }



    public EntryCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {
        emoteResource = -1;

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        cardEmote = (ImageView) parent.findViewById(R.id.card_icon);
        cardTitle = (TextView) parent.findViewById(R.id.card_title);
        cardStatus = (TextView) parent.findViewById(R.id.card_status);
        cardEntryDate = (TextView) parent.findViewById(R.id.card_entry_date);

        if (entryDate != null)
            cardEntryDate.setText(entryDate.toString());
        if (content != null)
            cardStatus.setText(content);
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
}
