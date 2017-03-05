package com.grace.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.event.AddStarEvent;
import com.grace.book.event.CancelStarEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.BaseBookRequest;
import com.grace.book.http.response.BaseResponse;
import com.grace.book.http.response.RecordResponse;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.utils.ToastUtils;
import com.grace.book.widget.theme.ColorBackButton;
import com.grace.book.widget.theme.ColorTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxb on 2017/2/15.
 */
public class RecordReadingAdapter extends BaseAdapter {
    private Context context;
    private List<RecordResponse.RecordInfo> records;

    public RecordReadingAdapter(Context context, List<RecordResponse.RecordInfo> records) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record_reading, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final RecordResponse.RecordInfo record = records.get(position);
        ImageLoaderUtils.setImageUrl(viewHolder.ivAvatar, record.getPic(), R.drawable.default_book_cover);
        viewHolder.tvName.setText(record.getBookName());
        viewHolder.tvType.setText(record.getBookTypeName());
        viewHolder.tvAuthor.setText(record.getAuthor());
        if (record.isBorrowing()) {
            viewHolder.btnCancel.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btnCancel.setVisibility(View.GONE);
        }
        if (record.isCollected()) {
            viewHolder.tvStar.setVisibility(View.VISIBLE);
            viewHolder.btnStar.setVisibility(View.GONE);
        } else {
            viewHolder.tvStar.setVisibility(View.GONE);
            viewHolder.btnStar.setVisibility(View.VISIBLE);
        }
        viewHolder.btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStar(record);
            }
        });

        return convertView;
    }

    private void addStar(final RecordResponse.RecordInfo record) {
        BaseBookRequest request = new BaseBookRequest();
        request.setBookId(record.getBookId());
        request.setAuthToken(SharedUtils.getUserToken());
        RequestManager.post(getClass().getSimpleName(), HttpData.BOOK_STAR, request, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
                record.setHasCollected();
                EventBus.getDefault().post(new AddStarEvent(record.getBookId()));
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(context, message);
            }
        });
    }

    static class ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.btn_cancel)
        ColorBackButton btnCancel;
        @Bind(R.id.tv_star)
        ColorTextView tvStar;
        @Bind(R.id.btn_star)
        ColorBackButton btnStar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            ThemeUtils.addThemeToView(btnCancel);
            ThemeUtils.addThemeToView(btnStar);
        }
    }
}
