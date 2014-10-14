package com.omnibuttie.therable.provider.journalentry;

import android.database.Cursor;

import com.omnibuttie.therable.provider.base.AbstractCursor;
import com.omnibuttie.therable.provider.status.StatusColumns;


import java.util.Date;

/**
 * Cursor wrapper for the {@code journalentry} table.
 */
public class JournalentryCursor extends AbstractCursor {
    public JournalentryCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code date_created} value.
     * Can be {@code null}.
     */
    public Date getDateCreated() {
        return getDate(JournalentryColumns.DATE_CREATED);
    }

    /**
     * Get the {@code date_modified} value.
     * Can be {@code null}.
     */
    public Date getDateModified() {
        return getDate(JournalentryColumns.DATE_MODIFIED);
    }

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    public String getContent() {
        Integer index = getCachedColumnIndexOrThrow(JournalentryColumns.CONTENT);
        return getString(index);
    }

    /**
     * Get the {@code is_archived} value.
     * Can be {@code null}.
     */
    public Boolean getIsArchived() {
        return getBoolean(JournalentryColumns.IS_ARCHIVED);
    }

    /**
     * Get the {@code simpledate} value.
     * Can be {@code null}.
     */
    public String getSimpledate() {
        Integer index = getCachedColumnIndexOrThrow(JournalentryColumns.SIMPLEDATE);
        return getString(index);
    }

    /**
     * Get the {@code cause} value.
     * Cannot be {@code null}.
     */
    public String getCause() {
        Integer index = getCachedColumnIndexOrThrow(JournalentryColumns.CAUSE);
        return getString(index);
    }

    /**
     * Get the {@code intensity} value.
     * Can be {@code null}.
     */
    public Float getIntensity() {
        return getFloatOrNull(JournalentryColumns.INTENSITY);
    }

    /**
     * Get the {@code status_id} value.
     */
    public long getStatusId() {
        return getLongOrNull(JournalentryColumns.STATUS_ID);
    }

    /**
     * Get the {@code entry_type} value.
     * Cannot be {@code null}.
     */
    public EntryType getEntryType() {
        Integer intValue = getIntegerOrNull(JournalentryColumns.ENTRY_TYPE);
        if (intValue == null) return null;
        return EntryType.values()[intValue];
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
    public EntryType getStatusType() {
        Integer intValue = getIntegerOrNull(StatusColumns.STATUS_TYPE);
        if (intValue == null) return null;
        return EntryType.values()[intValue];
    }
}
