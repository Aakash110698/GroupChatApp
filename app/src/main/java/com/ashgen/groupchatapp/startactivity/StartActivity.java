package com.ashgen.groupchatapp.startactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;

import com.ashgen.groupchatapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity implements StartActivityMVP.View {

    @BindView(R.id.button_getstarted)
    Button buttonGetstarted;
    @BindView(R.id.textinputlayout_username)
    TextInputLayout textinputlayoutUsername;

    StartActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        presenter = new StartActivityPresenter();


    }

    @Override
    public String getUsername() {
        return textinputlayoutUsername.getEditText().getText().toString();
    }

    @Override
    public void initializeChat(String username) {
        Intent intent = new Intent(StartActivity.this,StartActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    @OnClick(R.id.button_getstarted)
    public void onViewClicked() {
        presenter.getStartedButtonClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }
}

