package com.grace.book.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.base.BaseLoadingActivity;
import com.grace.book.event.UserEditEvent;
import com.grace.book.http.response.UserInfo;
import com.grace.book.utils.ImageLoaderUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

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
    TextView mTvBirthday;
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

    @OnClick({R.id.tv_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_birthday:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                mTvBirthday.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "UserEditActivity");
                break;
        }
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

    @Override
    public void onClickRightText() {
        mUserInfo.setBirthday(mTvBirthday.getText().toString());
        mUserInfo.setMobile(mTvMobile.getText().toString());
        mUserInfo.setEmail(mTvMail.getText().toString());
        EventBus.getDefault().post(new UserEditEvent(mUserInfo));
        UserEditActivity.this.finish();
    }
}
