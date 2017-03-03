package com.grace.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.http.response.BookCommentResponse;
import com.grace.book.utils.ImageLoaderUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxb
 * 17/3/3.
 */
public class BookCommentAdapter extends BaseAdapter {
    private Context context;
    private List<BookCommentResponse.BookComment> comments;

    public BookCommentAdapter(Context context, List<BookCommentResponse.BookComment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book_comment, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        BookCommentResponse.BookComment comment = comments.get(position);
        ImageLoaderUtils.setUserAvatarUrl(viewHolder.ivAvatar, comment.getAvatar());
        viewHolder.tvName.setText(comment.getUserName());
        viewHolder.tvComment.setText(comment.getMessage());
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_comment)
        TextView tvComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
