package com.omnibuttie.therable.views.cards;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.views.controls.CircularCounter;
import com.omnibuttie.therable.views.drawables.LeftBorderDrawable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by rayarvin on 6/17/14.
 */
public class EntryCard extends Card {
    final public static int VIEW_ARCHIVE = 101;
    final public static int VIEW_ALL = 102;
    final public static int VIEW_BY_DATE = 103;
    final public static int VIEW_TAGGED = 104;


    protected TextView cardTitle;
    protected TextView cardStatus;

    protected String primaryColorString;
    protected String secondaryColorString;
    protected String tertiaryColorString;
    protected String quaternaryColorString;

    protected Date entryDate;
    protected String content;
    protected String title;
    protected int emoteResource;
    protected float intensity;
    protected long moodIndex;

    protected long journalID;

    protected OnCardClickListener cardClickListener;
    protected OnSwipeListener swipeListener;
    protected OnUndoSwipeListListener undoSwipeListListener;


    TextView tvMonth;
    TextView tvDay;
    TextView tvTime;
    TextView tvWeek;
    CircularCounter counter;

    TypedArray cardBorderColors;
    TypedArray cardBackgroundColors;


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
        cardBorderColors = getContext().getResources().obtainTypedArray(R.array.emotiveColors);
        cardBackgroundColors = getContext().getResources().obtainTypedArray(R.array.lightEmotiveColors);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        cardTitle = (TextView) parent.findViewById(R.id.card_title);
        cardStatus = (TextView) parent.findViewById(R.id.card_status);

        tvDay = (TextView) parent.findViewById(R.id.tvDay);
        tvMonth = (TextView) parent.findViewById(R.id.tvMonth);
        tvTime = (TextView) parent.findViewById(R.id.tvTime);
        tvWeek = (TextView) parent.findViewById(R.id.tvWeek);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Black.ttf");
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


        view.setBackground(new LeftBorderDrawable(Color.parseColor(this.primaryColorString), Color.parseColor(this.secondaryColorString)));

        counter = (CircularCounter) view.findViewById(R.id.counter);
        if (counter != null) {
            counter.setFirstWidth(20);
            counter.setFirstColor(getContext().getResources().getColor(R.color.blue_700));

            counter.setSecondWidth(20);
            counter.setSecondColor(getContext().getResources().getColor(R.color.indigo_700));

            counter.setThirdWidth(20);
            counter.setThirdWidth(getContext().getResources().getColor(R.color.purple_700));

            Random r = new Random();
            counter.setValues((int) Math.floor(this.intensity), (int) Math.floor(this.intensity) + 60, (int) Math.floor(this.intensity) + 120);
        }

        TextView cbtico = (TextView) view.findViewById(R.id.cbt_icon);
        if (cbtico != null) {
            cbtico.setTextColor(Color.parseColor(this.primaryColorString));
        }

    }

    public long getMoodIndex() {
        return moodIndex;
    }

    public void setMoodIndex(long moodIndex) {
        this.moodIndex = moodIndex;
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

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
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

    public String getPrimaryColorString() {
        return primaryColorString;
    }

    public void setPrimaryColorString(String primaryColorString) {
        if (TextUtils.getTrimmedLength(primaryColorString) <= 0) {
            primaryColorString = "ffffff";
        }
        this.primaryColorString = "#" + primaryColorString;
    }

    public String getSecondaryColorString() {
        return secondaryColorString;
    }

    public void setSecondaryColorString(String secondaryColorString) {
        if (TextUtils.getTrimmedLength(secondaryColorString) <= 0) {
            secondaryColorString = "ffffff";
        }
        this.secondaryColorString = "#" + secondaryColorString;
    }

    public String getTertiaryColorString() {
        return tertiaryColorString;
    }

    public void setTertiaryColorString(String tertiaryColorString) {
        if (TextUtils.getTrimmedLength(tertiaryColorString) <= 0) {
            tertiaryColorString = "ffffff";
        }
        this.tertiaryColorString = "#" + tertiaryColorString;
    }

    public String getQuaternaryColorString() {
        return quaternaryColorString;
    }

    public void setQuaternaryColorString(String quaternaryColorString) {
        if (TextUtils.getTrimmedLength(quaternaryColorString) <= 0) {
            quaternaryColorString = "ffffff";
        }
        this.quaternaryColorString = "#" + quaternaryColorString;
    }
}
