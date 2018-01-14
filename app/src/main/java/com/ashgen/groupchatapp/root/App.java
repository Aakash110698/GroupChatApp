package com.ashgen.groupchatapp.root;

import android.app.Application;
import android.content.Intent;

import com.ashgen.groupchatapp.chatactivity.ChatActivity;
import com.ashgen.groupchatapp.chatactivity.Message;
import com.ashgen.groupchatapp.startactivity.StartActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    @Override
    public void onCreate() {
        super.onCreate();


    }
}
