package com.omnibuttie.therable.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.omnibuttie.therable.BuildConfig;

/**
 * Implement your custom database creation or upgrade code here.
 * <p/>
 * This file will not be overwritten if you re-run the content provider generator.
 */
public class TherableSQLiteOpenHelperCallbacks {
    private static final String TAG = TherableSQLiteOpenHelperCallbacks.class.getSimpleName();

    public void onOpen(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onOpen");
        // Insert your db open code here.
    }

    public void onPreCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPreCreate");
        // Insert your db creation code here. This is called before your tables are created.
    }

    public void onPostCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPostCreate");

        db.execSQL("INSERT INTO `status` VALUES(0,'Angry','d01716','f69988','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(1,'Happy','ffa000','ffe082','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(2,'Panicked','afb42b','e6ee9c','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(3,'Energized','f57c00','ffcc80','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(4,'Sad','303f9f','9fa8da','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(5,'Scared','0a7e07','72d572','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(6,'Apathetic','455a64','b0bec5','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(7,'Sick','7b1fa2','ce93d8','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(8,'Moody','c2185b','f48fb1','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(9,'Relaxed','0288d1','81d4fa','','',0)");
        db.execSQL("INSERT INTO `status` VALUES(10,'Walk','','','','',1)");
        db.execSQL("INSERT INTO `status` VALUES(11,'Run','','','','',1)");
        db.execSQL("INSERT INTO `status` VALUES(12,'Weights','','','','',1)");
        db.execSQL("INSERT INTO `status` VALUES(13,'Cardio','','','','',1)");
        db.execSQL("INSERT INTO `status` VALUES(14,'Legday','','','','',1)");
        db.execSQL("INSERT INTO `status` VALUES(15,'MADE IT WORSE','','','','',2)");
        db.execSQL("INSERT INTO `status` VALUES(16,'Very Discomforting','','','','',2)");
        db.execSQL("INSERT INTO `status` VALUES(17,'Mild discomfort','','','','',2)");
        db.execSQL("INSERT INTO `status` VALUES(18,'No effect','','','','',2)");
        db.execSQL("INSERT INTO `status` VALUES(19,'Sort of works','','','','',2)");
        db.execSQL("INSERT INTO `status` VALUES(20,'Mildly effective','','','','',2)");
        db.execSQL("INSERT INTO `status` VALUES(21,'Effective','','','','',2)");
        db.execSQL("INSERT INTO `status` VALUES(22,'Super Effective','','','','',2)");
        db.execSQL("INSERT INTO `status` VALUES(23,'None','b2ff59','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(24,'Very mild','eeff41','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(25,'Discomforting','ffee58','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(26,'Tolerable','fdd835','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(27,'Distressing','fb8c00','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(28,'Very Distressing','ef6c00','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(29,'Intense','f4511e','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(30,'Very intense','d84315','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(31,'Horrible','e51c23','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(32,'Excruciating','d01716','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(33,'Unspeakable','b0120a','','','',3)");
        db.execSQL("INSERT INTO `status` VALUES(34,'CUSTOM','000000','','','',4)");

    }

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Insert your upgrading code here.
    }
}
