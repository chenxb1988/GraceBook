package com.grace.book.activitys;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.bumptech.glide.Glide;
import com.grace.book.R;
import com.grace.book.base.BaseActivity;
import com.grace.book.entity.LoginInfo;
import com.grace.book.event.LoginEvent;
import com.grace.book.event.SkinChangeEvent;
import com.grace.book.fragments.HomeContactFragment;
import com.grace.book.fragments.HomeMainFragment;
import com.grace.book.fragments.HomeMallFragment;
import com.grace.book.fragments.HomeRecordFragment;
import com.grace.book.fragments.HomeSelfFragment;
import com.grace.book.utils.ActivityUtils;
import com.grace.book.utils.DrawableUtils;
import com.grace.book.utils.LoginUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.widget.ResideLayout;
import com.grace.book.widget.theme.ColorUiUtil;
import com.grace.book.widget.theme.Theme;
import com.mikepenz.foundation_icons_typeface_library.FoundationIcons;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.xiaopan.android.content.res.DimenUtils;
import me.xiaopan.android.preference.PreferencesUtils;

import static com.grace.book.utils.DrawableUtils.setIconDrawable;

public class MainActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    @Bind(R.id.resideLayout)
    ResideLayout mResideLayout;

    @Bind(R.id.home)
    TextView mHome;
    @Bind(R.id.mall)
    TextView mMall;
    @Bind(R.id.record)
    TextView mRecord;
    @Bind(R.id.contact)
    TextView mContact;
    @Bind(R.id.self)
    TextView mSelf;

    @Bind(R.id.container)
    FrameLayout mContainer;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.theme)
    TextView mTheme;
    @Bind(R.id.avatar)
    ImageView mAvatar;
    @Bind(R.id.username)
    TextView mUserName;

    private Fragment currentFragment, mHomeFragment, mMallFragment, mRecordFragment, mContactFragment, mSelfFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        startTranslationNoShowTranslation();

        DrawableUtils.setIconDrawable(mHome, MaterialDesignIconic.Icon.gmi_home);
        DrawableUtils.setIconDrawable(mMall, MaterialDesignIconic.Icon.gmi_city);
        DrawableUtils.setIconDrawable(mRecord, MaterialDesignIconic.Icon.gmi_calendar_note);
        DrawableUtils.setIconDrawable(mContact, MaterialDesignIconic.Icon.gmi_accounts_list);
        DrawableUtils.setIconDrawable(mSelf, MaterialDesignIconic.Icon.gmi_account);

        setIconDrawable(mTheme, MaterialDesignIconic.Icon.gmi_palette);

        Glide.with(MainActivity.this)
                .load(R.drawable.logo)
                .placeholder(new IconicsDrawable(this)
                        .icon(FoundationIcons.Icon.fou_photo)
                        .color(Color.GRAY)
                        .backgroundColor(Color.WHITE)
                        .roundedCornersDp(40)
                        .paddingDp(15)
                        .sizeDp(75))
                .bitmapTransform(new CropCircleTransformation(this))
                .dontAnimate()
                .into(mAvatar);

