package com.ashgen.groupchatapp.startactivity;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by malavan on 12/01/18.
 */

public class StartActivityPresenter implements StartActivityMVP.Presenter {


    private   String TAG = getClass().getSimpleName() ;
    @Nullable
    private StartActivityMVP.View view;



    @Override
    public void setView(@Nullable StartActivityMVP.View view) {
        this.view = view;


    }

    @Override
    public void getStartedButtonClick() {
        if (view!=null)
        {
            if (!TextUtils.isEmpty(view.getUsername().trim()))
            {
                view.initializeChat(view.getUsername());
            }
        }
    }


}
