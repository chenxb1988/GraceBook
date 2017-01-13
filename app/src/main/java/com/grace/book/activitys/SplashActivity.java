package com.grace.book.activitys;

import android.os.Bundle;
import android.widget.ImageView;

import com.grace.book.R;
import com.grace.book.base.BaseActivity;
import com.grace.book.utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxb
 * 17/1/13.
 */

public class SplashActivity extends BaseActivity {
    @Bind(R.id.iv_splash)
    ImageView mIvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mIvSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.launchActivity(SplashActivity.this, MainActivity.class, mIvSplash);
                finish();
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {

    }
}
