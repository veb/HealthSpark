package com.omnibuttie.therable.model;

import android.content.Context;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by rayarvin on 6/16/14.
 */
public class JournalEntries extends SugarRecord<JournalEntries> {
    Date dateCreated;
    Date dateModified;
    String content;
    int mood;
    int intensity;

    User owner;

    public JournalEntries(Context context, String content, int mood, int intensity, User owner) {
        super(context);
        this.content = content;
        this.mood = mood;
        this.intensity = intensity;
        this.owner = owner;

        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;

    }

    public JournalEntries(Context context) {
        super(context);
        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;
    }

    public JournalEntries(Context context, Date dateCreated, Date dateModified, String content, int mood, int intensity, User owner) {
        super(context);
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.content = content;
        this.mood = mood;

        this.intensity = intensity;
        this.owner = owner;
    }
}
