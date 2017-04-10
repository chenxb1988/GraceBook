package com.grace.book.dialog;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.grace.book.R;
import com.grace.book.activity.base.BaseActivity;
import com.grace.book.event.LoginEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.LoginRequest;
import com.grace.book.http.response.LoginInfo;
import com.grace.book.utils.ConstData;
import com.grace.book.utils.DialogUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.widget.AnimCheckBox;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.java.lang.StringUtils;

/**
 * Created by chenxb
 * 17/1/12.
 */

public class LoginDialog extends Dialog {
    @Bind(R.id.login_layout)
    LinearLayout mLlLogin;
    @Bind(R.id.et_username)
    EditText mEtUsername;
    @Bind(R.id.et_password)
    EditText mEtPassword;

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

    private BaseActivity mActivity;

    public LoginDialog(BaseActivity activity) {
        super(activity, R.style.MyDialog);
        mActivity = activity;
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        showLogin();
        ThemeUtils.addThemeToView(mLoginBtn);
        ThemeUtils.addThemeToView(mGetCodeBtn);
        ThemeUtils.addThemeToView(mConfirmBtn);

        if (StringUtils.isEmpty(SharedUtils.getString(ConstData.MOBILE))) {
            mCbRemberPwd.setChecked(false);
            mEtUsername.setText("");
            mEtPassword.setText("");
        } else {
            mCbRemberPwd.setChecked(true, false);
            mEtUsername.setText(SharedUtils.getString(ConstData.MOBILE));
            mEtPassword.setText(SharedUtils.getString(ConstData.PASSWORD));
        }

    }

    @OnClick({R.id.tv_remember_pwd, R.id.tv_forget_password, R.id.tv_go_login, R.id.btn_login})
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
            case R.id.btn_login:
                String mobile = mEtUsername.getText().toString();
                if (StringUtils.isEmpty(mobile)) {
                    mEtUsername.setError("请输入手机号!");
                    break;
                }
                String password = mEtPassword.getText().toString();
                if (StringUtils.isEmpty(password)) {
                    mEtPassword.setError("请输入密码!");
                    break;
                }
                DialogUtils.showProgress(mActivity, "正在登陆...");
                LoginRequest request = new LoginRequest();
                request.setMobile("18868808315");//TODO cxb
                request.setPassword("r2N7aKOsCAFAO/v0Ge3MKQ==");

                RequestManager.post(mActivity.getName(), HttpData.LOGIN, request, new CallBack<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo result) {
                        if (mCbRemberPwd.isChecked()) {
                            SharedUtils.putString(ConstData.MOBILE, mEtUsername.getText().toString());
                            SharedUtils.putString(ConstData.PASSWORD, mEtPassword.getText().toString());
                        } else {
                            SharedUtils.putString(ConstData.MOBILE, "");
                            SharedUtils.putString(ConstData.PASSWORD, "");
                        }

                        DialogUtils.dismissProgress();
                        SharedUtils.saveUserData(result);
                        EventBus.getDefault().post(new LoginEvent(result));
                        dismiss();
                    }

                    @Override
                    public void onFailure(String message) {
                        showFailMsg(mActivity, message);
                        DialogUtils.dismissProgress();
                        dismiss();
                    }
                });
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