//        RequestManager.get(getName(), "http://gank.io/api/data/福利/1/1", true, new CallBack<List<GanHuo>>() {
//            @Override
//            public void onSuccess(List<GanHuo> result) {
//                Glide.with(MainActivity.this)
//                        .load(result.get(0).getUrl())
//                        .placeholder(new IconicsDrawable(MainActivity.this)
//                                .icon(FoundationIcons.Icon.fou_photo)
//                                .color(Color.GRAY)
//                                .backgroundColor(Color.WHITE)
//                                .roundedCornersDp(40)
//                                .paddingDp(15)
//                                .sizeDp(75))
//                        .bitmapTransform(new CropCircleTransformation(MainActivity.this))
//                        .dontAnimate()
//                        .into(mAvatar);
//            }
//        });

        if (PreferencesUtils.getBoolean(this, "isFirst", true)) {
            mResideLayout.openPane();
            PreferencesUtils.putBoolean(this, "isFirst", false);
        }
        switchFragment(new HomeMainFragment());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        LoginInfo loginInfo = event.getLoginInfo();
        mUserName.setText(loginInfo.getUserName());
        Glide.with(MainActivity.this)
                .load(loginInfo.getAvatar())
                .placeholder(new IconicsDrawable(this)
                        .icon(FoundationIcons.Icon.fou_photo)
                        .color(Color.GRAY)
                        .backgroundColor(Color.WHITE)
                        .roundedCornersDp(40)
                        .paddingDp(15)
                        .sizeDp(75))
                .bitmapTransform(new CropCircleTransformation(this))
                .dontAnimate()
                .into(mAvatar);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction mTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (currentFragment == null) {
            mTransaction.replace(R.id.container, fragment).commit();
        } else if (!fragment.getClass().getName().equals(currentFragment.getClass().getName())) {
            mTransaction.hide(currentFragment);
            if (!fragment.isAdded()) {
                mTransaction.add(R.id.container, fragment).commitAllowingStateLoss();
            } else {
                mTransaction.show(fragment).commitAllowingStateLoss();
            }
        }
        currentFragment = fragment;
    }

    private Fragment getHomeFragment() {
        if (null == mHomeFragment) {
            mHomeFragment = new HomeMainFragment();
        }
        return mHomeFragment;
    }

    public Fragment getMallFragment() {
        if (null == mMallFragment) {
            mMallFragment = new HomeMallFragment();
        }
        return mMallFragment;
    }

    private Fragment getRecordFragment() {
        if (null == mRecordFragment) {
            mRecordFragment = new HomeRecordFragment();
        }
        return mRecordFragment;
    }

    private Fragment getContactFragment() {
        if (null == mContactFragment) {
            mContactFragment = new HomeContactFragment();
        }
        return mContactFragment;
    }

    private Fragment getSelfFragment() {
        if (null == mSelfFragment) {
            mSelfFragment = new HomeSelfFragment();
        }
        return mSelfFragment;
    }

    @Override
    public void onBackPressed() {
        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        } else if (!(currentFragment instanceof HomeMainFragment)) {
            switchFragment(0);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.avatar,
            R.id.home, R.id.mall, R.id.record, R.id.contact, R.id.self,
            R.id.theme})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                if (LoginUtils.isLogin(this)) {
                    ActivityUtils.launchActivity(MainActivity.this, UserInfoActivity.class);
                }
                break;
            case R.id.home:
                switchFragment(0);
                break;
            case R.id.mall:
                switchFragment(1);
                break;
            case R.id.record:
                switchFragment(2);
                break;
            case R.id.contact:
                switchFragment(3);
                break;
            case R.id.self:
                switchFragment(4);
                break;
