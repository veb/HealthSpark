package com.omnibuttie.therable.model;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rayarvin on 6/16/14.
 */
public class JournalEntry extends SugarRecord<JournalEntry> {
    Date dateCreated;
    Date dateModified;
    String content;
    boolean isArchived;

    String simpledate;

    int mood;
    int intensity;



    public JournalEntry(String content, int mood, int intensity) {
        super();
        this.content = content;
        this.mood = mood;
        this.intensity = intensity;

        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;
        this.simpledate = new SimpleDateFormat("yyyy-MM-dd").format(this.dateModified);

    }

    public JournalEntry() {
        this.isArchived = false;
    }

    public JournalEntry(Date dateCreated, Date dateModified, String content, int mood, int intensity) {
        super();
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.content = content;
        this.mood = mood;
        this.isArchived = false;
        this.intensity = intensity;
        this.simpledate = new SimpleDateFormat("yyyy-MM-dd").format(this.dateModified);
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
        this.simpledate = new SimpleDateFormat("yyyy-MM-dd").format(this.dateModified);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMood() {
        return mood;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }


    @Override
    public String toString() {
        return "JournalEntry{" +
                "dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", content='" + content + '\'' +
                ", mood=" + mood +
                ", intensity=" + intensity +
                ", isArchived=" + isArchived +
                '}';
    }
}
