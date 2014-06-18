package com.omnibuttie.therable.model;

import android.content.Context;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by rayarvin on 6/16/14.
 */
public class JournalEntry extends SugarRecord<JournalEntry> {
    Date dateCreated;
    Date dateModified;
    String content;
    int mood;
    int intensity;

    User owner;

    public JournalEntry(Context context, String content, int mood, int intensity, User owner) {
        super(context);
        this.content = content;
        this.mood = mood;
        this.intensity = intensity;
        this.owner = owner;

        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;

    }

    public JournalEntry(Context context) {
        super(context);
        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;
    }

    public JournalEntry(Context context, Date dateCreated, Date dateModified, String content, int mood, int intensity, User owner) {
        super(context);
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.content = content;
        this.mood = mood;

        this.intensity = intensity;
        this.owner = owner;
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

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", content='" + content + '\'' +
                ", mood=" + mood +
                ", intensity=" + intensity +
                ", owner=" + owner +
                '}';
    }
}
