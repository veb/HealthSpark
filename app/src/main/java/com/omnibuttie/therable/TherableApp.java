package com.omnibuttie.therable;

import android.content.res.TypedArray;
import android.util.Log;

import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.model.JournalEntry;
import com.orm.SugarApp;

import org.joda.time.DateTime;

import java.sql.Timestamp;
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

        TypedArray emoticonIndexes = getResources().obtainTypedArray(R.array.emoticonthumbs);
        String[] moodSubStrings = getResources().getStringArray(R.array.moodSubStrings);

        List list = JournalEntry.listAll(JournalEntry.class);
        if (list.size() <= 0) {
            JournalChartData jchartinit = new JournalChartData(0, new Date(), new Date(), 0, 0);

            jchartinit.save();
            for (int i = 0; i < 500; i++) {
                Random r = new Random();
                long t1 = System.currentTimeMillis() - (Math.abs(r.nextInt()));
                Date d1 = new Date(t1);


                long rangebegin = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
                long rangeend = new Date().getTime();
                long diff = rangeend - rangebegin + 1;
                Timestamp randTS = new Timestamp(rangebegin + (long) (Math.random() * diff));
                Date rand = new DateTime(randTS).toDate();

                int randEmo = r.nextInt(emoticonIndexes.length());

                Log.i("DATETH", "emote: " + randEmo + " t1: " + rand.toString());

                JournalEntry journalEntry = new JournalEntry();
                journalEntry.setDateModified(rand);
                journalEntry.setDateCreated(rand);
                journalEntry.setMoodIndex(randEmo);
                journalEntry.setMood(moodSubStrings[randEmo]);
                journalEntry.setContent("Sample content " + i);
                journalEntry.setIntensity(r.nextInt(2));
                journalEntry.setCause(r.nextInt(5));
                journalEntry.setArchived(r.nextBoolean());
                journalEntry.save();
            }
        }


    }
}
