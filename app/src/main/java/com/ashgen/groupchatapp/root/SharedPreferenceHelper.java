package com.ashgen.groupchatapp.root;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by malavan on 14/01/18.
 */

public class SharedPreferenceHelper {
    Context context;
    SharedPreferences sharedPreferences;

    public SharedPreferenceHelper(Context context) {
        this.context = context;
    }

    public void putUserDetails(UserDetails userDetails) {
        sharedPreferences = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", userDetails.getName());
        editor.putString("uniqueid", userDetails.getUniqueID());
        editor.putString("color", userDetails.getColor());
        editor.apply();
    }

    public UserDetails getUserDetails() {
        sharedPreferences = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String uniqueid = sharedPreferences.getString("uniqueid", "");
        String color = sharedPreferences.getString("color", "");
        App.setUserObject(new UserDetails(name, uniqueid, color));
        return new UserDetails(name, uniqueid, color);
    }
    public boolean isUserLoggedIn()
    {
        sharedPreferences = context.getSharedPreferences("userdetails",Context.MODE_PRIVATE);
        return sharedPreferences.contains("name");
    }
    public void endSession(){
        sharedPreferences = context.getSharedPreferences("userdetails",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

}
