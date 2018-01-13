package com.ashgen.groupchatapp.chatactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        final LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerviewChat.setLayoutManager(linearLayoutManager);
        recyclerviewChat.setHasFixedSize(false);
        presenter = new ChatActivityPresenter();

        messageAdapter = new MessageAdapter(new ArrayList<Message>(),getIntent().getStringExtra("username"));
        recyclerviewChat.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItemPosition
                        + mPostsPerPage)) {
                    presenter.getMessages(messageAdapter.getLastItemId());
                    mIsLoading = true;
                }
            }
        });



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


    }

    @Override
    public String getMessage() {
        return edittextMessage.getText().toString();
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
