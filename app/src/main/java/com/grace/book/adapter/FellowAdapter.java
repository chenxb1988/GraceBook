package com.grace.book.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.activity.GroupListActivity;
import com.grace.book.http.response.FellowListResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.grace.book.utils.ExtraUtils.CHURCH_ID;

/**
 * Created by chenxb on 2017/2/15.
 */
public class FellowAdapter extends BaseAdapter {
    private Context context;
    private List<FellowListResponse.FellowInfo> fellowInfos;

    public FellowAdapter(Context context, List<FellowListResponse.FellowInfo> fellowInfos) {
        this.context = context;
        this.fellowInfos = fellowInfos;
    }

    @Override
    public int getCount() {
        return fellowInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return fellowInfos.get(position);
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
        final FellowListResponse.FellowInfo fellowInfo = fellowInfos.get(position);
        viewHolder.mText.setText(fellowInfo.getChurchName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupListActivity.class);
                intent.putExtra(CHURCH_ID, fellowInfo.getChurchId());
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
