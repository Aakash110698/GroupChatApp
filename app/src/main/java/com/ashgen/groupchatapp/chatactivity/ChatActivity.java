package com.ashgen.groupchatapp.chatactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ashgen.groupchatapp.R;
import com.ashgen.groupchatapp.root.App;
import com.ashgen.groupchatapp.root.Constants;
import com.ashgen.groupchatapp.root.SharedPreferenceHelper;
import com.ashgen.groupchatapp.root.UserDetails;
import com.ashgen.groupchatapp.startactivity.StartActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ashgen.groupchatapp.root.App.getUserObject;

public class ChatActivity extends AppCompatActivity implements ChatActivityMVP.View {

    @BindView(R.id.edittext_message)
    EditText edittextMessage;
    @BindView(R.id.imgbutton_send)
    ImageButton imgbuttonSend;
    @BindView(R.id.recyclerview_chat)
    RecyclerView recyclerviewChat;

    ChatActivityPresenter presenter;
    private int mTotalItemCount;
    private MessageAdapter messageAdapter;
    private int mLastVisibleItemPosition;
    private boolean mIsLoading = false;
    private int mPostsPerPage =20;
     LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
         linearLayoutManager =new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);

        recyclerviewChat.setLayoutManager(linearLayoutManager);

        presenter = new ChatActivityPresenter();


        messageAdapter = new MessageAdapter(new ArrayList<Message>(),App.getUserObject().getName());




    }

    @OnClick(R.id.imgbutton_send)
    public void onViewClicked() {
        presenter.sendButtonClicked();
        edittextMessage.setText("");


    }

    @Override
    public void setAdapter(List<Message> messageList) {
       messageAdapter =  new MessageAdapter(messageList,App.getUserObject().getName());
        recyclerviewChat.setAdapter(messageAdapter);
    }

    @Override
    public void notifyData(List<Message> messages) {
        messageAdapter.addAll(messages);
        linearLayoutManager.scrollToPosition(messageAdapter.messageList.size()-1);



    }

    @Override
    public String getMessage() {
        return edittextMessage.getText().toString().trim();
    }

    @Override
    public void isLoading(boolean isloading) {
        this.mIsLoading = isloading;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_chat,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout)
        {
            SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(ChatActivity.this);
            sharedPreferenceHelper.endSession();
            UserDetails userDetails = getUserObject();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("chat");
            String pus
                    = databaseReference.push().getKey();
            Message message = new Message(userDetails.getName(), Constants.STATUS_MESSAGE_LEFT,"NONE","00:00",Constants.ALERT_MESSAGE,userDetails.getUniqueID(),pus);
            databaseReference.child(pus).setValue(message);
            startActivity(new Intent(ChatActivity.this, StartActivity.class));
            FirebaseMessaging.getInstance().unsubscribeFromTopic(getString(R.string.topic));
            finish();
        }
        return true;
    }
}
