package com.omnibuttie.therable.provider.journalentry;

import android.net.Uri;
import android.provider.BaseColumns;

import com.omnibuttie.therable.provider.TherableProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * Columns for the {@code journalentry} table.
 */
public class JournalentryColumns implements BaseColumns {
    public static final String TABLE_NAME = "journalentry";
    public static final Uri CONTENT_URI = Uri.parse(TherableProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;
    public static final String DEFAULT_ORDER = TABLE_NAME + "." + _ID;
    public static final String DATE_CREATED = "date_created";
    public static final String DATE_MODIFIED = "date_modified";
    public static final String CONTENT = "content";
    public static final String IS_ARCHIVED = "is_archived";
    public static final String SIMPLEDATE = "simpledate";
    public static final String CAUSE = "cause";
    public static final String INTENSITY = "intensity";
    public static final String STATUS_ID = "status_id";
    public static final String ENTRY_TYPE = "entry_type";
    // @formatter:off
    public static final String[] FULL_PROJECTION = new String[]{
            TABLE_NAME + "." + _ID + " AS " + BaseColumns._ID,
            TABLE_NAME + "." + DATE_CREATED,
            TABLE_NAME + "." + DATE_MODIFIED,
            TABLE_NAME + "." + CONTENT,
            TABLE_NAME + "." + IS_ARCHIVED,
            TABLE_NAME + "." + SIMPLEDATE,
            TABLE_NAME + "." + CAUSE,
            TABLE_NAME + "." + INTENSITY,
            TABLE_NAME + "." + STATUS_ID,
            TABLE_NAME + "." + ENTRY_TYPE
    };
    // @formatter:on

    private static final Set<String> ALL_COLUMNS = new HashSet<String>();

    static {
        ALL_COLUMNS.add(_ID);
        ALL_COLUMNS.add(DATE_CREATED);
        ALL_COLUMNS.add(DATE_MODIFIED);
        ALL_COLUMNS.add(CONTENT);
        ALL_COLUMNS.add(IS_ARCHIVED);
        ALL_COLUMNS.add(SIMPLEDATE);
        ALL_COLUMNS.add(CAUSE);
        ALL_COLUMNS.add(INTENSITY);
        ALL_COLUMNS.add(STATUS_ID);
        ALL_COLUMNS.add(ENTRY_TYPE);
    }

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (ALL_COLUMNS.contains(c)) return true;
        }
        return false;
    }
}
