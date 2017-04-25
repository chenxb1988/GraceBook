package com.grace.book.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.grace.book.R;
import com.grace.book.activity.base.BaseLoadingActivity;
import com.grace.book.event.UserEditEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.FellowListRequest;
import com.grace.book.http.request.GroupListRequest;
import com.grace.book.http.request.UploadImgRequest;
import com.grace.book.http.response.FellowListResponse;
import com.grace.book.http.response.GroupListResponse;
import com.grace.book.http.response.UploadImgResponse;
import com.grace.book.http.response.UserInfo;
import com.grace.book.utils.CropUtils;
import com.grace.book.utils.ExtraUtils;
import com.grace.book.utils.FileUtils;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ToastUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.xiaopan.java.lang.StringUtils;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class UserEditActivity extends BaseLoadingActivity {
    @Bind(R.id.et_name)
    TextView mTvName;
    @Bind(R.id.tv_gentle)
    TextView mTvGentle;
    @Bind(R.id.et_group)
    TextView mTvGroup;
    @Bind(R.id.tv_birthday)
    TextView mTvBirthday;
    @Bind(R.id.tv_mail)
    EditText mTvMail;
    @Bind(R.id.tv_mobile)
    EditText mTvMobile;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;

    private UserInfo mUserInfo;
    private String mAvatarPath;
    private String mFellowName = "", mGroupName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_user_edit);
        ImageLoaderUtils.setIconDrawable(mIcon, MaterialDesignIconic.Icon.gmi_account);
        setTitle("编辑信息", "完成");

        loadData();
    }


    @Override
    protected void loadData() {
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(ExtraUtils.USER_INFO);
        setUserInfo(mUserInfo);
    }

    @OnClick({R.id.tv_birthday, R.id.ll_avatar, R.id.et_group})
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
            case R.id.ll_avatar:
                CropUtils.pickImage(this);
                break;
            case R.id.et_group:
//                getFellowList();
                break;
        }
    }

    private void getFellowList() {
        if (SharedUtils.getFellowList().getFellowNames().size() == 0) {
            FellowListRequest request = new FellowListRequest();
            request.setChurchId("*");
            RequestManager.post(getName(), HttpData.FELLOW_LIST, request, new CallBack<FellowListResponse>() {
                @Override
                public void onSuccess(FellowListResponse result) {
                    SharedUtils.saveFellowList(result);
                    if (result.getFellowNames().size() > 0) {
                        showFellowDialog(result.getFellowNames());
                    }
                }

                @Override
                public void onFailure(String message) {
                    showFailMsg(message);
                }
            });
        } else {
            showFellowDialog(SharedUtils.getFellowList().getFellowNames());
        }
    }

    private void showFellowDialog(List<String> fellowNames) {
        new MaterialDialog.Builder(this)
                .title("选择团契")
                .items(fellowNames)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        mFellowName = text.toString();
                        mGroupName = "";
                        mTvGroup.setText(mFellowName);
                        String fellowId = SharedUtils.getFellowList().getFellowId(mFellowName);
                        if (!StringUtils.isEmpty(fellowId)) {
                            getGroupList(fellowId);
                        }
                    }
                })
                .show();
    }

    private void getGroupList(final String fellowId) {
        if (SharedUtils.getGroupList(fellowId).getGroupNames().size() == 0) {
            GroupListRequest request = new GroupListRequest();
            request.setChurchId(fellowId);
            RequestManager.post(getName(), HttpData.GROUP_LIST, request, new CallBack<GroupListResponse>() {
                @Override
                public void onSuccess(GroupListResponse result) {
                    SharedUtils.saveGroupList(fellowId, result);
                    if (result.getGroupNames().size() > 0) {
                        showGroupDialog(result.getGroupNames());
                    }
                }

                @Override
                public void onFailure(String message) {
                    showFailMsg(UserEditActivity.this, message);
                }
            });
        } else {
            showGroupDialog(SharedUtils.getGroupList(fellowId).getGroupNames());
        }
    }

    private void showGroupDialog(List<String> groupNames) {
        new MaterialDialog.Builder(this)
                .title("选择小组")
                .items(groupNames)
                .cancelable(false)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        mGroupName = text.toString();
                        mTvGroup.setText(mFellowName + " " + mGroupName);
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == CropUtils.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == CropUtils.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(FileUtils.getRootDir(this), "crop_" + System.currentTimeMillis()));
        Intent intent = new Intent(this, CropPhotoActivity.class);
        intent.setData(source);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        startActivityForResult(intent, CropUtils.REQUEST_CROP);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri output = CropUtils.getOutput(result);
            mAvatarPath = output.getPath();
            ImageLoaderUtils.setLocalCircleImage(ivAvatar, mAvatarPath);

            UploadImgRequest request = new UploadImgRequest();
            request.setFile(mAvatarPath);
            RequestManager.post(getName(), HttpData.UPLOAD_IMG, request, new CallBack<UploadImgResponse>() {
                @Override
                public void onSuccess(UploadImgResponse result) {
                    if (result.getFileList() != null && result.getFileList().size() > 0) {
                        mAvatarPath = result.getFileList().get(0).getThumbnailAddress();
                        ImageLoaderUtils.setUserAvatarUrl(ivAvatar, mAvatarPath);
                        ToastUtils.showSuccessToasty(UserEditActivity.this, "上传图片成功");
                    }
                }

                @Override
                public void onFailure(String message) {
                    showFailMsg(UserEditActivity.this, message);
                }
            });
        } else if (resultCode == CropUtils.RESULT_ERROR) {
            ToastUtils.showErrorToasty(this, CropUtils.getError(result).getMessage());
        }
    }

    private void setUserInfo(UserInfo info) {
        ImageLoaderUtils.setUserAvatarUrl(ivAvatar, info.getAvatar());
        mTvName.setText(info.getRealName());
        mTvGentle.setText(info.getGender() == 0 ? "姊妹" : "弟兄");
        mFellowName = info.getChurchName() == null ? "" : info.getChurchName();
        mGroupName = info.getGroupName() == null ? "" : info.getGroupName();
        mTvGroup.setText(mFellowName + " " + mGroupName);
        mTvBirthday.setText(info.getBirthday());
        mTvMobile.setText(info.getMobile());
        mTvMail.setText(info.getEmail());
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("您还没保存，需要保存吗？")
                .positiveText("保存")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        saveUserInfo();
                    }
                })
                .negativeText("退出")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UserEditActivity.this.finish();
                    }
                })
                .show();
    }

    @Override
    public void onClickRightText() {
        saveUserInfo();
    }

    private void saveUserInfo() {
        mUserInfo.setAvatar(mAvatarPath);
        mUserInfo.setBirthday(mTvBirthday.getText().toString());
        mUserInfo.setMobile(mTvMobile.getText().toString());
        mUserInfo.setEmail(mTvMail.getText().toString());
        EventBus.getDefault().post(new UserEditEvent(mUserInfo));
        UserEditActivity.this.finish();
    }
}
