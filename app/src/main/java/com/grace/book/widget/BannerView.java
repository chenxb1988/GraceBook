package com.grace.book.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.grace.book.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * banner轮播
 */
public class BannerView extends RelativeLayout {
    @Bind(R.id.promotion_pager)
    ViewPager mViewPager;
    @Bind(R.id.promotion_bottom_dots)
    LinearLayout mViewDots;

    ArrayList<View> mViews;
    ArrayList<ImageView> mPoints;
    Context mContext;

    final int TIME_PERIOD = 2000;
    Timer timer;
    TimerTask mTimerTask;
    int currentIndex;

    int[] banners = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4};

    public BannerView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_banner_viewpager, this);
        ButterKnife.bind(this, itemView);

        mViews = new ArrayList<>();
        mPoints = new ArrayList<>();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 20, 20, 20);
        for (int i = banners.length - 1; i < banners.length * 2 + 1; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(params);
            imageView.setImageResource(banners[i % banners.length]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mViews.add(imageView);
        }
        for (int i = 0; i < banners.length; i++) {
            ImageView dot = new ImageView(mContext);
            dot.setImageResource(mPoints.size() == 0 ? R.drawable.ic_point_select : R.drawable.ic_point_normal);
            mPoints.add(dot);
            mViewDots.addView(dot, params);
        }
    }

    /**
     * 填充数据
     */
    public void initData() {
        mViewPager.setAdapter(new PromotionAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    position = banners.length;
                    mViewPager.setCurrentItem(position, false);
                } else if (position == banners.length + 1) {
                    position = 1;
                    mViewPager.setCurrentItem(position, false);
                }
                currentIndex = position;
                setCurrentDot(currentIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        stopTimer();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        startTimer();
                        break;
                }
                return false;
            }
        });
        mViewPager.setCurrentItem(banners.length);

        startTimer();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    /**
     * 设置轮播位置的显示图
     *
     * @param dot
     * @param enable
     */
    private void setDotEnable(ImageView dot, boolean enable) {
        if (enable) {
            dot.setImageResource(R.drawable.ic_point_select);
        } else {
            dot.setImageResource(R.drawable.ic_point_normal);
        }
    }

    private void setCurrentDot(int position) {
        position = position % banners.length;
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == position) {
                setDotEnable(mPoints.get(i), true);
            } else {
                setDotEnable(mPoints.get(i), false);
            }
        }
    }

    /**
     * 开始轮播
     */
    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        initTimerTask();
        timer.schedule(mTimerTask, TIME_PERIOD, TIME_PERIOD);
    }

    /**
     * 结束轮播
     */
    private void stopTimer() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    private void initTimerTask() {
        stopTimer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mViewPager.setCurrentItem(currentIndex + 1);
                    break;
            }
        }
    };

    class PromotionAdapter extends PagerAdapter {
        private SparseArray<View> mToDestroy = new SparseArray<View>();

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            View toDestroy = mToDestroy.get(position);
//            if (toDestroy != null) {
//                mToDestroy.remove(position);
//                return toDestroy;
//            }
//            position = position % banners.length;
            if (mViews.get(position).getParent() == null) {
                container.addView(mViews.get(position), 0);
            }
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            if (position == 1 || position == banners.length) {
//                mToDestroy.put(position, (View) object);
//            } else {
//                position = position % banners.length;
            container.removeView(mViews.get(position));
//            }
        }

        @Override
        public void notifyDataSetChanged() {
            mToDestroy.clear();
            super.notifyDataSetChanged();
        }
    }
}
