package com.grace.book.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.lguipeng.library.animcheckbox.AnimCheckBox;
import com.grace.book.R;
import com.grace.book.utils.ThemeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxb
 * 17/1/12.
 */

public class LoginDialog extends Dialog {
    @Bind(R.id.login_layout)
    LinearLayout mLlLogin;
    @Bind(R.id.reset_layout)
    LinearLayout mLlReset;
    @Bind(R.id.btn_login)
    Button mLoginBtn;
    @Bind(R.id.btn_getcode)
    Button mGetCodeBtn;
    @Bind(R.id.btn_confirm)
    Button mConfirmBtn;
    @Bind(R.id.cb_remember_pwd)
    AnimCheckBox mCbRemberPwd;

    public LoginDialog(Context context) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        showLogin();
        ThemeUtils.addThemeToView(mLoginBtn);
        ThemeUtils.addThemeToView(mGetCodeBtn);
        ThemeUtils.addThemeToView(mConfirmBtn);
    }

    @OnClick({R.id.tv_remember_pwd, R.id.tv_forget_password, R.id.tv_go_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_remember_pwd:
                mCbRemberPwd.setChecked(!mCbRemberPwd.isChecked(), true);
                break;
            case R.id.tv_forget_password:
                showReset();
                break;
            case R.id.tv_go_login:
                showLogin();
                break;
        }
    }

    private void showLogin() {
        mLlLogin.setVisibility(View.VISIBLE);
        mLlReset.setVisibility(View.GONE);
    }

    private void showReset() {
        mLlLogin.setVisibility(View.GONE);
        mLlReset.setVisibility(View.VISIBLE);
    }

}
