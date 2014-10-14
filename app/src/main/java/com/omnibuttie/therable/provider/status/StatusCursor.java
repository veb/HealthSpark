package com.omnibuttie.therable.provider.status;

import android.database.Cursor;

import com.omnibuttie.therable.provider.base.AbstractCursor;
import com.omnibuttie.therable.provider.journalentry.EntryType;

/**
 * Cursor wrapper for the {@code status} table.
 */
public class StatusCursor extends AbstractCursor {
    public StatusCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code status_name} value.
     * Can be {@code null}.
     */
    public String getStatusName() {
        Integer index = getCachedColumnIndexOrThrow(StatusColumns.STATUS_NAME);
        return getString(index);
    }

    /**
     * Get the {@code status_primary_color} value.
     * Can be {@code null}.
     */
    public String getStatusPrimaryColor() {
        Integer index = getCachedColumnIndexOrThrow(StatusColumns.STATUS_PRIMARY_COLOR);
        return getString(index);
    }

    /**
     * Get the {@code status_secondary_color} value.
     * Can be {@code null}.
     */
    public String getStatusSecondaryColor() {
        Integer index = getCachedColumnIndexOrThrow(StatusColumns.STATUS_SECONDARY_COLOR);
        return getString(index);
    }

    /**
     * Get the {@code status_tertiary_color} value.
     * Can be {@code null}.
     */
    public String getStatusTertiaryColor() {
        Integer index = getCachedColumnIndexOrThrow(StatusColumns.STATUS_TERTIARY_COLOR);
        return getString(index);
    }

    /**
     * Get the {@code status_quaternary_color} value.
     * Can be {@code null}.
     */
    public String getStatusQuaternaryColor() {
        Integer index = getCachedColumnIndexOrThrow(StatusColumns.STATUS_QUATERNARY_COLOR);
        return getString(index);
    }

    /**
     * Get the {@code status_type} value.
     * Cannot be {@code null}.
     */
    public EntryType getEntryType() {
        Integer intValue = getIntegerOrNull(StatusColumns.STATUS_TYPE);
        if (intValue == null) return null;
        return EntryType.values()[intValue];
    }
}
