package com.omnibuttie.therable.provider.journalentry;

import android.content.ContentResolver;
import android.net.Uri;

import com.omnibuttie.therable.provider.base.AbstractContentValues;

import java.util.Date;

/**
 * Content values wrapper for the {@code journalentry} table.
 */
public class JournalentryContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return JournalentryColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, JournalentrySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public JournalentryContentValues putDateCreated(Date value) {
        mContentValues.put(JournalentryColumns.DATE_CREATED, value == null ? null : value.getTime());
        return this;
    }

    public JournalentryContentValues putDateCreatedNull() {
        mContentValues.putNull(JournalentryColumns.DATE_CREATED);
        return this;
    }

    public JournalentryContentValues putDateCreated(Long value) {
        mContentValues.put(JournalentryColumns.DATE_CREATED, value);
        return this;
    }


    public JournalentryContentValues putDateModified(Date value) {
        mContentValues.put(JournalentryColumns.DATE_MODIFIED, value == null ? null : value.getTime());
        return this;
    }

    public JournalentryContentValues putDateModifiedNull() {
        mContentValues.putNull(JournalentryColumns.DATE_MODIFIED);
        return this;
    }

    public JournalentryContentValues putDateModified(Long value) {
        mContentValues.put(JournalentryColumns.DATE_MODIFIED, value);
        return this;
    }


    public JournalentryContentValues putContent(String value) {
        mContentValues.put(JournalentryColumns.CONTENT, value);
        return this;
    }

    public JournalentryContentValues putContentNull() {
        mContentValues.putNull(JournalentryColumns.CONTENT);
        return this;
    }


    public JournalentryContentValues putIsArchived(Boolean value) {
        mContentValues.put(JournalentryColumns.IS_ARCHIVED, value);
        return this;
    }

    public JournalentryContentValues putIsArchivedNull() {
        mContentValues.putNull(JournalentryColumns.IS_ARCHIVED);
        return this;
    }


    public JournalentryContentValues putSimpledate(String value) {
        mContentValues.put(JournalentryColumns.SIMPLEDATE, value);
        return this;
    }

    public JournalentryContentValues putSimpledateNull() {
        mContentValues.putNull(JournalentryColumns.SIMPLEDATE);
        return this;
    }


    public JournalentryContentValues putCause(String value) {
        if (value == null) throw new IllegalArgumentException("value for cause must not be null");
        mContentValues.put(JournalentryColumns.CAUSE, value);
        return this;
    }


    public JournalentryContentValues putIntensity(Float value) {
        mContentValues.put(JournalentryColumns.INTENSITY, value);
        return this;
    }

    public JournalentryContentValues putIntensityNull() {
        mContentValues.putNull(JournalentryColumns.INTENSITY);
        return this;
    }


    public JournalentryContentValues putStatusId(long value) {
        mContentValues.put(JournalentryColumns.STATUS_ID, value);
        return this;
    }


    public JournalentryContentValues putEntryType(EntryType value) {
        if (value == null)
            throw new IllegalArgumentException("value for entryType must not be null");
        mContentValues.put(JournalentryColumns.ENTRY_TYPE, value.ordinal());
        return this;
    }


}
