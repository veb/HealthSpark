package com.omnibuttie.therable.provider.journalentry;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.omnibuttie.therable.provider.base.AbstractSelection;

import java.util.Date;

/**
 * Selection for the {@code journalentry} table.
 */
public class JournalentrySelection extends AbstractSelection<JournalentrySelection> {
    @Override
    public Uri uri() {
        return JournalentryColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection      A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder       How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *                        order, which may be unordered.
     * @return A {@code JournalentryCursor} object, which is positioned before the first entry, or null.
     */
    public JournalentryCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new JournalentryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public JournalentryCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public JournalentryCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public JournalentrySelection id(long... value) {
        addEquals(JournalentryColumns._ID, toObjectArray(value));
        return this;
    }


    public JournalentrySelection dateCreated(Date... value) {
        addEquals(JournalentryColumns.DATE_CREATED, value);
        return this;
    }

    public JournalentrySelection dateCreatedNot(Date... value) {
        addNotEquals(JournalentryColumns.DATE_CREATED, value);
        return this;
    }

    public JournalentrySelection dateCreated(Long... value) {
        addEquals(JournalentryColumns.DATE_CREATED, value);
        return this;
    }

    public JournalentrySelection dateCreatedAfter(Date value) {
        addGreaterThan(JournalentryColumns.DATE_CREATED, value);
        return this;
    }

    public JournalentrySelection dateCreatedAfterEq(Date value) {
        addGreaterThanOrEquals(JournalentryColumns.DATE_CREATED, value);
        return this;
    }

    public JournalentrySelection dateCreatedBefore(Date value) {
        addLessThan(JournalentryColumns.DATE_CREATED, value);
        return this;
    }

    public JournalentrySelection dateCreatedBeforeEq(Date value) {
        addLessThanOrEquals(JournalentryColumns.DATE_CREATED, value);
        return this;
    }

    public JournalentrySelection dateModified(Date... value) {
        addEquals(JournalentryColumns.DATE_MODIFIED, value);
        return this;
    }

    public JournalentrySelection dateModifiedNot(Date... value) {
        addNotEquals(JournalentryColumns.DATE_MODIFIED, value);
        return this;
    }

    public JournalentrySelection dateModified(Long... value) {
        addEquals(JournalentryColumns.DATE_MODIFIED, value);
        return this;
    }

    public JournalentrySelection dateModifiedAfter(Date value) {
        addGreaterThan(JournalentryColumns.DATE_MODIFIED, value);
        return this;
    }

    public JournalentrySelection dateModifiedAfterEq(Date value) {
        addGreaterThanOrEquals(JournalentryColumns.DATE_MODIFIED, value);
        return this;
    }

    public JournalentrySelection dateModifiedBefore(Date value) {
        addLessThan(JournalentryColumns.DATE_MODIFIED, value);
        return this;
    }

    public JournalentrySelection dateModifiedBeforeEq(Date value) {
        addLessThanOrEquals(JournalentryColumns.DATE_MODIFIED, value);
        return this;
    }

    public JournalentrySelection content(String... value) {
        addEquals(JournalentryColumns.CONTENT, value);
        return this;
    }

    public JournalentrySelection contentNot(String... value) {
        addNotEquals(JournalentryColumns.CONTENT, value);
        return this;
    }

    public JournalentrySelection contentLike(String... value) {
        addLike(JournalentryColumns.CONTENT, value);
        return this;
    }

    public JournalentrySelection isArchived(Boolean value) {
        addEquals(JournalentryColumns.IS_ARCHIVED, toObjectArray(value));
        return this;
    }

    public JournalentrySelection simpledate(String... value) {
        addEquals(JournalentryColumns.SIMPLEDATE, value);
        return this;
    }

    public JournalentrySelection simpledateNot(String... value) {
        addNotEquals(JournalentryColumns.SIMPLEDATE, value);
        return this;
    }

    public JournalentrySelection simpledateLike(String... value) {
        addLike(JournalentryColumns.SIMPLEDATE, value);
        return this;
    }

    public JournalentrySelection cause(String... value) {
        addEquals(JournalentryColumns.CAUSE, value);
        return this;
    }

    public JournalentrySelection causeNot(String... value) {
        addNotEquals(JournalentryColumns.CAUSE, value);
        return this;
    }

    public JournalentrySelection causeLike(String... value) {
        addLike(JournalentryColumns.CAUSE, value);
        return this;
    }

    public JournalentrySelection intensity(Float... value) {
        addEquals(JournalentryColumns.INTENSITY, value);
        return this;
    }

    public JournalentrySelection intensityNot(Float... value) {
        addNotEquals(JournalentryColumns.INTENSITY, value);
        return this;
    }

    public JournalentrySelection intensityGt(float value) {
        addGreaterThan(JournalentryColumns.INTENSITY, value);
        return this;
    }

    public JournalentrySelection intensityGtEq(float value) {
        addGreaterThanOrEquals(JournalentryColumns.INTENSITY, value);
        return this;
    }

    public JournalentrySelection intensityLt(float value) {
        addLessThan(JournalentryColumns.INTENSITY, value);
        return this;
    }

    public JournalentrySelection intensityLtEq(float value) {
        addLessThanOrEquals(JournalentryColumns.INTENSITY, value);
        return this;
    }

    public JournalentrySelection statusId(long... value) {
        addEquals(JournalentryColumns.STATUS_ID, toObjectArray(value));
        return this;
    }

    public JournalentrySelection statusIdNot(long... value) {
        addNotEquals(JournalentryColumns.STATUS_ID, toObjectArray(value));
        return this;
    }

    public JournalentrySelection statusIdGt(long value) {
        addGreaterThan(JournalentryColumns.STATUS_ID, value);
        return this;
    }

    public JournalentrySelection statusIdGtEq(long value) {
        addGreaterThanOrEquals(JournalentryColumns.STATUS_ID, value);
        return this;
    }

    public JournalentrySelection statusIdLt(long value) {
        addLessThan(JournalentryColumns.STATUS_ID, value);
        return this;
    }

    public JournalentrySelection statusIdLtEq(long value) {
        addLessThanOrEquals(JournalentryColumns.STATUS_ID, value);
        return this;
    }

    public JournalentrySelection entryType(EntryType... value) {
        addEquals(JournalentryColumns.ENTRY_TYPE, value);
        return this;
    }

    public JournalentrySelection entryTypeNot(EntryType... value) {
        addNotEquals(JournalentryColumns.ENTRY_TYPE, value);
        return this;
    }

}
