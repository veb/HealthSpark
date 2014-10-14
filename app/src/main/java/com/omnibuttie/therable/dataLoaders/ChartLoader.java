package com.omnibuttie.therable.dataLoaders;

import android.content.Context;
import android.database.Cursor;

import com.omnibuttie.therable.model.JournalChartData;
import com.omnibuttie.therable.provider.journalentry.EntryType;
import com.omnibuttie.therable.provider.journalentry.JournalentryColumns;
import com.omnibuttie.therable.provider.journalentry.JournalentryCursor;
import com.omnibuttie.therable.provider.journalentry.JournalentrySelection;
import com.omnibuttie.therable.provider.status.StatusColumns;

import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rayarvin on 9/6/14.
 */
public class ChartLoader {
    protected Context context;

    public ChartLoader(Context context) {
        this.context = context;
    }

    private static String appModeFilter(EntryType appMode) {
        String filterString = "";
        switch (appMode) {
            case CBT:
                filterString = " AND entry_type = 0 ";
                break;
            case FITNESS:
                filterString = " AND entry_type = 1 ";
                break;
            case MEDICAL:
                filterString = " AND entry_type = 2 ";
                break;
            case PAIN:
                filterString = " AND entry_type = 3 ";
                break;
        }
        return filterString;
    }

