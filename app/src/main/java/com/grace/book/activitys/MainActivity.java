package com.grace.book.activitys;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.bumptech.glide.Glide;
import com.grace.book.R;
import com.grace.book.base.BaseActivity;
import com.grace.book.beans.GanHuo;
import com.grace.book.event.SkinChangeEvent;
import com.grace.book.fragments.ContactFragment;
import com.grace.book.fragments.HomeFragment;
import com.grace.book.fragments.MallFragment;
import com.grace.book.fragments.RecordFragment;
import com.grace.book.fragments.SelfFragment;
import com.grace.book.http.CallBack;
import com.grace.book.http.RequestManager;
import com.grace.book.theme.ColorUiUtil;
import com.grace.book.theme.Theme;
import com.grace.book.utils.LoginUtils;
import com.grace.book.utils.PreUtils;
import com.grace.book.utils.SystemUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.widget.ResideLayout;
import com.mikepenz.foundation_icons_typeface_library.FoundationIcons;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.xiaopan.android.content.res.DimenUtils;
import me.xiaopan.android.preference.PreferencesUtils;

public class MainActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    @Bind(R.id.menu)
    RelativeLayout mMenu;
    @Bind(R.id.resideLayout)
    ResideLayout mResideLayout;
    @Bind(R.id.status_bar)
    View mStatusBar;

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
    @Bind(R.id.icon)
    ImageView mIcon;
    @Bind(R.id.title)
    TextView mTitle;

    private Fragment currentFragment, mHomeFragment, mMallFragment, mRecordFragment, mContactFragment, mSelfFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        startTranslationNoShowTranslation();

        setIconDrawable(mHome, MaterialDesignIconic.Icon.gmi_home);
        setIconDrawable(mMall, MaterialDesignIconic.Icon.gmi_city);
        setIconDrawable(mRecord, MaterialDesignIconic.Icon.gmi_airline_seat_flat);
        setIconDrawable(mContact, MaterialDesignIconic.Icon.gmi_calendar_note);
        setIconDrawable(mSelf, MaterialDesignIconic.Icon.gmi_account);

        setIconDrawable(mTheme, MaterialDesignIconic.Icon.gmi_palette);

        Glide.with(MainActivity.this)
                .load(R.mipmap.avatar)
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

        RequestManager.get(getName(), "http://gank.io/api/data/福利/1/1", true, new CallBack<List<GanHuo>>() {
            @Override
            public void onSuccess(List<GanHuo> result) {
                Glide.with(MainActivity.this)
                        .load(result.get(0).getUrl())
                        .placeholder(new IconicsDrawable(MainActivity.this)
                                .icon(FoundationIcons.Icon.fou_photo)
                                .color(Color.GRAY)
                                .backgroundColor(Color.WHITE)
                                .roundedCornersDp(40)
                                .paddingDp(15)
                                .sizeDp(75))
                        .bitmapTransform(new CropCircleTransformation(MainActivity.this))
                        .dontAnimate()
                        .into(mAvatar);
            }
        });

        if (PreferencesUtils.getBoolean(this, "isFirst", true)) {
            mResideLayout.openPane();
            PreferencesUtils.putBoolean(this, "isFirst", false);
        }
        mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_home).sizeDp(20));
        mTitle.setText(R.string.homepage);
        switchFragment(new HomeFragment());

    }

    private void setIconDrawable(TextView view, IIcon icon) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(this)
                        .icon(icon)
                        .color(Color.WHITE)
                        .sizeDp(16),
                null, null, null);
        view.setCompoundDrawablePadding(DimenUtils.dp2px(this, 10));
    }

    private void switchFragment(Fragment fragment) {
        if (currentFragment == null || !fragment.getClass().getName().equals(currentFragment.getClass().getName())) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.container, fragment)
                    .commit();
            currentFragment = fragment;
        }
    }

    private Fragment getHomeFragment() {
        if (null == mHomeFragment) {
            mHomeFragment = new HomeFragment();
        }
        return mHomeFragment;
    }

    public Fragment getMallFragment() {
        if (null == mMallFragment) {
            mMallFragment = new MallFragment();
        }
        return mMallFragment;
    }

    private Fragment getRecordFragment() {
        if (null == mRecordFragment) {
            mRecordFragment = new RecordFragment();
        }
        return mRecordFragment;
    }

    private Fragment getContactFragment() {
        if (null == mContactFragment) {
            mContactFragment = new ContactFragment();
        }
        return mContactFragment;
    }

    private Fragment getSelfFragment() {
        if (null == mSelfFragment) {
            mSelfFragment = new SelfFragment();
        }
        return mSelfFragment;
    }

    @Override
    public void onBackPressed() {
        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        } else if (!(currentFragment instanceof HomeFragment)) {
            switchFragment(0);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.avatar,
            R.id.home, R.id.mall, R.id.record, R.id.contact, R.id.self,
            R.id.theme, R.id.icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                LoginUtils.showLoginDialog(this);
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

    public void switchFragment(int index) {
        mResideLayout.closePane();
        switch (index) {
            case 0:
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_view_comfy).sizeDp(20));
                mTitle.setText(R.string.homepage);
                switchFragment(getHomeFragment());
                break;
            case 1:
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_city).sizeDp(20));
                mTitle.setText(R.string.mall);
                switchFragment(getMallFragment());
                break;
            case 2:
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_mood).sizeDp(20));
                mTitle.setText(R.string.record);
                switchFragment(getRecordFragment());
                break;
            case 3:
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_android).sizeDp(20));
                mTitle.setText(R.string.contact);
                switchFragment(getContactFragment());
                break;
            case 4:
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_apple).sizeDp(20));
                mTitle.setText(R.string.self);
                switchFragment(getSelfFragment());
                break;
        }
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (selectedColor == ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
            return;
        EventBus.getDefault().post(new SkinChangeEvent());

        if (selectedColor == getResources().getColor(R.color.colorBluePrimary)) {
            setTheme(R.style.BlueTheme);
            PreUtils.setCurrentTheme(this, Theme.Blue);

        } else if (selectedColor == getResources().getColor(R.color.colorRedPrimary)) {
            setTheme(R.style.RedTheme);
            PreUtils.setCurrentTheme(this, Theme.Red);

        } else if (selectedColor == getResources().getColor(R.color.colorBrownPrimary)) {
            setTheme(R.style.BrownTheme);
            PreUtils.setCurrentTheme(this, Theme.Brown);

        } else if (selectedColor == getResources().getColor(R.color.colorGreenPrimary)) {
            setTheme(R.style.GreenTheme);
            PreUtils.setCurrentTheme(this, Theme.Green);

        } else if (selectedColor == getResources().getColor(R.color.colorPurplePrimary)) {
            setTheme(R.style.PurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.Purple);

        } else if (selectedColor == getResources().getColor(R.color.colorTealPrimary)) {
            setTheme(R.style.TealTheme);
            PreUtils.setCurrentTheme(this, Theme.Teal);

        } else if (selectedColor == getResources().getColor(R.color.colorPinkPrimary)) {
            setTheme(R.style.PinkTheme);
            PreUtils.setCurrentTheme(this, Theme.Pink);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepPurplePrimary)) {
            setTheme(R.style.DeepPurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepPurple);

        } else if (selectedColor == getResources().getColor(R.color.colorOrangePrimary)) {
            setTheme(R.style.OrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.Orange);

        } else if (selectedColor == getResources().getColor(R.color.colorIndigoPrimary)) {
            setTheme(R.style.IndigoTheme);
            PreUtils.setCurrentTheme(this, Theme.Indigo);

        } else if (selectedColor == getResources().getColor(R.color.colorLightGreenPrimary)) {
            setTheme(R.style.LightGreenTheme);
            PreUtils.setCurrentTheme(this, Theme.LightGreen);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepOrangePrimary)) {
            setTheme(R.style.DeepOrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepOrange);

        } else if (selectedColor == getResources().getColor(R.color.colorLimePrimary)) {
            setTheme(R.style.LimeTheme);
            PreUtils.setCurrentTheme(this, Theme.Lime);

        } else if (selectedColor == getResources().getColor(R.color.colorBlueGreyPrimary)) {
            setTheme(R.style.BlueGreyTheme);
            PreUtils.setCurrentTheme(this, Theme.BlueGrey);

        } else if (selectedColor == getResources().getColor(R.color.colorCyanPrimary)) {
            setTheme(R.style.CyanTheme);
            PreUtils.setCurrentTheme(this, Theme.Cyan);

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
