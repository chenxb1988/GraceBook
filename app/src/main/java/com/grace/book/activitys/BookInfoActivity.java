package com.grace.book.activitys;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.base.BaseLoadingActivity;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.BookInfoRequest;
import com.grace.book.http.response.BookInfoResponse;
import com.grace.book.utils.DimenUtils;
import com.grace.book.utils.ExtraUtils;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.utils.ToastUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by chenxb
 * 17/2/16.
 */
public class BookInfoActivity extends BaseLoadingActivity {
    @Bind(R.id.iv_cover)
    ImageView ivCover;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.back_view)
    View backView;

    private BookInfoResponse mBookInfo;

    private PopupWindow mPopupWindow;
    private View mPopView;
    private TextView tvBorrow;
    private LinearLayout llBorrow;
    private TextView tvStar;
    private LinearLayout llStar;
    private ImageView ivBorrow;
    private ImageView ivStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_book_info);
        ImageLoaderUtils.setIconDrawable(mIcon, MaterialDesignIconic.Icon.gmi_arrow_back);
        setTitle("书籍信息");
        showLoadingView();

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(ThemeUtils.getThemePrimaryColor(this));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ImageLoaderUtils.getDrawable(BookInfoActivity.this, MaterialDesignIconic.Icon.gmi_arrow_back));

        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        loadData();
    }

    @Override
    protected void loadData() {
        BookInfoRequest request = new BookInfoRequest();
        request.setBookId(getIntent().getStringExtra(ExtraUtils.BOOK_ID));
        request.setAuthToken(SharedUtils.getUserToken());

        RequestManager.post(getName(), HttpData.BOOK_INFO, request, new CallBack<BookInfoResponse>() {
            @Override
            public void onSuccess(BookInfoResponse result) {
                showContentView();
                mToolBarLayout.setVisibility(View.GONE);
                mBookInfo = result;

                collapsingToolbar.setTitle(result.getBookName());
                ImageLoaderUtils.setImageUrl(ivCover, result.getPic(), R.drawable.default_book_info);
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(message);
                showErrorView();
            }
        });
    }

    @OnClick({R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                showPopupWindow();
                break;
        }
    }

    private void showPopupWindow() {
        if (mPopupWindow == null) {
            mPopView = LayoutInflater.from(this).inflate(R.layout.dialog_bookinfo_action, null);
            tvBorrow = (TextView) mPopView.findViewById(R.id.tv_borrow);
            llBorrow = (LinearLayout) mPopView.findViewById(R.id.ll_borrow);
            ivBorrow = (ImageView) mPopView.findViewById(R.id.iv_borrow);
            tvStar = (TextView) mPopView.findViewById(R.id.tv_star);
            llStar = (LinearLayout) mPopView.findViewById(R.id.ll_star);
            ivStar = (ImageView) mPopView.findViewById(R.id.iv_star);

            llBorrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showSnack(BookInfoActivity.this, "borrow");
                    mPopupWindow.dismiss();
                }
            });
            llStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showSnack(BookInfoActivity.this, "star");
                    mPopupWindow.dismiss();
                }
            });

            mPopupWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 处理KEYCODE_MENU无法dismiss popupWindow的问题
            mPopupWindow.getContentView().setFocusableInTouchMode(true);
            mPopupWindow.getContentView().setFocusable(true);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backView.setVisibility(View.GONE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "rotation", 270f, 0f);
                    animator.setDuration(300);
                    animator.start();
                }
            });
        }
        mPopupWindow.showAsDropDown(fab, -DimenUtils.dp2px(this, 40), -20);
        if (mBookInfo.isCollected()) {
            ivStar.setImageResource(R.drawable.ic_star_select);
            tvStar.setText("取消收藏");
        } else {
            ivStar.setImageResource(R.drawable.ic_star_normal);
            tvStar.setText("收藏");
        }
        if (mBookInfo.canBorrow()) {
            llBorrow.setVisibility(View.VISIBLE);
            ivBorrow.setImageResource(R.drawable.ic_star_normal);
            tvBorrow.setText("借阅");
        } else if (mBookInfo.haveBorrowed()) {
            llBorrow.setVisibility(View.VISIBLE);
            ivBorrow.setImageResource(R.drawable.ic_star_select);
            tvBorrow.setText("取消预借");
        } else {
            llBorrow.setVisibility(View.GONE);
        }

        backView.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "rotation", 0f, 270f);
        animator.setDuration(300);
        animator.start();
    }
}
