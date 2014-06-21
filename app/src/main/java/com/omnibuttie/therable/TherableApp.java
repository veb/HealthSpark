package com.omnibuttie.therable;

import android.app.Application;
import android.content.res.TypedArray;
import android.util.Log;

import com.omnibuttie.therable.model.JournalEntry;
import com.orm.SugarApp;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by rayarvin on 6/18/14.
 */
public class TherableApp extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();

        TypedArray emoticonIcons = getResources().obtainTypedArray(R.array.emoticonthumbs);

        List list = JournalEntry.listAll(JournalEntry.class);
        if (list.size() <= 0) {
            for (int i=0; i < 100; i++) {
                Random r = new Random();
                long t1 = System.currentTimeMillis() - (Math.abs(r.nextInt()));
                Date d1 = new Date(t1);

                int randEmo = r.nextInt(emoticonIcons.length());

                Log.i("DATETH", "emote: " + randEmo + " t1: " + d1.toString());

                JournalEntry journalEntry = new JournalEntry();
                journalEntry.setDateModified(d1);
                journalEntry.setDateCreated(d1);
                journalEntry.setMood(randEmo);
                journalEntry.setContent("Sample content " + i);
                journalEntry.setIntensity(r.nextInt(10));
                journalEntry.save();
            }
        }
    }
}
