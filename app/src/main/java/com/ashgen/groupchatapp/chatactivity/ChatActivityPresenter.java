package com.ashgen.groupchatapp.chatactivity;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.ashgen.groupchatapp.root.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("chat");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message user;
                List<Message> userModels = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    userModels.add(userSnapshot.getValue(Message.class));
                }

                view.setAdapter(userModels);
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
                Message message = new Message();
                message.setColor("BLACK");
                message.setName("Malavan");
                message.setText(view.getMessage());
                message.setTime("21:00");
                message.setUniqueid("Malavan");
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
            query = FirebaseDatabase.getInstance().getReference()
                    .child(Constants.FIREBASE_DATABASE_LOCATION_USERS)
                    .orderByKey()
                    .limitToFirst(mPostsPerPage);
        else
            query = FirebaseDatabase.getInstance().getReference()
                    .child(Constants.FIREBASE_DATABASE_LOCATION_USERS)
                    .orderByKey()
                    .startAt(nodeId)
                    .limitToFirst(mPostsPerPage);

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
