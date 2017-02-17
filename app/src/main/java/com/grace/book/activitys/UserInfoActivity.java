package com.grace.book.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.base.BaseLoadingActivity;
import com.grace.book.event.LogoutEvent;
import com.grace.book.http.response.BaseResponse;
import com.grace.book.http.response.UserInfo;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.BaseTokenRequest;
import com.grace.book.http.request.UserInfoRequest;
import com.grace.book.utils.DialogUtils;
import com.grace.book.utils.DrawableUtils;
import com.grace.book.utils.SharedUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class UserInfoActivity extends BaseLoadingActivity {
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_logout)
    TextView mTvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_user_info);
        DrawableUtils.setIconDrawable(mTitle, MaterialDesignIconic.Icon.gmi_account);
        setTitle("个人信息");

        loadData();
    }


    @Override
    protected void loadData() {
        showLoadingView();
        UserInfoRequest request = new UserInfoRequest();
        request.setUserId(SharedUtils.getUserId());
        request.setAuthToken(SharedUtils.getUserToken());

        RequestManager.post(getName(), HttpData.USER_INFO, request, new CallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                showContentView();
                mTvName.setText(result.getRealName());
            }

            @Override
            public void onFailure(String message) {
                showErrorView();
            }
        });
    }

    @OnClick({R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                BaseTokenRequest request = new BaseTokenRequest(SharedUtils.getUserToken());
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
                        showFailMsg(message);
                    }
                });
                break;
        }
    }

}
