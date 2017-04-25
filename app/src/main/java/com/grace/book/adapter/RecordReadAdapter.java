package com.grace.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.dialog.InputDialog;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.CommentRequest;
import com.grace.book.http.request.RecommendRequest;
import com.grace.book.http.response.BaseResponse;
import com.grace.book.http.response.RecordResponse;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.widget.theme.ColorBackButton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxb on 2017/2/15.
 */
public class RecordReadAdapter extends BaseAdapter {
    private Context context;
    private List<RecordResponse.RecordInfo> records;

    public RecordReadAdapter(Context context, List<RecordResponse.RecordInfo> records) {
        this.context = context;
        this.records = records;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record_read, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final RecordResponse.RecordInfo record = records.get(position);
        ImageLoaderUtils.setImageUrl(viewHolder.ivAvatar, record.getPic(), R.drawable.default_book_cover);
        viewHolder.tvName.setText(record.getBookName());
        viewHolder.tvType.setText(record.getBookTypeName());
        viewHolder.tvAuthor.setText(record.getAuthor());
        if (record.isCommented()) {
            viewHolder.tvComment.setVisibility(View.VISIBLE);
            viewHolder.btnComment.setVisibility(View.GONE);
        } else {
            viewHolder.tvComment.setVisibility(View.GONE);
            viewHolder.btnComment.setVisibility(View.VISIBLE);
        }
        if (record.isRecommended()) {
            viewHolder.tvRecommend.setVisibility(View.VISIBLE);
            viewHolder.btnRecommend.setVisibility(View.GONE);
        } else {
            viewHolder.tvRecommend.setVisibility(View.GONE);
            viewHolder.btnRecommend.setVisibility(View.VISIBLE);
        }
        viewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InputDialog.Builder(context).setTitle("评论")
                        .setInputHint("这本书怎么样？")
                        .setPositiveButton("发表", new InputDialog.ButtonActionListener() {
                            @Override
                            public void onClick(CharSequence inputText) {
                                addComment(record, inputText.toString());
                            }
                        }).show();
            }
        });
        viewHolder.btnRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecommend(record);
            }
        });

        return convertView;
    }

    private void addComment(final RecordResponse.RecordInfo record, String comment) {
        CommentRequest request = new CommentRequest();
        request.setBookId(record.getBookId());
        request.setMsg(comment);
        RequestManager.post(getClass().getSimpleName(), HttpData.BOOK_COMMENT, request, new CallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse result) {
                record.setHasComment();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(context, message);
            }
        });
    }

    private void addRecommend(final RecordResponse.RecordInfo record) {
        RecommendRequest request = new RecommendRequest();
        request.setBorrowId(record.getBorrowId());
        RequestManager.post(getClass().getSimpleName(), HttpData.BOOK_RECOMMEND, request, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
                record.setHasRecommend();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(context, message);
            }
        });
    }

    class ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.tv_comment)
        TextView tvComment;
        @Bind(R.id.tv_recommend)
        TextView tvRecommend;
        @Bind(R.id.btn_comment)
        ColorBackButton btnComment;
        @Bind(R.id.btn_recommend)
        ColorBackButton btnRecommend;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            ThemeUtils.addThemeToView(btnComment);
            ThemeUtils.addThemeToView(btnRecommend);
        }
    }
}