//            case R.id.about:
//                new MaterialDialog.Builder(this)
//                        .title(R.string.about)
//                        .icon(new IconicsDrawable(this)
//                                .color(ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
//                                .icon(MaterialDesignIconic.Icon.gmi_account)
//                                .sizeDp(20))
//                        .content(R.string.about_me)
//                        .positiveText(R.string.close)
//                        .show();
//                break;
            case R.id.theme:
                new ColorChooserDialog.Builder(this, R.string.theme)
                        .customColors(R.array.colors, null)
                        .doneButton(R.string.done)
                        .cancelButton(R.string.cancel)
                        .allowUserColorInput(false)
                        .allowUserColorInputAlpha(false)
                        .show();
                break;
            case R.id.icon:
                mResideLayout.openPane();
                break;
        }
    }

    public void openResideLayout() {
        mResideLayout.openPane();
    }

    public void switchFragment(int index) {
        mResideLayout.closePane();
        switch (index) {
            case 0:
                switchFragment(getHomeFragment());
                break;
            case 1:
                switchFragment(getMallFragment());
                break;
            case 2:
                switchFragment(getRecordFragment());
                break;
            case 3:
                switchFragment(getContactFragment());
                break;
            case 4:
                switchFragment(getSelfFragment());
                break;
        }
    }

    public boolean rejectSlide(float x, float y) {
        if (mResideLayout.isOpen()) {
            return false;
        }
        if (currentFragment instanceof HomeMainFragment) {
            int minY = DimenUtils.dp2px(this, 68);
            int maxY = DimenUtils.dp2px(this, 268);
            if (y >= minY && y <= maxY) {
                return true;
            }
        }
        if (currentFragment instanceof HomeRecordFragment) {
            return !((HomeRecordFragment) currentFragment).isOnFirstTab();
        }
        return false;
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (selectedColor == ThemeUtils.getThemePrimaryColor(this))
            return;
        EventBus.getDefault().post(new SkinChangeEvent());

        if (selectedColor == getResources().getColor(R.color.colorBluePrimary)) {
            setTheme(R.style.BlueTheme);
            SharedUtils.setCurrentTheme(Theme.Blue);

        } else if (selectedColor == getResources().getColor(R.color.colorRedPrimary)) {
            setTheme(R.style.RedTheme);
            SharedUtils.setCurrentTheme(Theme.Red);

        } else if (selectedColor == getResources().getColor(R.color.colorBrownPrimary)) {
            setTheme(R.style.BrownTheme);
            SharedUtils.setCurrentTheme(Theme.Brown);

        } else if (selectedColor == getResources().getColor(R.color.colorGreenPrimary)) {
            setTheme(R.style.GreenTheme);
            SharedUtils.setCurrentTheme(Theme.Green);

        } else if (selectedColor == getResources().getColor(R.color.colorPurplePrimary)) {
            setTheme(R.style.PurpleTheme);
            SharedUtils.setCurrentTheme(Theme.Purple);

        } else if (selectedColor == getResources().getColor(R.color.colorTealPrimary)) {
            setTheme(R.style.TealTheme);
            SharedUtils.setCurrentTheme(Theme.Teal);

        } else if (selectedColor == getResources().getColor(R.color.colorPinkPrimary)) {
            setTheme(R.style.PinkTheme);
            SharedUtils.setCurrentTheme(Theme.Pink);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepPurplePrimary)) {
            setTheme(R.style.DeepPurpleTheme);
            SharedUtils.setCurrentTheme(Theme.DeepPurple);

        } else if (selectedColor == getResources().getColor(R.color.colorOrangePrimary)) {
            setTheme(R.style.OrangeTheme);
            SharedUtils.setCurrentTheme(Theme.Orange);

        } else if (selectedColor == getResources().getColor(R.color.colorIndigoPrimary)) {
            setTheme(R.style.IndigoTheme);
            SharedUtils.setCurrentTheme(Theme.Indigo);

        } else if (selectedColor == getResources().getColor(R.color.colorLightGreenPrimary)) {
            setTheme(R.style.LightGreenTheme);
            SharedUtils.setCurrentTheme(Theme.LightGreen);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepOrangePrimary)) {
            setTheme(R.style.DeepOrangeTheme);
            SharedUtils.setCurrentTheme(Theme.DeepOrange);

        } else if (selectedColor == getResources().getColor(R.color.colorLimePrimary)) {
            setTheme(R.style.LimeTheme);
            SharedUtils.setCurrentTheme(Theme.Lime);

        } else if (selectedColor == getResources().getColor(R.color.colorBlueGreyPrimary)) {
            setTheme(R.style.BlueGreyTheme);
            SharedUtils.setCurrentTheme(Theme.BlueGrey);

        } else if (selectedColor == getResources().getColor(R.color.colorCyanPrimary)) {
            setTheme(R.style.CyanTheme);
            SharedUtils.setCurrentTheme(Theme.Cyan);

        }
        final View rootView = getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getApplicationContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ColorUiUtil.changeTheme(rootView, getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }
}
