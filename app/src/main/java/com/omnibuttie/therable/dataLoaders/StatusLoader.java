package com.omnibuttie.therable.dataLoaders;

import android.content.Context;
import android.database.Cursor;

import com.omnibuttie.therable.provider.journalentry.EntryType;
import com.omnibuttie.therable.provider.status.StatusColumns;
import com.omnibuttie.therable.provider.status.StatusCursor;
import com.omnibuttie.therable.provider.status.StatusSelection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayarvin on 10/3/14.
 */
public class StatusLoader {
   public class StatusMap {
        long statusID;
        String statusName;

        public long getStatusID() {
            return statusID;
        }

        public String getStatusName() {
            return statusName;
        }

        StatusMap(long statusID, String statusName) {

            this.statusID = statusID;
            this.statusName = statusName;
        }
    }
    protected Context context;

    public StatusLoader(Context context) {
        this.context = context;
    }

    public List<StatusMap> getStatusesForEntryType(EntryType entryType) {
        StatusSelection selection = new StatusSelection();
        if (entryType != null) {
            selection.entryType(entryType);
        }
        StatusCursor cursor = selection.query(context.getContentResolver(), null, StatusColumns._ID + " asc");

        List<StatusMap> statuses = new ArrayList<StatusMap>();
        while (cursor.moveToNext()) {
            String typeString = "";
            switch (cursor.getEntryType()) {
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
            statuses.add(new StatusMap(cursor.getId(), cursor.getStatusName()));
        }
        return statuses;
    }
}
