package com.ashgen.groupchatapp.chatactivity;



public interface ChatActivityMVP  {
    interface View{
        void setAdapter();

    }
    interface Presenter{
        void setView(ChatActivityMVP.View view);
    }
}
