package com.omnibuttie.therable.model;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by rayarvin on 6/17/14.
 */
public class User extends SugarRecord<User> {
    @Ignore
    String userToken;

    long userID;
    String username;

    public User(String userToken, long userID, String username) {
        super();
        this.userToken = userToken;
        this.userID = userID;
        this.username = username;
    }

    public User() {

        super();
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "userToken='" + userToken + '\'' +
                ", userID=" + userID +
                ", username='" + username + '\'' +
                '}';
    }
}
