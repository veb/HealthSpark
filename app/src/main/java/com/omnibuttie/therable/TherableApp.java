package com.omnibuttie.therable;

import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.model.JournalEntry;
import com.orm.SugarApp;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.omnibuttie.therable.model.JournalEntry.EntryType;

/**
 * Created by rayarvin on 6/18/14.
 */
public class TherableApp extends SugarApp {
    private EntryType appMode;

    public EntryType getAppMode() {
        return appMode;
    }

    public void setAppMode(EntryType appMode) {
        this.appMode = appMode;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.setAppMode(EntryType.FITNESS);

        CalligraphyConfig.initDefault("fonts/Lato-Regular.ttf", R.attr.fontPath);

        List list = JournalEntry.listAll(JournalEntry.class);
        if (list.size() <= 0) {
            JournalChartData jchartinit = new JournalChartData(0, new Date(), new Date(), 0, 0);

            jchartinit.save();
            for (int i = 0; i < 1500; i++) {
                Random r = new Random();
                long t1 = System.currentTimeMillis() - (Math.abs(r.nextInt()));
                Date d1 = new Date(t1);


                long rangebegin = Timestamp.valueOf("2013-09-01 00:00:00").getTime();
                long rangeend = new Date().getTime();
                long diff = rangeend - rangebegin + 1;
                Timestamp randTS = new Timestamp(rangebegin + (long) (Math.random() * diff));
                Date rand = new DateTime(randTS).toDate();


                JournalEntry journalEntry = new JournalEntry();
                journalEntry.setDateModified(rand);
                journalEntry.setDateCreated(rand);


                journalEntry.setContent("Sample content " + i);

                journalEntry.setCause(r.nextInt(5));
                journalEntry.setArchived(r.nextBoolean());

                journalEntry.setEntryType(r.nextInt(EntryType.values().length));

                EntryType type = journalEntry.getEntryType();
                String[] moodSubStrings = null;
                switch (type) {
                    case MOOD:
                        moodSubStrings = getResources().getStringArray(R.array.moodSubStrings);
                        journalEntry.setIntensity(r.nextInt(3));
                        break;
                    case FITNESS:
                        moodSubStrings = getResources().getStringArray(R.array.fitnessActivityStrings);
                        journalEntry.setIntensity(r.nextInt(720));
                        break;
                    case HEALTH:
                        moodSubStrings = getResources().getStringArray(R.array.effectivenessString);
                        journalEntry.setIntensity(r.nextInt(10));
                        break;
                    case PAIN:
                        moodSubStrings = getResources().getStringArray(R.array.painStrings);
                        journalEntry.setIntensity(r.nextInt(10));
                        break;
                    default:
                        moodSubStrings = getResources().getStringArray(R.array.moodSubStrings);
                        journalEntry.setIntensity(r.nextInt());
                        break;
                }

                int randEmo = r.nextInt(moodSubStrings.length);
                journalEntry.setMoodIndex(randEmo);
                journalEntry.setMood(moodSubStrings[randEmo]);
                journalEntry.save();
            }
        }


    }
}
