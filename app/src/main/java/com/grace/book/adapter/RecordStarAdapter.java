package com.grace.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.event.CancelStarEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.CancelStarRequest;
import com.grace.book.http.response.BaseResponse;
import com.grace.book.http.response.RecordResponse;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.utils.ToastUtils;
import com.grace.book.widget.theme.ColorBackButton;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxb on 2017/2/15.
 */
public class RecordStarAdapter extends BaseAdapter {
    private Context context;
    private List<RecordResponse.RecordInfo> records;

    public RecordStarAdapter(Context context, List<RecordResponse.RecordInfo> records) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record_star, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final RecordResponse.RecordInfo record = records.get(position);
        viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelStar(record);
            }
        });

        return convertView;
    }

    private void cancelStar(final RecordResponse.RecordInfo record) {
        CancelStarRequest request = new CancelStarRequest();
        request.setCollectId(record.getCollectId());
        request.setAuthToken(SharedUtils.getUserToken());
        RequestManager.post(getClass().getSimpleName(), HttpData.BOOK_CALCEL_STAR, request, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
                EventBus.getDefault().post(new CancelStarEvent(record.getCollectId()));
            }

            @Override
            public void onFailure(String message) {
                ToastUtils.showErrorToasty(context, message);
            }
        });
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.btn_cancel)
        ColorBackButton btnCancel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            ThemeUtils.addThemeToView(btnCancel);
        }
    }
}
