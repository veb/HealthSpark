package com.omnibuttie.therable.provider.status;

import android.content.ContentResolver;
import android.net.Uri;

import com.omnibuttie.therable.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code status} table.
 */
public class StatusContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StatusColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, StatusSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public StatusContentValues putStatusName(String value) {
        mContentValues.put(StatusColumns.STATUS_NAME, value);
        return this;
    }

    public StatusContentValues putStatusNameNull() {
        mContentValues.putNull(StatusColumns.STATUS_NAME);
        return this;
    }


    public StatusContentValues putStatusPrimaryColor(String value) {
        mContentValues.put(StatusColumns.STATUS_PRIMARY_COLOR, value);
        return this;
    }

    public StatusContentValues putStatusPrimaryColorNull() {
        mContentValues.putNull(StatusColumns.STATUS_PRIMARY_COLOR);
        return this;
    }


    public StatusContentValues putStatusSecondaryColor(String value) {
        mContentValues.put(StatusColumns.STATUS_SECONDARY_COLOR, value);
        return this;
    }

    public StatusContentValues putStatusSecondaryColorNull() {
        mContentValues.putNull(StatusColumns.STATUS_SECONDARY_COLOR);
        return this;
    }


    public StatusContentValues putStatusTertiaryColor(String value) {
        mContentValues.put(StatusColumns.STATUS_TERTIARY_COLOR, value);
        return this;
    }

    public StatusContentValues putStatusTertiaryColorNull() {
        mContentValues.putNull(StatusColumns.STATUS_TERTIARY_COLOR);
        return this;
    }


    public StatusContentValues putStatusQuaternaryColor(String value) {
        mContentValues.put(StatusColumns.STATUS_QUATERNARY_COLOR, value);
        return this;
    }

    public StatusContentValues putStatusQuaternaryColorNull() {
        mContentValues.putNull(StatusColumns.STATUS_QUATERNARY_COLOR);
        return this;
    }


    public StatusContentValues putStatusType(StatusType value) {
        if (value == null)
            throw new IllegalArgumentException("value for statusType must not be null");
        mContentValues.put(StatusColumns.STATUS_TYPE, value.ordinal());
        return this;
    }


}
