package com.omnibuttie.therable;

import android.app.Application;

import com.omnibuttie.therable.provider.journalentry.EntryType;
import com.omnibuttie.therable.provider.journalentry.JournalentryContentValues;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by rayarvin on 6/18/14.
 */
public class TherableApp extends Application {
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


            for (int i = 0; i < 1500; i++) {
                Random r = new Random();
                long t1 = System.currentTimeMillis() - (Math.abs(r.nextInt()));
                Date d1 = new Date(t1);

                long rangebegin = Timestamp.valueOf("2013-09-01 00:00:00").getTime();
                long rangeend = new Date().getTime();
                long diff = rangeend - rangebegin + 1;
                Timestamp randTS = new Timestamp(rangebegin + (long) (Math.random() * diff));
                Date rand = new DateTime(randTS).toDate();

                JournalentryContentValues values = new JournalentryContentValues();
                values.putDateCreated(rand);
                values.putDateModified(rand);
                values.putContent("SampleContent: " + i);
                values.putIsArchived(r.nextBoolean());

                EntryType entryType = EntryType.values()[r.nextInt(EntryType.values().length)];
                values.putEntryType(entryType);
                values.putCause("default");

                String[] moodSubStrings = null;

                int statusCode = r.nextInt(35);
                values.putStatusId(statusCode);

                if (statusCode > 34) {
                    values.putEntryType(EntryType.OTHER);
                    values.putIntensity((float) r.nextInt());
                } else if (statusCode > 22) {
                    values.putEntryType(EntryType.PAIN);
                    values.putIntensity(0f);
                } else if (statusCode > 14) {
                    values.putEntryType(EntryType.MEDICAL);
                    values.putIntensity(r.nextFloat());
                } else if (statusCode > 9) {
                    values.putEntryType(EntryType.FITNESS);
                    values.putIntensity((float) r.nextInt(1000));
                } else {
                    values.putEntryType(EntryType.CBT);
                    values.putIntensity((float) r.nextInt(3));
                }
                switch (entryType) {
                    case CBT:
                        break;
                    case FITNESS:
                        break;
                    case MEDICAL:
                        break;
                    case PAIN:
                        break;
                    case OTHER:
                        break;
                }

                values.insert(getContentResolver());
            }

    }
}
