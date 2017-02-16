package com.grace.book.activitys;

import android.os.Bundle;

import com.grace.book.R;
import com.grace.book.base.BaseLoadingActivity;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class UserInfoActivity extends BaseLoadingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showContentView();
        setLoadingContentView(R.layout.activity_user_info);
    }


    @Override
    protected void loadData() {

    }
}
