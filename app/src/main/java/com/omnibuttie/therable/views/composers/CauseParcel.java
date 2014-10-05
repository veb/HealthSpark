package com.omnibuttie.therable.views.composers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rayarvin on 10/2/14.
 */
public class CauseParcel implements Parcelable {
    public static final Parcelable.Creator<CauseParcel> CREATOR = new Parcelable.Creator<CauseParcel>() {
        public CauseParcel createFromParcel(Parcel source) {
            return new CauseParcel(source);
        }

        public CauseParcel[] newArray(int size) {
            return new CauseParcel[size];
        }
    };
    String cause;

    public CauseParcel(Parcel in) {
        this.cause = in.readString();
    }

    public CauseParcel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cause);
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "CauseParcel{" +
                "cause='" + cause + '\'' +
                '}';
    }
}

