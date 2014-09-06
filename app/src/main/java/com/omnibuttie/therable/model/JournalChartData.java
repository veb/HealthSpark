package com.omnibuttie.therable.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by rayarvin on 9/6/14.
 */
public class JournalChartData extends SugarRecord<JournalChartData> {
    int weeknumber;
    Date weekstart;
    Date weekend;
    int moodcount;
    int mood_index;

    public JournalChartData(int weeknumber, Date weekstart, Date weekend, int moodcount, int mood_index) {
        this.weeknumber = weeknumber;
        this.weekstart = weekstart;
        this.weekend = weekend;
        this.moodcount = moodcount;
        this.mood_index = mood_index;
    }

    public JournalChartData() {

    }

    @Override
    public String toString() {
        return "JournalChartData{" +
                "weeknumber=" + weeknumber +
                ", weekstart=" + weekstart +
                ", weekend=" + weekend +
                ", moodcount=" + moodcount +
                ", mood_index=" + mood_index +
                '}';
    }

    public int getWeeknumber() {
        return weeknumber;
    }

    public void setWeeknumber(int weeknumber) {
        this.weeknumber = weeknumber;
    }

    public Date getWeekstart() {
        return weekstart;
    }

    public void setWeekstart(Date weekstart) {
        this.weekstart = weekstart;
    }

    public Date getWeekend() {
        return weekend;
    }

    public void setWeekend(Date weekend) {
        this.weekend = weekend;
    }

    public int getMoodcount() {
        return moodcount;
    }

    public void setMoodcount(int moodcount) {
        this.moodcount = moodcount;
    }

    public int getMood_index() {
        return mood_index;
    }

    public void setMood_index(int mood_index) {
        this.mood_index = mood_index;
    }
}
