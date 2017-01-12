package com.grace.book.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import com.grace.book.R;
import com.grace.book.utils.ThemeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxb
 * 17/1/12.
 */

public class LoginDialog extends Dialog {
    @Bind(R.id.btn_login)
    Button mLoginBtn;

    public LoginDialog(Context context) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        ThemeUtils.addThemeToView(mLoginBtn);
    }
}
