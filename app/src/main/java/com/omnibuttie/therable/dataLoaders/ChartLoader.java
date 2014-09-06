package com.omnibuttie.therable.dataLoaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.omnibuttie.therable.model.JournalEntry;

import java.util.List;

/**
 * Created by rayarvin on 9/6/14.
 */
public class ChartLoader extends AsyncTaskLoader<List<JournalEntry>> {
    public ChartLoader(Context context) {
        super(context);
    }

    @Override
    public List<JournalEntry> loadInBackground() {
        List<JournalEntry> journalEntryList;
        journalEntryList = JournalEntry.findWithQuery(JournalEntry.class, "select \n" +
                "    strftime('%W', simpledate) WeekNumber,\n" +
                "    max(date(simpledate, 'weekday 0', '-7 day')) WeekStart,\n" +
                "    max(date(simpledate, 'weekday 0', '-1 day')) WeekEnd,\n" +
                "    count(mood_index) as moodcount,\n" +
                "    mood\n" +
                "from JOURNAL_ENTRY\n" +
                "\n" +
                "group by mood_index, WeekNumber\n" +
                "order by date_modified desc");

        return journalEntryList;
    }
}
