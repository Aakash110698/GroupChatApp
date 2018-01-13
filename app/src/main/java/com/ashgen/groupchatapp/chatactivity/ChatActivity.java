package com.ashgen.groupchatapp.chatactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ashgen.groupchatapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


        messageAdapter = new MessageAdapter(new ArrayList<Message>(),getIntent().getStringExtra("username"));





    }

    @OnClick(R.id.imgbutton_send)
    public void onViewClicked() {
        presenter.sendButtonClicked();


    }

    @Override
    public void setAdapter(List<Message> messageList) {
       messageAdapter =  new MessageAdapter(messageList,getIntent().getStringExtra("username"));
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
}
