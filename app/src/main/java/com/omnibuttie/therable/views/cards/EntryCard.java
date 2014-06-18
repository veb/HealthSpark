package com.omnibuttie.therable.views.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.JournalEntry;

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


    public EntryCard(Context context, Date entryDate, String content, String title, int emoteResource) {
        this(context);
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
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        cardEmote = (ImageView) parent.findViewById(R.id.card_icon);
        cardTitle = (TextView) parent.findViewById(R.id.card_title);
        cardStatus = (TextView) parent.findViewById(R.id.card_status);
        cardEntryDate = (TextView) parent.findViewById(R.id.card_entry_date);

        if (entryDate != null)
            setEntryDate(entryDate);
        if (content != null)
            setContent(content);
        if (title != null)
            setTitle(title);
        if (emoteResource != -1)
            setEmoteResource(emoteResource);
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
        cardEntryDate.setText(entryDate.toString());
    }

    public void setContent(String content) {
        this.content = content;
        cardStatus.setText(content);
    }

    public void setTitle(String title) {
        this.title = title;
        cardTitle.setText(title);
    }

    public void setEmoteResource(int emoteResource) {
        this.emoteResource = emoteResource;
        cardEmote.setImageResource(emoteResource);
    }
}
