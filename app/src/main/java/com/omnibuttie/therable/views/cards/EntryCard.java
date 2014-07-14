package com.omnibuttie.therable.views.cards;

import android.content.Context;
import android.graphics.Typeface;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omnibuttie.therable.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    protected Date entryDate;
    protected String content;
    protected String title;
    protected int emoteResource;

    protected long journalID;

    protected OnCardClickListener cardClickListener;
    protected OnSwipeListener swipeListener;
    protected OnUndoSwipeListListener undoSwipeListListener;


    TextView tvMonth;
    TextView tvDay;
    TextView tvTime;
    TextView tvWeek;


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

        cardTitle = (TextView) parent.findViewById(R.id.card_title);
        cardStatus = (TextView) parent.findViewById(R.id.card_status);

        tvDay = (TextView) parent.findViewById(R.id.tvDay);
        tvMonth = (TextView) parent.findViewById(R.id.tvMonth);
        tvTime = (TextView) parent.findViewById(R.id.tvTime);
        tvWeek = (TextView) parent.findViewById(R.id.tvWeek);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Black.ttf");
        tvDay.setTypeface(font);


        if (entryDate != null) {
            Calendar cal = Calendar.getInstance();
            tvMonth.setText(new SimpleDateFormat("MMMM").format(entryDate));
            tvDay.setText(new SimpleDateFormat("dd").format(entryDate));
            tvTime.setText(new SimpleDateFormat("h:mm a").format(entryDate));
            tvWeek.setText(new SimpleDateFormat("EEEE").format(entryDate));
        }
        if (content != null) {
            cardStatus.setText(content);

            Pattern tagMatcher = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");
            String newActivityURL = "content://com.omnibuttie.therable.views.TagDetailsActivity/";
            Linkify.addLinks(cardStatus, tagMatcher, newActivityURL);
        }
        if (title != null)
            cardTitle.setText(title);
        if (emoteResource != -1) {
//            cardEmote.setImageResource(emoteResource);
        }
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
