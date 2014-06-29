package com.omnibuttie.therable;

import android.app.Application;
import android.content.res.TypedArray;
import android.util.Log;

import com.omnibuttie.therable.model.JournalEntry;
import com.orm.SugarApp;

import java.sql.Timestamp;
import java.util.Calendar;
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


                long offset = Timestamp.valueOf("2014-01-01 00:00:00").getTime();
                long end = new Date().getTime();
                long diff = end - offset + 1;
                Date rand = new Date(offset + (long)(Math.random() * diff));


                int randEmo = r.nextInt(emoticonIcons.length());

                Log.i("DATETH", "emote: " + randEmo + " t1: " + rand.toString());

                JournalEntry journalEntry = new JournalEntry();
                journalEntry.setDateModified(rand);
                journalEntry.setDateCreated(rand);
                journalEntry.setMood(randEmo);
                journalEntry.setContent("Sample content " + i);
                journalEntry.setIntensity(r.nextInt(10));

//                journalEntry.setArchived(r.nextBoolean());
                journalEntry.save();
            }

            JournalEntry.executeQuery("create table dates (id integer primary key);");
            JournalEntry.executeQuery("insert into dates default values;");
            JournalEntry.executeQuery("insert into dates default values;");
            JournalEntry.executeQuery("insert into dates select null from dates d1, dates d2, dates d3;");
            JournalEntry.executeQuery("insert into dates select null from dates d1, dates d2, dates d3, dates d4;");
            JournalEntry.executeQuery("alter table dates add date datetime;");
            JournalEntry.executeQuery("update dates set date=date('2013-01-01',(-1+id)||' day');");
            JournalEntry.executeQuery("create index mdateindex on dates(date);");
        }


    }
}
