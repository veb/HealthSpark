package com.omnibuttie.therable.provider.status;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.omnibuttie.therable.provider.base.AbstractSelection;

/**
 * Selection for the {@code status} table.
 */
public class StatusSelection extends AbstractSelection<StatusSelection> {
    @Override
    public Uri uri() {
        return StatusColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection      A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder       How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *                        order, which may be unordered.
     * @return A {@code StatusCursor} object, which is positioned before the first entry, or null.
     */
    public StatusCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new StatusCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public StatusCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public StatusCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public StatusSelection id(long... value) {
        addEquals(StatusColumns._ID, toObjectArray(value));
        return this;
    }


    public StatusSelection statusName(String... value) {
        addEquals(StatusColumns.STATUS_NAME, value);
        return this;
    }

    public StatusSelection statusNameNot(String... value) {
        addNotEquals(StatusColumns.STATUS_NAME, value);
        return this;
    }

    public StatusSelection statusNameLike(String... value) {
        addLike(StatusColumns.STATUS_NAME, value);
        return this;
    }

    public StatusSelection statusPrimaryColor(String... value) {
        addEquals(StatusColumns.STATUS_PRIMARY_COLOR, value);
        return this;
    }

    public StatusSelection statusPrimaryColorNot(String... value) {
        addNotEquals(StatusColumns.STATUS_PRIMARY_COLOR, value);
        return this;
    }

    public StatusSelection statusPrimaryColorLike(String... value) {
        addLike(StatusColumns.STATUS_PRIMARY_COLOR, value);
        return this;
    }

    public StatusSelection statusSecondaryColor(String... value) {
        addEquals(StatusColumns.STATUS_SECONDARY_COLOR, value);
        return this;
    }

    public StatusSelection statusSecondaryColorNot(String... value) {
        addNotEquals(StatusColumns.STATUS_SECONDARY_COLOR, value);
        return this;
    }

    public StatusSelection statusSecondaryColorLike(String... value) {
        addLike(StatusColumns.STATUS_SECONDARY_COLOR, value);
        return this;
    }

    public StatusSelection statusTertiaryColor(String... value) {
        addEquals(StatusColumns.STATUS_TERTIARY_COLOR, value);
        return this;
    }

    public StatusSelection statusTertiaryColorNot(String... value) {
        addNotEquals(StatusColumns.STATUS_TERTIARY_COLOR, value);
        return this;
    }

    public StatusSelection statusTertiaryColorLike(String... value) {
        addLike(StatusColumns.STATUS_TERTIARY_COLOR, value);
        return this;
    }

    public StatusSelection statusQuaternaryColor(String... value) {
        addEquals(StatusColumns.STATUS_QUATERNARY_COLOR, value);
        return this;
    }

    public StatusSelection statusQuaternaryColorNot(String... value) {
        addNotEquals(StatusColumns.STATUS_QUATERNARY_COLOR, value);
        return this;
    }

    public StatusSelection statusQuaternaryColorLike(String... value) {
        addLike(StatusColumns.STATUS_QUATERNARY_COLOR, value);
        return this;
    }

    public StatusSelection statusType(StatusType... value) {
        addEquals(StatusColumns.STATUS_TYPE, value);
        return this;
    }

    public StatusSelection statusTypeNot(StatusType... value) {
        addNotEquals(StatusColumns.STATUS_TYPE, value);
        return this;
    }

}
