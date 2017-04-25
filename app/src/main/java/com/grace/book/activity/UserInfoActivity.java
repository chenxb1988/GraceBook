package com.grace.book.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.activity.base.BaseLoadingActivity;
import com.grace.book.event.LogoutEvent;
import com.grace.book.event.UserEditEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.BaseRequest;
import com.grace.book.http.request.UserInfoRequest;
import com.grace.book.http.response.BaseResponse;
import com.grace.book.http.response.UserInfo;
import com.grace.book.utils.DialogUtils;
import com.grace.book.utils.ExtraUtils;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.utils.ToastUtils;
import com.grace.book.widget.theme.ColorBackButton;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;
import me.xiaopan.java.lang.StringUtils;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class UserInfoActivity extends BaseLoadingActivity {
    public static final String KEY_USERID = "key_userid";
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_gentle)
    TextView mTvGentle;
    @Bind(R.id.tv_group)
    TextView mTvGroup;
    @Bind(R.id.tv_birthday)
    TextView mTvBirthday;
    @Bind(R.id.tv_mail)
    TextView mTvMail;
    @Bind(R.id.tv_mobile)
    TextView mTvMobile;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_fellow)
    TextView mTvFellow;
    @Bind(R.id.btn_logout)
    ColorBackButton mBtnLogout;

    private UserInfo mUserInfo;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_user_info);
        ImageLoaderUtils.setIconDrawable(mIcon, MaterialDesignIconic.Icon.gmi_account);
        ThemeUtils.addThemeToView(mBtnLogout);
        EventBus.getDefault().register(this);
        loadData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void loadData() {
        mUserId = getIntent().getStringExtra(KEY_USERID);
        if (StringUtils.isEmpty(mUserId)) {
            mUserId = SharedUtils.getUserId();
        }
        if (mUserId.equals(SharedUtils.getUserId())) {
            setTitle("个人信息", "编辑");
            mBtnLogout.setVisibility(View.VISIBLE);
        } else {
            setTitle("个人信息");
            ThemeUtils.addThemeToView(mBtnLogout);
            mBtnLogout.setVisibility(View.GONE);
        }

        showLoadingView();
        UserInfoRequest request = new UserInfoRequest();
        request.setUserId(mUserId);

        RequestManager.post(getName(), HttpData.USER_INFO, request, new CallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                showContentView();
                setUserInfo(result);
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(UserInfoActivity.this, message);
                showErrorView();
            }
        });
    }

    @OnClick({R.id.btn_logout, R.id.tv_mobile, R.id.tv_mail, R.id.tv_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                BaseRequest request = new BaseRequest();
                DialogUtils.showProgress(UserInfoActivity.this, "正在退出登录...");
                RequestManager.post(getName(), HttpData.LOGOUT, request, new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
                        DialogUtils.dismissProgress();
                        SharedUtils.clearUserData();
                        EventBus.getDefault().post(new LogoutEvent());
                        UserInfoActivity.this.finish();
                    }

                    @Override
                    public void onFailure(String message) {
                        DialogUtils.dismissProgress();
                        showFailMsg(UserInfoActivity.this, message);
                    }
                });
                break;
            case R.id.tv_mobile:
                String mobile = mTvMobile.getText().toString();
                if (!StringUtils.isEmpty(mobile)) {
                    copy(mobile);
                    ToastUtils.showSuccessToasty(UserInfoActivity.this, "手机号已复制到粘贴板");
                    if (!mUserId.equals(SharedUtils.getUserId())) {
                        Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intentPhone);
                    }
                }
                break;
            case R.id.tv_mail:
                String mail = mTvMail.getText().toString();
                if (!StringUtils.isEmpty(mail)) {
                    copy(mail);
                    ToastUtils.showSuccessToasty(UserInfoActivity.this, "邮箱已复制到粘贴板");
                }
                break;
            case R.id.tv_birthday:
                String birthday = mTvBirthday.getText().toString();
                if (!StringUtils.isEmpty(birthday)) {
                    copy(birthday);
                    ToastUtils.showSuccessToasty(UserInfoActivity.this, "生日已复制到粘贴板");
                }
                break;
        }
    }

    @Subscribe
    public void onUserEdit(UserEditEvent event) {
        setUserInfo(event.getUserInfo());
    }

    private void setUserInfo(UserInfo info) {
        mUserInfo = info;
        ImageLoaderUtils.setUserAvatarUrl(ivAvatar, info.getAvatar());
        mTvName.setText(info.getRealName());
        mTvGentle.setText(info.getGender() == 0 ? "姊妹" : "弟兄");
        mTvFellow.setText(info.getChurchName());
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
        if (mUserInfo == null) {
            return;
        }
        Intent intent = new Intent(UserInfoActivity.this, UserEditActivity.class);
        intent.putExtra(ExtraUtils.USER_INFO, mUserInfo);
        UserInfoActivity.this.startActivity(intent);
    }
}
