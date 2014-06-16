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

    public User(Context context, String userToken, long userID, String username) {
        super(context);
        this.userToken = userToken;
        this.userID = userID;
        this.username = username;
    }

    public User(Context context) {

        super(context);
    }
}
