package com.grace.book.adapter;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.activity.UserInfoActivity;
import com.grace.book.http.response.UserInfo;
import com.grace.book.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.xiaopan.java.lang.StringUtils;

/**
 * Created by chenxb on 2017/2/15.
 */
public class ContactAdapter extends BaseAdapter {
    private Context context;
    private List<UserInfo> userInfos;

    public ContactAdapter(Context context, List<UserInfo> userInfos) {
        this.context = context;
        this.userInfos = userInfos;
    }

    @Override
    public int getCount() {
        return userInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfos.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        final UserInfo userInfo = userInfos.get(position);
        viewHolder.mText.setText(userInfo.getRealName());
        viewHolder.mTvPhone.setText(userInfo.getMobile());
        viewHolder.mTvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = userInfo.getMobile();
                if (!StringUtils.isEmpty(mobile)) {
                    copy(mobile);
                    ToastUtils.showSuccessToasty(context, "手机号已复制到粘贴板");
                    Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(intentPhone);
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra(UserInfoActivity.KEY_USERID, userInfo.getUserId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void copy(String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, content));
    }

    static class ViewHolder {
        @Bind(R.id.text)
        TextView mText;
        @Bind(R.id.tv_phone)
        TextView mTvPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}