    public static List<JournalChartData> getWeeks(EntryType appMode) {
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

    public static List<JournalChartData> getMonths(EntryType appMode) {
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

    public static List<JournalChartData> getPeriodData(Date startDate, Date endDate, EntryType appMode) {
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

    public static List<JournalChartData> getAggregateForYear(int year, EntryType appMode) {
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

    public static List<JournalChartData> getEntireDataset(EntryType appMode) {
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

    public Cursor getWeeksCursor(EntryType entryType) {
        JournalentrySelection selection = new JournalentrySelection();

        String[] joinedProjection = ArrayUtils.addAll(JournalentryColumns.FULL_PROJECTION, StatusColumns.JOIN_PROJECTION);
        joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                "strftime('%W', " + JournalentryColumns.SIMPLEDATE + ") WEEKNUMBER",
                "max(strftime('%s', (" + JournalentryColumns.SIMPLEDATE + "), 'weekday 0', '-7 day'))*1000 STARTDATE",
                "max(strftime('%s', (" + JournalentryColumns.SIMPLEDATE + "), 'weekday 0', '-1 day'))*1000 ENDDATE",
        };
        selection.contentNot("");
        if (entryType != null) {
            selection.and().entryType(entryType);
        }

        selection.addRaw("GROUP BY WEEKNUMBER");
        return selection.query(this.context.getContentResolver(), joinedProjection, "date_modified desc").getWrappedCursor();
    }

    public Cursor getYearsCursor(EntryType entryType) {
        JournalentrySelection selection = new JournalentrySelection();

        String[] joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                "strftime('%Y', " + JournalentryColumns.SIMPLEDATE + ") WEEKNUMBER",
        };
        selection.contentNot("");
        if (entryType != null) {
            selection.and().entryType(entryType);
        }

        selection.addRaw("GROUP BY WEEKNUMBER");
        return selection.query(this.context.getContentResolver(), joinedProjection, "date_modified desc").getWrappedCursor();
    }

    public Cursor getMonthsCursor(EntryType entryType) {
        JournalentrySelection selection = new JournalentrySelection();

        String[] joinedProjection = ArrayUtils.addAll(JournalentryColumns.FULL_PROJECTION, StatusColumns.JOIN_PROJECTION);
        joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                "strftime('%M', " + JournalentryColumns.SIMPLEDATE + ") MONTHNUMBER",
                "strftime('%s', " + JournalentryColumns.SIMPLEDATE + ")*1000 STARTDATE",
                "strftime('%s', " + JournalentryColumns.SIMPLEDATE + ", '+1 month', '-1 day'))*1000 ENDDATE",
        };
        selection.contentNot("");
        if (entryType != null) {
            selection.and().entryType(entryType);
        }

        selection.addRaw("GROUP BY MONTHNUMBER");
        Cursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, "date_modified desc");
        return jcur;
    }

    public Cursor getMonthliesForYear(int year, long statusID) {
        JournalentrySelection selection = new JournalentrySelection();

        String[] joinedProjection = ArrayUtils.addAll(JournalentryColumns.FULL_PROJECTION, StatusColumns.JOIN_PROJECTION);
        joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.DATE_MODIFIED,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID,
                "count(" + JournalentryColumns.STATUS_ID + ") MOODCOUNT",
                "cast(strftime('%m'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") as INTEGER) MONTHNUMBER",
                "strftime('%m'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS month",
                "strftime('%Y'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS year",
                "strftime('%d'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS day",
        };

        selection.addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " between '" + year + "-01-01' and '" + year + "-12-31'");

        selection.and().statusId(statusID);

        selection.addRaw("GROUP BY " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.ENTRY_TYPE + ", " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID + ", year, month");
        Cursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, "date_modified desc");
        return jcur;

    }

    public Cursor getDatasetCursor(EntryType entryType) {
        JournalentrySelection selection = new JournalentrySelection();

        String[] joinedProjection = ArrayUtils.addAll(JournalentryColumns.FULL_PROJECTION, StatusColumns.JOIN_PROJECTION);
        joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.DATE_MODIFIED,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID,
                "count(" + JournalentryColumns.STATUS_ID + ") MOODCOUNT",
                "strftime('%m'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS month",
                "strftime('%Y'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS year",
                "strftime('%d'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS day",
        };
        selection.contentNot("");

        if (entryType != null) {
            selection.and().entryType(entryType);
        }

        selection.addRaw("GROUP BY " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.ENTRY_TYPE + ", " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID + ", year, month, day");
        Cursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, "date_modified desc");
        return jcur;
    }

    public Cursor getDatasetCursor(EntryType entryType, long statusID) {
        JournalentrySelection selection = new JournalentrySelection();

        String[] joinedProjection = ArrayUtils.addAll(JournalentryColumns.FULL_PROJECTION, StatusColumns.JOIN_PROJECTION);
        joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.DATE_MODIFIED,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID,
                "count(" + JournalentryColumns.STATUS_ID + ") MOODCOUNT",
                "strftime('%m'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS month",
                "strftime('%Y'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS year",
                "strftime('%d'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS day",
        };

        selection.statusId(statusID);
        selection.and().entryType(entryType);

        selection.addRaw("GROUP BY " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.ENTRY_TYPE + ", " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID + ", year, month, day");
        Cursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, "date_modified desc");
        return jcur;
    }

    public List<LocalDate> getMaxMinDate(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("YYYY-MM-dd");
        JournalentrySelection selection = new JournalentrySelection();

        String[] joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                "min(" + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.DATE_MODIFIED + ")",
                "max(" + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.DATE_MODIFIED + ")",

        };


        if (startDate == null && endDate == null) {

        } else if (startDate == null) {
            selection.addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " <=  " + dtfOut.print(endDate));
        } else if (endDate == null) {
            selection.addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " >= " + dtfOut.print(startDate));
        } else {
            selection.addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " between '" + dtfOut.print(startDate) + "' AND '" + dtfOut.print(endDate) + "'");
        }

        JournalentryCursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, "date_modified asc");

        List<LocalDate> lDate = new ArrayList<LocalDate>();
        while (jcur.moveToNext()) {
            lDate.add(new LocalDate(jcur.getLong(1)));
            lDate.add(new LocalDate(jcur.getLong(2)));
        }
        return lDate;
    }

    public Cursor getDatasetCursor(long statusID, LocalDate startDate, LocalDate endDate, int filter) {
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("YYYY-MM-dd");
        JournalentrySelection selection = new JournalentrySelection();

        String[] joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.DATE_MODIFIED,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID,
                "count(" + JournalentryColumns.STATUS_ID + ") MOODCOUNT",
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE,
                "strftime('%m'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS month",
                "strftime('%Y'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS year",
                "strftime('%d'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS day",
                "strftime('%W'," + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + ") AS week",
        };

        selection.statusId(statusID);

        if (startDate == null && endDate == null) {

        } else if (startDate == null) {
            selection.and().addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " <=  " + dtfOut.print(endDate));
        } else if (endDate == null) {
            selection.and().addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " >= " + dtfOut.print(startDate));
        } else {
            selection.and().addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " between '" + dtfOut.print(startDate) + "' AND '" + dtfOut.print(endDate) + "'");
        }

        String filterString = "";

        switch (filter) {
            case 0:
                filterString = ", day, year, month";
                break;
            case 1:
                filterString = ", week, year, month";
                break;
            case 2:
                filterString = ", year, month";
                break;
        }
        selection.addRaw("GROUP BY " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.ENTRY_TYPE + ", " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID + filterString);
        JournalentryCursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, "date_modified asc");
        return jcur.getWrappedCursor();
    }
    public Cursor getPeriodDataCursor(LocalDate startDate, LocalDate endDate, EntryType appMode) {
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("YYYY-MM-dd");

        String[] joinedProjection = new String[]{
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns._ID,
                JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID,
                "count(" + JournalentryColumns.STATUS_ID + ") MOODCOUNT",
        };

        JournalentrySelection selection = new JournalentrySelection();


        if (startDate == null && endDate == null) {

        } else if (startDate == null) {
            selection.addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " <=  " + dtfOut.print(endDate));
        } else if (endDate == null) {
            selection.addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " >= " + dtfOut.print(startDate));
        } else {
            selection.addRaw(JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.SIMPLEDATE + " between '" + dtfOut.print(startDate) + "' AND '" + dtfOut.print(endDate) + "'");
        }

        selection.and().entryType(appMode);
        selection.addRaw("GROUP BY " + JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID);
        JournalentryCursor jcur = selection.query(this.context.getContentResolver(), joinedProjection, JournalentryColumns.TABLE_NAME + "." + JournalentryColumns.STATUS_ID + " asc, date_modified asc");

        return jcur.getWrappedCursor();
    }

    public enum ChartFilter {
        DAILY, WEEKLY, MONTHLY
    }
}
