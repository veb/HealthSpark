package com.omnibuttie.therable.model;

import com.orm.SugarRecord;

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

    String mood;
    int intensity;

    int cause;

    public JournalEntry() {
        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;
        this.isArchived = false;
        this.simpledate = new SimpleDateFormat("yyyy-MM-dd").format(this.dateModified);
    }

    public JournalEntry(Date dateCreated, Date dateModified, String content, String mood, int intensity) {
        super();
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.content = content;
        this.mood = mood;
        this.isArchived = false;
        this.intensity = intensity;
        this.simpledate = new SimpleDateFormat("yyyy-MM-dd").format(this.dateModified);
    }

    public int getCause() {
        return cause;
    }

    public void setCause(int cause) {
        this.cause = cause;
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

    public String getMood() {
        return mood;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public void setMood(String mood) {
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
