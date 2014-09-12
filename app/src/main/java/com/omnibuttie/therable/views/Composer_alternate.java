package com.omnibuttie.therable.views;

import android.app.ActionBar;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText;
import com.marvinlabs.widget.floatinglabel.itempicker.FloatingLabelItemPicker;
import com.marvinlabs.widget.floatinglabel.itempicker.ItemPickerListener;
import com.marvinlabs.widget.floatinglabel.itempicker.StringPickerDialogFragment;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.HashTagEntry;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.views.fragments.EmoteGridFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Composer_alternate extends FragmentActivity implements ItemPickerListener<String> {
    FloatingLabelEditText editEmojicon;
    FloatingLabelItemPicker<String> picker;
    Toast mToast;
    View root;

    View imFeelingButton;
    String selectedPrimaryMood;
    int selectedIntensity;
    int selectedMoodIndex;
    int selectedCause;
    String[] intensityModifiers;

    String[] moodQuestions;
    TypedArray cardBackgroundColors;

    JournalEntry entry;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardBackgroundColors = getResources().obtainTypedArray(R.array.emotiveColors);

        setContentView(R.layout.activity_composer_alternate);

        moodQuestions = getResources().getStringArray(R.array.moodQuestion);
        intensityModifiers = getResources().getStringArray(R.array.intensityModifiers);

        long journalID = getIntent().getLongExtra("JournalID", -1);
        if (journalID != -1) {
            entry = JournalEntry.findById(JournalEntry.class, journalID);
        }

        editEmojicon = (FloatingLabelEditText) findViewById(R.id.editEmojicon);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);


        root = findViewById(R.id.root);

        picker = (FloatingLabelItemPicker<String>) findViewById(R.id.spinner);
        picker.setAvailableItems(new ArrayList<String>(Arrays.asList("nothing", "work", "family", "friends", "other people")));

        // We listen to our pickerWidget events to show the dialog

        picker.setWidgetListener(new FloatingLabelItemPicker.OnWidgetEventListener<String>() {
            @Override
            public void onShowItemPickerDialog(FloatingLabelItemPicker<String> source) {
                StringPickerDialogFragment itemPicker = StringPickerDialogFragment.newInstance(
                        source.getId(),
                        "caused by",
                        "OK", null,
                        false,
                        source.getSelectedIndices(),
                        new ArrayList<String>((Collection<String>) source.getAvailableItems()));

                itemPicker.show(getSupportFragmentManager(), "ItemPicker");
            }
        });


        imFeelingButton = findViewById(R.id.imfeelingbutton);

        imFeelingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                EmoteGridFragment editEmoteGrid= EmoteGridFragment.newInstance(entry!=null?entry.getIntensity():0);
                editEmoteGrid.show(fm, null);
            }
        });

        fillCard();
    }

    //itempicker

    @Override
    public void onCancelled(int i) {

    }

    @Override
    public void onItemsSelected(int i, int[] ints) {
        picker.setSelectedIndices(ints);
    }

    private void fillCard() {
        if (entry == null) {
            return;
        }
        appendMood(entry.getMood(), entry.getIntensity(), entry.getMoodIndex());
        editEmojicon.setInputWidgetText(entry.getContent());
        picker.setSelectedIndices(new int[]{entry.getCause()});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.composer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                doPost();
                break;
            case R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doPost() {
        Pattern tagMatcher = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");
        Matcher m = tagMatcher.matcher(editEmojicon.getInputWidgetText().toString());
        List<String> tokens = new ArrayList<String>();
        while(m.find()) {
            String token = m.group();
            tokens.add(token);
        }

        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setMood(selectedPrimaryMood);
        journalEntry.setMoodIndex(selectedMoodIndex);
        journalEntry.setContent(editEmojicon.getInputWidgetText().toString());
        journalEntry.setIntensity(selectedIntensity);
        journalEntry.setArchived(false);
        journalEntry.setCause(selectedCause);
        journalEntry.save();

        HashTagEntry hashtagEntry = null;
        for (int i = 0; i < tokens.size(); i++) {
            hashtagEntry = new HashTagEntry();
            hashtagEntry.setTag(tokens.get(i));
            hashtagEntry.setEntry(journalEntry);
            hashtagEntry.save();
        }

        finish();

    }

    public void appendMood(String mood, int intensityValue, int moodID) {
        TextView tvEmoteText = (TextView) imFeelingButton.findViewById(R.id.emoteSubText);
        selectedPrimaryMood = mood;
        selectedIntensity = intensityValue;
        selectedMoodIndex = moodID;

        tvEmoteText.setText(selectedPrimaryMood);

        ColorDrawable colorDraw = new ColorDrawable(cardBackgroundColors.getColor(selectedMoodIndex, Color.RED));
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(colorDraw);

        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("mood", selectedPrimaryMood);
        savedInstanceState.putInt("intensityValue", selectedIntensity);
        savedInstanceState.putInt("moodID", selectedMoodIndex);

        super.onSaveInstanceState(savedInstanceState);
    }

    //onRestoreInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String mood = savedInstanceState.getString("mood");
        int intensityValue = savedInstanceState.getInt("intensityValue");
        int moodID = savedInstanceState.getInt("moodID");

        appendMood(mood, intensityValue, moodID);
    }
}
