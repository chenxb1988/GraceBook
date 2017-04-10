package com.grace.book.activity;

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
import com.grace.book.activity.base.BaseLoadingActivity;
import com.grace.book.adapter.BookCommentAdapter;
import com.grace.book.event.AddBorrowEvent;
import com.grace.book.event.AddStarEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.BaseBookRequest;
import com.grace.book.http.response.BaseResponse;
import com.grace.book.http.response.BookCommentResponse;
import com.grace.book.http.response.BookInfoResponse;
import com.grace.book.utils.DimenUtils;
import com.grace.book.utils.ExtraUtils;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.LoginUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ThemeUtils;
import com.grace.book.utils.ToastUtils;
import com.grace.book.widget.NoScrollListView;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.tv_book_state)
    TextView tvBookState;
    @Bind(R.id.tv_person_read)
    TextView tvPersonRead;
    @Bind(R.id.tv_person_recommend)
    TextView tvPersonRecommend;
    @Bind(R.id.tv_person_star)
    TextView tvPersonStar;
    @Bind(R.id.tv_book_author)
    TextView tvBookAuthor;
    @Bind(R.id.tv_book_type)
    TextView tvBookType;
    @Bind(R.id.tv_book_store_time)
    TextView tvBookStoreTime;
    @Bind(R.id.tv_book_comment)
    TextView tvBookComment;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.lv_comment)
    NoScrollListView lvComment;

    private BookInfoResponse mBookInfo;
    private List<BookCommentResponse.BookComment> mCommentList;

    private PopupWindow mPopupWindow;
    private View mPopView;
    private TextView tvBorrow;
    private LinearLayout llBorrow;
    private TextView tvStar;
    private LinearLayout llStar;
    private ImageView ivBorrow;
    private ImageView ivStar;

    private BookCommentAdapter mAdapter;

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
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookInfoActivity.this.finish();
            }
        });

        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        loadData();
    }

    @Override
    protected void loadData() {
        BaseBookRequest request = new BaseBookRequest();
        request.setBookId(getIntent().getStringExtra(ExtraUtils.BOOK_ID));
        request.setAuthToken(SharedUtils.getUserToken());

        RequestManager.post(getName(), HttpData.BOOK_INFO, request, new CallBack<BookInfoResponse>() {
            @Override
            public void onSuccess(BookInfoResponse result) {
                mToolBarLayout.setVisibility(View.GONE);
                showContentView();
                setBookContent(result);
            }

            @Override
            public void onFailure(String message) {
                mToolBarLayout.setVisibility(View.VISIBLE);
                showErrorView();
                showFailMsg(BookInfoActivity.this, message);
            }
        });

        mCommentList = new ArrayList<>();
        mAdapter = new BookCommentAdapter(this, mCommentList);
        lvComment.setAdapter(mAdapter);
        RequestManager.post(getName(), HttpData.BOOK_COMMENTS, request, new CallBack<BookCommentResponse>() {
            @Override
            public void onSuccess(BookCommentResponse result) {
                if (result.getRecords() != null && result.getRecords().size() > 0) {
                    mCommentList.clear();
                    mCommentList.addAll(result.getRecords());
                    mAdapter.notifyDataSetChanged();

                    tvComment.setVisibility(View.VISIBLE);
                    lvComment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(BookInfoActivity.this, message);
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

    private void setBookContent(BookInfoResponse bookInfo) {
        mBookInfo = bookInfo;

        collapsingToolbar.setTitle(mBookInfo.getBookName());
        ImageLoaderUtils.setImageUrl(ivCover, mBookInfo.getPic(), R.drawable.default_book_info);
        tvBookState.setText(mBookInfo.getBookStateText());
        tvPersonRead.setText(mBookInfo.getReadCount() + "");
        tvPersonStar.setText(mBookInfo.getCollectCount() + "");
        tvPersonRecommend.setText(mBookInfo.getCommendCount() + "");
        tvBookAuthor.setText(mBookInfo.getAuthor());
        tvBookStoreTime.setText(mBookInfo.getInStoreTime());
        tvBookType.setText(mBookInfo.getBookTypeName());
        tvBookComment.setText(mBookInfo.getComment());
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
                    clickBorrowBook();
                    mPopupWindow.dismiss();
                }
            });
            llStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickStarBook();
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
            tvStar.setText("已收藏");
        } else {
            ivStar.setImageResource(R.drawable.ic_star_normal);
            tvStar.setText("收藏");
        }
        if (mBookInfo.canBorrow()) {
            ivBorrow.setImageResource(R.drawable.ic_star_normal);
            tvBorrow.setText("借阅");
        } else if (mBookInfo.hasBorrowed()) {
            ivBorrow.setImageResource(R.drawable.ic_star_select);
            tvBorrow.setText("已预借");
        } else {
            ivBorrow.setImageResource(R.drawable.ic_star_normal);
            tvBorrow.setText("无法借阅");
        }

        backView.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "rotation", 0f, 270f);
        animator.setDuration(300);
        animator.start();
    }

    private void clickBorrowBook() {
        if (mBookInfo.canBorrow() && LoginUtils.isLogin(this)) {
            BaseBookRequest request = new BaseBookRequest();
            request.setBookId(mBookInfo.getBookId());
            request.setAuthToken(SharedUtils.getUserToken());
            RequestManager.post(getName(), HttpData.BOOK_BORROW, request, new CallBack<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse result) {
                    mBookInfo.setHasBorrowed();
                    EventBus.getDefault().post(new AddBorrowEvent(mBookInfo.getBookId()));
                    ToastUtils.showSnack(BookInfoActivity.this, "预借成功");
                }

                @Override
                public void onFailure(String message) {
                    showFailMsg(BookInfoActivity.this, message);
                }
            });
        }
    }

    private void clickStarBook() {
        if (!mBookInfo.isCollected() && LoginUtils.isLogin(this)) {
            BaseBookRequest request = new BaseBookRequest();
            request.setBookId(mBookInfo.getBookId());
            request.setAuthToken(SharedUtils.getUserToken());
            RequestManager.post(getName(), HttpData.BOOK_STAR, request, new CallBack<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse result) {
                    mBookInfo.setHasCollected();
                    mBookInfo.addCollectCount();
                    setBookContent(mBookInfo);
                    EventBus.getDefault().post(new AddStarEvent(mBookInfo.getBookId()));
                    ToastUtils.showSnack(BookInfoActivity.this, "收藏成功");
                }

                @Override
                public void onFailure(String message) {
                    showFailMsg(BookInfoActivity.this, message);
                }
            });
        }
    }
}
