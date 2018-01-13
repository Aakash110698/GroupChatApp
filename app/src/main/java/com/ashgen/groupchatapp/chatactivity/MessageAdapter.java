package com.ashgen.groupchatapp.chatactivity;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashgen.groupchatapp.R;
import com.ashgen.groupchatapp.root.App;
import com.ashgen.groupchatapp.root.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public List<Message> messageList = new ArrayList<>();
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
        switch (holder.getItemViewType())
        {
            case 0: ((MessageViewHolderAlert)holder).setMessage(messageList.get(position));
                    break;
            case 1: ((MessageViewHolderMine)holder).setMessage(messageList.get(position));
                    break;
            case 2: ((MessageViewHolderOthers)holder).setMessage(messageList.get(position));
                    break;
        }
    }



    @Override
    public int getItemViewType(int position) {
       String type =  messageList.get(position).getType();
       String uniqueid = messageList.get(position).getUniqueid();

       if (type.equals(Constants.ALERT_MESSAGE))
       {
           return 0;
       }
       else if (type.equals(Constants.NORMAL_MESSAGE) && uniqueid.equals(App.getUserObject().getUniqueID()))
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

    public void addAll(List<Message> messageList)
    {
        this.messageList = messageList;
        notifyDataSetChanged();

    }


    public String getLastItemId() {
        return messageList.get(messageList.size() - 1).getKey();
    }
    public class MessageViewHolderMine extends RecyclerView.ViewHolder{



        @BindView(R.id.textview_message_mine)
        TextView textView_message_mine;


        public MessageViewHolderMine(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setMessage(Message message) {

            textView_message_mine.setText(message.getText());


        }
    }

    public class MessageViewHolderOthers extends RecyclerView.ViewHolder{

        @BindView(R.id.textview_message)
        TextView textView_message;

        public MessageViewHolderOthers(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void setMessage(Message message)
        {
          String text = message.getText();
          String name = message.getName();

           Spanned te
                   =  Html.fromHtml("<p>"+text+"</p>");
            textView_message.setText(text);

        }

    }

    public class MessageViewHolderAlert extends RecyclerView.ViewHolder{

        @BindView(R.id.textView_status)
        TextView textView_status;
        @BindView(R.id.textView_username)
        TextView textView_username;


        public MessageViewHolderAlert(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
        public void setMessage(Message message)
        {
            textView_status.setText(message.getText());
            textView_username.setText(message.getName());
        }
    }

}
