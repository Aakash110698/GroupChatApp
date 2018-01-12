package com.ashgen.groupchatapp.chatactivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashgen.groupchatapp.R;
import com.ashgen.groupchatapp.root.Constants;

import java.util.ArrayList;
import java.util.List;



public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Message> messageList = new ArrayList<>();
    private String uniqueID;

    public MessageAdapter(List<Message> messageList,String uniqueID) {
        this.messageList = messageList;
        this.uniqueID  = uniqueID;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case 0: return new MessageViewHolderAlert(
                    LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.alert_message_chat,parent,false));

            case 1: return new MessageViewHolderMine(
                    LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.my_message_chat,parent,false));

            case 2: return new MessageViewHolderOthers(
                    LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.others_message_chat,parent,false));

            default: return null;


        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }



    @Override
    public int getItemViewType(int position) {
       String type =  messageList.get(position).getType();
       String uniqueid = messageList.get(position).getUniqueid();

       if (type.equals(Constants.ALERT_MESSAGE))
       {
           return 0;
       }
       else if (type.equals(Constants.NORMAL_MESSAGE) && type.equals(uniqueid))
       {
           return 1;
       }
       else if (type.equals(Constants.NORMAL_MESSAGE))
       {
           return 2;
       }

       return 3;
    }




    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolderMine extends RecyclerView.ViewHolder{

        public MessageViewHolderMine(View itemView) {
            super(itemView);
        }
    }

    public class MessageViewHolderOthers extends RecyclerView.ViewHolder{

        public MessageViewHolderOthers(View itemView) {
            super(itemView);
        }
    }

    public class MessageViewHolderAlert extends RecyclerView.ViewHolder{

        public MessageViewHolderAlert(View itemView) {
            super(itemView);
        }
    }

}
