package com.grace.book.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.grace.book.R;
import com.grace.book.utils.Intents;


/**
 * 授权-登录
 *
 * @author Shaojie
 * @Date 2014-1-22 上午9:28:24
 */
public class LoginActivity extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        return new Intents.Builder().setClass(context, LoginActivity.class).toIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        final Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.7f;
        window.setAttributes(params);
    }
}
