package com.omnibuttie.therable.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.model.HashTagEntry;
import com.omnibuttie.therable.model.JournalEntry;
import com.omnibuttie.therable.views.fragments.EmoteGridFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Composer_alternate extends FragmentActivity {
    EditText editEmojicon;

    Toast mToast;
    View root;

    Spinner spinner1;

    Button imFeelingButton;
    String selectedPrimaryMood;

    JournalEntry entry;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composer_alternate);


        long journalID = getIntent().getLongExtra("JournalID", -1);
        if (journalID != -1) {
            entry = JournalEntry.findById(JournalEntry.class, journalID);
        }

        editEmojicon = (EditText) findViewById(R.id.editEmojicon);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);


        root = findViewById(R.id.root);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("nothing");
        list.add("work");
        list.add("family");
        list.add("friends");
        list.add("other people");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);

        imFeelingButton = (Button)findViewById(R.id.imfeelingbutton);

        imFeelingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                EmoteGridFragment editEmoteGrid= new EmoteGridFragment();
                editEmoteGrid.show(fm, null);
            }
        });

        fillCard();
    }

    private void fillCard() {
        appendMood(entry.getMood());
        editEmojicon.setText(entry.getContent());
        spinner1.setSelection(entry.getCause());
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
        if (id == R.id.action_settings) {
            doPost();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doPost() {
        Pattern tagMatcher = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");
        Matcher m = tagMatcher.matcher(editEmojicon.getText().toString());
        List<String> tokens = new ArrayList<String>();
        while(m.find()) {
            String token = m.group();
            tokens.add(token);
        }

        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setMood(selectedPrimaryMood);
        journalEntry.setContent(editEmojicon.getText().toString());
        journalEntry.setIntensity(3);
        journalEntry.setArchived(false);
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

    public void appendMood(String mood) {
//        editEmojicon.append(" #"+ mood );
        imFeelingButton.setText("I'm feeling " + mood.toLowerCase());
        selectedPrimaryMood = mood;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}
