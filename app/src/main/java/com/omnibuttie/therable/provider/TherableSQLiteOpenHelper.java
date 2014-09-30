package com.omnibuttie.therable.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.omnibuttie.therable.BuildConfig;
import com.omnibuttie.therable.provider.journalentry.JournalentryColumns;
import com.omnibuttie.therable.provider.status.StatusColumns;

public class TherableSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_FILE_NAME = "therable.db";
    private static final String TAG = TherableSQLiteOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    // @formatter:off
    private static final String SQL_CREATE_TABLE_JOURNALENTRY = "CREATE TABLE IF NOT EXISTS "
            + JournalentryColumns.TABLE_NAME + " ( "
            + JournalentryColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + JournalentryColumns.DATE_CREATED + " INTEGER, "
            + JournalentryColumns.DATE_MODIFIED + " INTEGER, "
            + JournalentryColumns.CONTENT + " TEXT, "
            + JournalentryColumns.IS_ARCHIVED + " INTEGER DEFAULT 'false', "
            + JournalentryColumns.SIMPLEDATE + " TEXT, "
            + JournalentryColumns.CAUSE + " TEXT NOT NULL, "
            + JournalentryColumns.INTENSITY + " REAL, "
            + JournalentryColumns.STATUS_ID + " INTEGER NOT NULL, "
            + JournalentryColumns.ENTRY_TYPE + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_status_id FOREIGN KEY (status_id) REFERENCES status (_id) ON DELETE SET NULL"
            + " );";
    private static final String SQL_CREATE_INDEX_JOURNALENTRY_DATE_CREATED = "CREATE INDEX IDX_JOURNALENTRY_DATE_CREATED "
            + " ON " + JournalentryColumns.TABLE_NAME + " ( " + JournalentryColumns.DATE_CREATED + " );";
    private static final String SQL_CREATE_INDEX_JOURNALENTRY_ENTRY_TYPE = "CREATE INDEX IDX_JOURNALENTRY_ENTRY_TYPE "
            + " ON " + JournalentryColumns.TABLE_NAME + " ( " + JournalentryColumns.ENTRY_TYPE + " );";
    private static final String SQL_CREATE_TABLE_STATUS = "CREATE TABLE IF NOT EXISTS "
            + StatusColumns.TABLE_NAME + " ( "
            + StatusColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StatusColumns.STATUS_NAME + " TEXT, "
            + StatusColumns.STATUS_PRIMARY_COLOR + " TEXT, "
            + StatusColumns.STATUS_SECONDARY_COLOR + " TEXT, "
            + StatusColumns.STATUS_TERTIARY_COLOR + " TEXT, "
            + StatusColumns.STATUS_QUATERNARY_COLOR + " TEXT, "
            + StatusColumns.STATUS_TYPE + " INTEGER NOT NULL "
            + " );";
    private static final String SQL_CREATE_INDEX_STATUS_STATUS_TYPE = "CREATE INDEX IDX_STATUS_STATUS_TYPE "
            + " ON " + StatusColumns.TABLE_NAME + " ( " + StatusColumns.STATUS_TYPE + " );";
    private static TherableSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final TherableSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:on

    private TherableSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mOpenHelperCallbacks = new TherableSQLiteOpenHelperCallbacks();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private TherableSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new TherableSQLiteOpenHelperCallbacks();
    }


    /*
     * Pre Honeycomb.
     */

    public static TherableSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static TherableSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Post Honeycomb.
     */

    private static TherableSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new TherableSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static TherableSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new TherableSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_JOURNALENTRY);
        db.execSQL(SQL_CREATE_INDEX_JOURNALENTRY_DATE_CREATED);
        db.execSQL(SQL_CREATE_INDEX_JOURNALENTRY_ENTRY_TYPE);
        db.execSQL(SQL_CREATE_TABLE_STATUS);
        db.execSQL(SQL_CREATE_INDEX_STATUS_STATUS_TYPE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
