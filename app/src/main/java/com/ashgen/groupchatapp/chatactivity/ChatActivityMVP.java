package com.ashgen.groupchatapp.chatactivity;


import java.util.List;

public interface ChatActivityMVP  {
    interface View{
        void setAdapter(List<Message> messages);
        void notifyData(List<Message> messages);
        String getMessage();
        void isLoading(boolean isloading);


    }
    interface Presenter{
        void setView(ChatActivityMVP.View view);
        void loadData();
        void sendButtonClicked();
        void getMessages(String key);
    }
}
