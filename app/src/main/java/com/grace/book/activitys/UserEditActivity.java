package com.grace.book.activitys;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.base.BaseLoadingActivity;
import com.grace.book.http.response.UserInfo;
import com.grace.book.utils.ImageLoaderUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.Bind;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class UserEditActivity extends BaseLoadingActivity {
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_gentle)
    TextView mTvGentle;
    @Bind(R.id.tv_group)
    TextView mTvGroup;
    @Bind(R.id.tv_birthday)
    EditText mTvBirthday;
    @Bind(R.id.tv_mail)
    EditText mTvMail;
    @Bind(R.id.tv_mobile)
    EditText mTvMobile;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_fellow)
    TextView tvFellow;

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_user_edit);
        ImageLoaderUtils.setIconDrawable(mTitle, MaterialDesignIconic.Icon.gmi_account);
        setTitle("编辑信息", "完成");

        loadData();
    }


    @Override
    protected void loadData() {
        showContentView();
        mUserInfo = (UserInfo) getIntent().getSerializableExtra("user");
        setUserInfo(mUserInfo);
    }

    private void setUserInfo(UserInfo info) {
        ImageLoaderUtils.setUserAvatarUrl(ivAvatar, info.getAvatar());
        mTvName.setText(info.getRealName());
        mTvGentle.setText(info.getGender() == 0 ? "姊妹" : "弟兄");
        mTvGroup.setText(info.getGroupName());
        mTvBirthday.setText(info.getBirthday());
        mTvMobile.setText(info.getMobile());
        mTvMail.setText(info.getEmail());
    }

    public void copy(String content) {
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, content));
    }

    @Override
    public void onClickRightText() {
        UserEditActivity.this.finish();
    }
}
