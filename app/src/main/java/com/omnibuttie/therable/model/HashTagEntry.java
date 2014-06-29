package com.omnibuttie.therable.model;

import com.orm.SugarRecord;

/**
 * Created by mike on 29/06/14.
 */
public class HashTagEntry extends SugarRecord<HashTagEntry> {
    String tag;
    JournalEntry entry;

    public HashTagEntry(JournalEntry entry, String tag) {
        super();
        this.entry = entry;
        this.tag = tag;
    }

    public HashTagEntry() {
        super();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public JournalEntry getEntry() {
        return entry;
    }

    public void setEntry(JournalEntry entry) {
        this.entry = entry;
    }
}
