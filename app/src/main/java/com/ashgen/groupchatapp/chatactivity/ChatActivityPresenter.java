package com.ashgen.groupchatapp.chatactivity;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.ashgen.groupchatapp.root.App;
import com.ashgen.groupchatapp.root.Constants;
import com.ashgen.groupchatapp.root.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by malavan on 13/01/18.
 */

public class ChatActivityPresenter implements ChatActivityMVP.Presenter {
    ChatActivityMVP.View view;
    private boolean mIsLoading=false;
    private int mPostsPerPage=20;


    @Override
    public void setView(ChatActivityMVP.View view) {
      this.view = view;
    }

    @Override
    public void loadData() {
        view.setAdapter(new ArrayList<Message>());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("chat");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message user;
                List<Message> userModels = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    userModels.add(userSnapshot.getValue(Message.class));
                }

                view.notifyData(userModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void sendButtonClicked() {
        if (view!=null)
        {
            if (!TextUtils.isEmpty(view.getMessage()))
            {
                UserDetails userDetails = App.getUserObject();
                Message message = new Message();
                message.setColor(userDetails.getColor());
                message.setName(userDetails.getName());
                message.setText(view.getMessage());
                String timeStamp = new SimpleDateFormat("HHmm").format(Calendar.getInstance().getTime());
                message.setTime(timeStamp);
                message.setUniqueid(userDetails.getUniqueID());
                message.setType(Constants.NORMAL_MESSAGE);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("chat");
                String pushkey = databaseReference.push().getKey();
                message.setKey(pushkey);
                databaseReference.child(pushkey).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Log.d("ChatActivity", "onComplete() returned: " + task.isSuccessful());
                        }
                    }
                });



            }
        }
    }

    @Override
    public void getMessages(String nodeId) {
        Query query;

        if (nodeId == null)
            query = FirebaseDatabase.getInstance().getReference().child("chat")
                    .orderByKey()
                    .limitToFirst(10);
        else
            query = FirebaseDatabase.getInstance().getReference()
                    .child("chat")
                    .orderByKey()
                    .startAt(nodeId)
                    .limitToFirst(10);

        Log.d("ss", "getMessages: "+query.toString());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message user;
                List<Message> userModels = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    userModels.add(userSnapshot.getValue(Message.class));
                }
                view.notifyData(userModels);
                view.isLoading(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.isLoading(false);
            }
        });
    }


}
