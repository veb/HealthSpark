package com.omnibuttie.therable.provider.status;

import android.net.Uri;
import android.provider.BaseColumns;

import com.omnibuttie.therable.provider.TherableProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * Columns for the {@code status} table.
 */
public class StatusColumns implements BaseColumns {
    public static final String TABLE_NAME = "status";
    public static final Uri CONTENT_URI = Uri.parse(TherableProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;
    public static final String DEFAULT_ORDER = TABLE_NAME + "." + _ID;
    public static final String STATUS_NAME = "status_name";
    public static final String STATUS_PRIMARY_COLOR = "status_primary_color";
    public static final String STATUS_SECONDARY_COLOR = "status_secondary_color";
    public static final String STATUS_TERTIARY_COLOR = "status_tertiary_color";
    public static final String STATUS_QUATERNARY_COLOR = "status_quaternary_color";
    public static final String[] JOIN_PROJECTION = new String[]{
            STATUS_NAME,
            STATUS_PRIMARY_COLOR,
            STATUS_SECONDARY_COLOR,
            STATUS_TERTIARY_COLOR,
            STATUS_QUATERNARY_COLOR,
    };
    public static final String STATUS_TYPE = "status_type";
    // @formatter:off
    public static final String[] FULL_PROJECTION = new String[]{
            TABLE_NAME + "." + _ID + " AS " + BaseColumns._ID,
            TABLE_NAME + "." + STATUS_NAME,
            TABLE_NAME + "." + STATUS_PRIMARY_COLOR,
            TABLE_NAME + "." + STATUS_SECONDARY_COLOR,
            TABLE_NAME + "." + STATUS_TERTIARY_COLOR,
            TABLE_NAME + "." + STATUS_QUATERNARY_COLOR,
            TABLE_NAME + "." + STATUS_TYPE
    };
    // @formatter:on
    private static final Set<String> ALL_COLUMNS = new HashSet<String>();

    static {
        ALL_COLUMNS.add(_ID);
        ALL_COLUMNS.add(STATUS_NAME);
        ALL_COLUMNS.add(STATUS_PRIMARY_COLOR);
        ALL_COLUMNS.add(STATUS_SECONDARY_COLOR);
        ALL_COLUMNS.add(STATUS_TERTIARY_COLOR);
        ALL_COLUMNS.add(STATUS_QUATERNARY_COLOR);
        ALL_COLUMNS.add(STATUS_TYPE);
    }

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (ALL_COLUMNS.contains(c)) return true;
        }
        return false;
    }
}
