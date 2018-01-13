package com.ashgen.groupchatapp.startactivity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ashgen.groupchatapp.chatactivity.Message;
import com.ashgen.groupchatapp.root.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


                Message message = new Message();
                message.setColor("BLACK");
                message.setName("Malavan");
                message.setText(Constants.STATUS_MESSAGE_JOINED);
                message.setTime("21:00");
                message.setUniqueid("Malavan");
                message.setType(Constants.ALERT_MESSAGE);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("chat");
                String pushkey = databaseReference.push().getKey();
                message.setKey(pushkey);
                databaseReference.child(pushkey).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        view.hideProgressBar();
                        if (task.isSuccessful())
                        {

                            Log.d("StartActivityPresenter", "onComplete() returned: " + task.isSuccessful());
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


}
