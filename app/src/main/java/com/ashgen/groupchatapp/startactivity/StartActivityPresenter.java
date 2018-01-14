package com.ashgen.groupchatapp.startactivity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ashgen.groupchatapp.chatactivity.Message;
import com.ashgen.groupchatapp.root.App;
import com.ashgen.groupchatapp.root.Constants;
import com.ashgen.groupchatapp.root.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

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
            view.showProgressBar();
            if (!TextUtils.isEmpty(view.getUsername().trim()))
            {
                App.setUserObject(new UserDetails(view.getUsername(),System.currentTimeMillis()+getColor()+view.getUsername(),getColor()));

                Message message = new Message();
                message.setColor(App.getUserObject().getColor());
                message.setName(App.getUserObject().getName());
                message.setText(Constants.STATUS_MESSAGE_JOINED);
                String timeStamp = new SimpleDateFormat("HHmm").format(Calendar.getInstance().getTime());
                message.setTime(timeStamp);
                message.setUniqueid(App.getUserObject().getUniqueID());
                message.setType(Constants.ALERT_MESSAGE);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("chat");
                final String pushkey = databaseReference.push().getKey();
                message.setKey(pushkey);
                databaseReference.child(pushkey).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        view.hideProgressBar();
                        if (task.isSuccessful())
                        {

                            Log.d("StartActivityPresenter", "onComplete() returned: " + task.isSuccessful());
                            FirebaseDatabase.getInstance().getReference().child("users").child(App.getUserObject().getUniqueID()).child("name").setValue(view.getUsername());

                            view.CreateUser(App.getUserObject());
                            view.initializeChat(view.getUsername());

                        }
                        else {
                            Log.d(TAG, "onComplete: summa iru");
                        }
                    }
                });



            }
        }
    }


    public String getColor()
    {
        Random r = new Random();
        int Low = 0;
        int High = 4;
        int Result = r.nextInt(High-Low) + Low;


        switch (Result)
        {
            case 0: return "RED";

            case 1: return "GREEN";

            case 2: return "BLUE";

            case 3: return "CYAN";

            case 4: return "BLACK";


            default: return "RED";
        }
    }

}
