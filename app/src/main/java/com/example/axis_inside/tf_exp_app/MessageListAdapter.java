package com.example.axis_inside.tf_exp_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by axis-inside on 2/6/17.
 */

public class MessageListAdapter extends ArrayAdapter<CommonDetails> {
    private Context ctx;
    public ArrayList<CommonDetails> messageListArray;
    public static boolean isDialogOpen = true;
    public MessageListAdapter(Context context, int textViewResourceId, ArrayList<CommonDetails> messageListArray) {
        super(context, textViewResourceId);
        this.messageListArray = messageListArray;
        this.ctx = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        LinearLayout layout;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_details_list_item, parent, false);
            holder.setMessageTo((TextView) convertView.findViewById(R.id.tvTitle));
            holder.setMessageContent((TextView) convertView.findViewById(R.id.tvContent));
            convertView.setTag(holder);
        } else {
            layout = (LinearLayout) convertView;
            holder = (Holder) layout.getTag();
        }
        CommonDetails message = getItem(position);
        holder.getMessageTo().setText(message.getTitle()+ " : ");
        holder.getMessageContent().setText(message.getDetails());
        return convertView;
    }
    @Override
    public int getCount() {
        return messageListArray.size();
    }
    @Override
    public CommonDetails getItem(int position) {
        return messageListArray.get(position);
    }
    public void setArrayList(ArrayList<CommonDetails> messageList) {
        this.messageListArray = messageList;
        notifyDataSetChanged();
    }
    private class Holder {
        public TextView getMessageTo() {
            return messageTo;
        }

        public void setMessageTo(TextView messageTo) {
            this.messageTo = messageTo;
        }

        public TextView getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(TextView messageContent) {
            this.messageContent = messageContent;
        }

        public TextView messageTo, messageContent;
    }
}
