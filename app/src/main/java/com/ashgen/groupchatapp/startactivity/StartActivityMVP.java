package com.ashgen.groupchatapp.startactivity;

import com.ashgen.groupchatapp.root.UserDetails;

/**
 * Created by malavan on 12/01/18.
 */

public interface StartActivityMVP {

     interface View{
         String getUsername();
         void initializeChat(String username);
         void CreateUser(UserDetails userDetails);
         void showProgressBar();
         void hideProgressBar();

         void showMessage(String message);
     }
    interface Presenter{
         void setView(StartActivityMVP.View view);
         void getStartedButtonClick();

    }



}
