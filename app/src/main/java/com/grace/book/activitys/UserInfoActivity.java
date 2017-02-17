package com.grace.book.activitys;

import android.os.Bundle;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.base.BaseLoadingActivity;
import com.grace.book.entity.UserInfo;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.UserInfoRequest;
import com.grace.book.utils.DrawableUtils;
import com.grace.book.utils.SharedUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.Bind;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class UserInfoActivity extends BaseLoadingActivity {
    @Bind(R.id.tv_name)
    TextView mTvName;

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
}
