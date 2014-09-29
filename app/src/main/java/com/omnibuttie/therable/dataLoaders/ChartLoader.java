package com.omnibuttie.therable.dataLoaders;

import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.model.JournalEntry;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;

/**
 * Created by rayarvin on 9/6/14.
 */
public class ChartLoader {
    private static String appModeFilter(JournalEntry.EntryType appMode) {
        String filterString = "";
        switch (appMode) {
            case MOOD:
                filterString = " AND entry_type = 0 ";
                break;
            case FITNESS:
                filterString = " AND entry_type = 1 ";
                break;
            case HEALTH:
                filterString = " AND entry_type = 2 ";
                break;
            case PAIN:
                filterString = " AND entry_type = 3 ";
                break;
        }
        return filterString;
    }

    public static List<JournalChartData> getWeeks(JournalEntry.EntryType appMode) {
        String sql = "select \n" +
                "    1 as ID,\n" +
                "    strftime('%W', simpledate) WEEKNUMBER,\n" +
                "    max(strftime('%s', (simpledate), 'weekday 0', '-7 day'))*1000 WEEKSTART,\n" +
                "    max(strftime('%s', (simpledate), 'weekday 0', '-1 day'))*1000 WEEKEND,\n" +
                "    3 as MOODCOUNT,\n" +
                "    3 as MOODINDEX\n" +
                "from JOURNAL_ENTRY\n " +
                "where 1 " + appModeFilter(appMode) +
                "\n" +
                "group by WEEKNUMBER\n" +
                "order by date_modified desc\n";

        return JournalChartData.findWithQuery(JournalChartData.class, sql);
    }

    public static List<JournalChartData> getMonths(JournalEntry.EntryType appMode) {
        String sql = "select \n" +
                "strftime('%m', simpledate) WEEKNUMBER,\n" +
                "strftime('%s', simpledate)*1000 WEEKSTART,\n" +
                "strftime('%s', date(simpledate, '+1 month', '-1 day'))*1000 WEEKEND,\n" +
                "1 as ID,\n" +
                "1 as _ID,\n" +
                "1 as MOODCOUNT,\n" +
                "1 as MOODINDEX\n" +
                "\n" +
                "from JOURNAL_ENTRY\n" +
                "where " + appModeFilter(appMode) +
                "group by WeekNumber\n" +
                "order by date_modified desc";

        return JournalChartData.findWithQuery(JournalChartData.class, sql);

    }

    public static List<JournalChartData> getPeriodData(Date startDate, Date endDate, JournalEntry.EntryType appMode) {
        DateTime simpleStart = new DateTime(startDate);
        DateTime simpleEnd = new DateTime(endDate);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");


        String sql = "select " +
                "     1 as ID,\n" +
                "     date_modified WEEKSTART,\n" +
                "     date_modified WEEKEND,\n" +
                "     MOOD_INDEX as MOODINDEX,\n" +
                "     count(MOOD_INDEX) as MOODCOUNT\n" +
                "     from JOURNAL_ENTRY\n" +
                "where SIMPLEDATE between '" + fmt.print(simpleStart) + "' and '" + fmt.print(simpleEnd) + "'\n " +
                appModeFilter(appMode) +
                "group by MOOD_INDEX\n" +
                "order by MOOD_INDEX asc, SIMPLEDATE desc";
        List<JournalChartData> journalChartList = JournalChartData.findWithQuery(JournalChartData.class, sql);
        return journalChartList;
    }

    public static List<JournalChartData> getAggregateForYear(int year, JournalEntry.EntryType appMode) {
        String sql = "SELECT \n" +
                "cast(strftime('%m', simpledate) as INTEGER) WEEKNUMBER,\n" +
                "date_modified WEEKSTART,\n" +
                "date_modified WEEKEND,\n" +
                "MOOD_INDEX as MOODINDEX,\n" +
                "count(MOOD_INDEX) as MOODCOUNT,\n" +
                "ID as ID,\n" +
                "ID as _ID,\n" +
                "strftime('%m',simpledate) AS month,\n" +
                "strftime('%Y',simpledate) AS year\n" +
                "from JOURNAL_ENTRY\n" +
                "where SIMPLEDATE between '" + year + "-01-01' and '" + year + "-12-31'\n " +
                appModeFilter(appMode) +
                "GROUP BY moodindex, year, month";
        List<JournalChartData> journalChartList = JournalChartData.findWithQuery(JournalChartData.class, sql);
        return journalChartList;
    }

    public static List<JournalChartData> getEntireDataset(JournalEntry.EntryType appMode) {
        String sql = "SELECT \n" +
                "cast(strftime('%Y%m%d', simpledate) as INTEGER) WEEKNUMBER,\n" +
                "date_modified WEEKSTART,\n" +
                "date_modified WEEKEND,\n" +
                "MOOD_INDEX as MOODINDEX,\n" +
                "count(MOOD_INDEX) as MOODCOUNT,\n" +
                "ID as ID,\n" +
                "ID as _ID,\n" +
                "strftime('%m',simpledate) AS month,\n" +
                "strftime('%Y',simpledate) AS year,\n" +
                "strftime('%d',simpledate) AS day\n" +
                "from JOURNAL_ENTRY\n" +
                "where 1 " +
                appModeFilter(appMode) +
                "GROUP BY moodindex, year, month, day";
        List<JournalChartData> journalChartList = JournalChartData.findWithQuery(JournalChartData.class, sql);
        return journalChartList;
    }
}
