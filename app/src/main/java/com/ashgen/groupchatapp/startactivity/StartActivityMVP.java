package com.ashgen.groupchatapp.startactivity;

/**
 * Created by malavan on 12/01/18.
 */

public interface StartActivityMVP {

     interface View{
         String getUsername();
         void initializeChat(String username);
    }
    interface Presenter{
         void setView(StartActivityMVP.View view);
         void getStartedButtonClick();

    }



}
