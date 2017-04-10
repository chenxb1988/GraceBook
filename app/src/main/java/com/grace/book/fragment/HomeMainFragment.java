package com.grace.book.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.grace.book.R;
import com.grace.book.activity.MainActivity;
import com.grace.book.fragment.base.BaseLoadingWithTitleFragment;
import com.grace.book.event.LoginEvent;
import com.grace.book.event.UserEditEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.FellowListRequest;
import com.grace.book.http.response.FellowListResponse;
import com.grace.book.http.response.LoginInfo;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.SystemUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.widget.BannerView;
import com.grace.book.widget.spinner.NiceSpinner;
import com.grace.book.widget.theme.ColorBackButton;
import com.grace.book.widget.theme.ColorIconTextView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMainFragment extends BaseLoadingWithTitleFragment {
    @Bind(R.id.avatar)
    ImageView mAvatar;
    @Bind(R.id.section_spinner)
    NiceSpinner mNiceSpinner;

    @Bind(R.id.icon_message)
    ColorIconTextView mIconMessage;
    @Bind(R.id.btn_read_book)
    ColorBackButton mBtnReadBook;
    @Bind(R.id.btn_read_bible)
    ColorBackButton mBtnReadBible;

    List<String> mFellowNameList;
    @Bind(R.id.banner_view)
    BannerView bannerView;

    @Override
    public void initFragment() {
        setLoadingContentView(R.layout.fragment_home_main);

        SystemUtils.setStatusBar(getActivity(), mStatusBar);
        mIcon.setImageDrawable(new IconicsDrawable(getActivity()).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_home).sizeDp(20));
        mTitle.setText(R.string.homepage);

        ThemeUtils.addThemeToView(mBtnReadBook);
        ThemeUtils.addThemeToView(mBtnReadBible);
        mIconMessage.setIIcon(MaterialDesignIconic.Icon.gmi_email, 24);

        showContentView();
        ImageLoaderUtils.setUserAvatarUrl(mAvatar, SharedUtils.getUserAvatar());

        mNiceSpinner.setText(SharedUtils.getFellowName());
        getFellowList();

        initBannerView();

        EventBus.getDefault().register(this);
    }

    private void initBannerView() {
        int[] banners = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4};

        List<View> viewList = new ArrayList<View>();
        for (int i = 0; i < banners.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setImageResource(banners[i]);
            viewList.add(image);
        }
        bannerView.setViewList(viewList);
        bannerView.startLoop(true);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        LoginInfo loginInfo = event.getLoginInfo();
        ImageLoaderUtils.setUserAvatarUrl(mAvatar, loginInfo.getAvatar());
    }

    @Subscribe
    public void onUserEdit(UserEditEvent event) {
        ImageLoaderUtils.setUserAvatarUrl(mAvatar, event.getUserInfo().getAvatar());
    }

    private void getFellowList() {
        FellowListRequest request = new FellowListRequest();
        request.setChurchId("*");
        RequestManager.post(getName(), HttpData.FELLOW_LIST, request, new CallBack<FellowListResponse>() {
            @Override
            public void onSuccess(FellowListResponse result) {
                SharedUtils.saveFellowList(result);
                mFellowNameList = result.getFellowNames();
                if (SharedUtils.isFellowNameNull()) {
                    mFellowNameList.add(0, "选择团契");
                }
                mNiceSpinner.attachDataSource(mFellowNameList);
                mNiceSpinner.setText(SharedUtils.getFellowName());
                mNiceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        SharedUtils.saveFellowName(mFellowNameList.get(i));
                        mFellowNameList.remove("选择团契");
                        mNiceSpinner.setText(mFellowNameList.get(i));
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(getActivity(), message);
            }
        });
    }

    @OnClick({R.id.ll_mall, R.id.ll_record, R.id.ll_contact, R.id.ll_more})
    public void onClick(View view) {
        MainActivity activity = (MainActivity) getActivity();
        switch (view.getId()) {
            case R.id.ll_mall:
                activity.switchFragment(1);
                break;
            case R.id.ll_record:
                activity.switchFragment(2);
                break;
            case R.id.ll_contact:
                activity.switchFragment(3);
                break;
            case R.id.ll_more:
                activity.switchFragment(4);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    protected void loadData() {

    }
}
