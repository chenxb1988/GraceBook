package com.grace.book.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.activity.ContactListActivity;
import com.grace.book.http.response.GroupListResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.grace.book.utils.ExtraUtils.CHURCH_ID;
import static com.grace.book.utils.ExtraUtils.GROUP_ID;

/**
 * Created by chenxb on 2017/2/15.
 */
public class GroupAdapter extends BaseAdapter {
    private Context context;
    private List<GroupListResponse.GroupInfo> groupInfos;

    public GroupAdapter(Context context, List<GroupListResponse.GroupInfo> groupInfos) {
        this.context = context;
        this.groupInfos = groupInfos;
    }

    @Override
    public int getCount() {
        return groupInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return groupInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fellow, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        final GroupListResponse.GroupInfo groupInfo = groupInfos.get(position);
        viewHolder.mText.setText(groupInfo.getGroupName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactListActivity.class);
                intent.putExtra(CHURCH_ID, groupInfo.getChurchId());
                intent.putExtra(GROUP_ID, groupInfo.getGrouphId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
