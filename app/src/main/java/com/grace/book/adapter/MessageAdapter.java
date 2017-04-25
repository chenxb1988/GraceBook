package com.grace.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.http.response.MessageResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageResponse.Message> messages;

    public MessageAdapter(Context context, List<MessageResponse.Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_message_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final MessageResponse.Message message = messages.get(position);
        holder.mTvName.setText(message.getMessageTitle());
        holder.mTvDateline.setText(message.getUpdateTime());
        holder.mTvText.setText(message.getMessageContent());
        if (message.getStatus() == 0) {
            holder.mTvDot.setVisibility(View.VISIBLE);
        } else {
            holder.mTvDot.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        @Bind((R.id.tv_name))
        TextView mTvName;
        @Bind((R.id.tv_dateline))
        TextView mTvDateline;
        @Bind((R.id.tv_text))
        TextView mTvText;
        @Bind((R.id.tv_dot))
        View mTvDot;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
