package com.omnibuttie.therable.dataLoaders;

import android.content.Context;
import android.database.Cursor;

import com.omnibuttie.therable.provider.status.StatusColumns;
import com.omnibuttie.therable.provider.status.StatusCursor;
import com.omnibuttie.therable.provider.status.StatusSelection;
import com.omnibuttie.therable.provider.status.StatusType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayarvin on 10/3/14.
 */
public class StatusLoader {
    protected Context context;

    public StatusLoader(Context context) {
        this.context = context;
    }

    public List<String> getStatusesForEntryType(StatusType entryType) {
        StatusSelection selection = new StatusSelection();
        if (entryType != null) {
            selection.statusType(entryType);
        }
        StatusCursor cursor = selection.query(context.getContentResolver(), null, null);

        List<String> statuses = new ArrayList<String>();
        while (cursor.moveToNext()) {
            String typeString = "";
            switch (cursor.getStatusType()) {
                case CBT:
                    typeString = "CBT:";
                    break;
                case FITNESS:
                    typeString = "FIT:";
                    break;
                case MEDICAL:
                    typeString = "MED:";
                    break;
                case PAIN:
                    typeString = "PAIN:";
                    break;
                case OTHER:
                    typeString = "OTH:";
                    break;
            }
            statuses.add((int) cursor.getId(), typeString + cursor.getStatusName());
        }

        return statuses;
    }

    public Cursor getStatusTypes() {
        StatusSelection selection = new StatusSelection();
        selection.addRaw("GROUP BY " + StatusColumns.TABLE_NAME + "." + StatusColumns.STATUS_TYPE);
        StatusCursor cursor = selection.query(context.getContentResolver(), null, null);
        return cursor.getWrappedCursor();
    }
}
