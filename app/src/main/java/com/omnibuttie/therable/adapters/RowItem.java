package com.omnibuttie.therable.adapters;

/**
 * Created by rayarvin on 6/17/14.
 */
public class RowItem {
    String title;
    int icon;

    public RowItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
