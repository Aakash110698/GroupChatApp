package com.ashgen.groupchatapp.root;

import android.app.Application;

/**
 * Created by malavan on 13/01/18.
 */

public  class App extends Application {
    static UserDetails users;

    public static void setUserObject(UserDetails user){
        users=user;
    }

    public static UserDetails getUserObject(){
        return users;
    }
}
