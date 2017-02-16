package com.grace.book.activitys;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.base.BaseActivity;
import com.grace.book.entity.GanHuo;
import com.grace.book.utils.ActivityUtils;
import com.grace.book.utils.SystemUtils;

import me.xiaopan.android.widget.ToastUtils;

/**
 * Web页面
 */
public class WebActivity extends BaseActivity {

    private WebView mWebView;
    private String mUrl;
    private ProgressBar mProgressBar;

    View mStatusBar;
    ImageView mMenu;
    ImageView mIcon;
    TextView mTitle;

    float NewX1, NewY1, NewX2, NewY2, OldX1, OldY1, OldX2, OldY2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initViews();
        init();
    }

    protected void initViews() {
        mWebView = (WebView) findViewById(R.id.wv_web);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mStatusBar = findViewById(R.id.status_bar);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setSingleLine();
        mIcon = (ImageView) findViewById(R.id.icon);
        mMenu = (ImageView) findViewById(R.id.iv_right);
        mMenu.setVisibility(View.VISIBLE);
    }

    protected void init() {
        SystemUtils.setStatusBar(this, mStatusBar);

        GanHuo ganhuo = (GanHuo) getIntent().getSerializableExtra(ActivityUtils.GANHUO_EXTRA);
        ActivityUtils.setIconByType(ganhuo.getType(), mIcon);
        mUrl = ganhuo.getUrl();
        String title = ganhuo.getDesc();
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.setInitialScale(5);

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new DefaultWebViewClient());
        mWebView.loadUrl(mUrl);

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_POINTER_2_DOWN:
                        if (event.getPointerCount() == 2) {
                            for (int i = 0; i < event.getPointerCount(); i++) {
                                if (i == 0) {
                                    OldX1 = event.getX(i);
                                    OldY1 = event.getY(i);
                                } else if (i == 1) {
                                    OldX2 = event.getX(i);
                                    OldY2 = event.getY(i);
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getPointerCount() == 2) {
                            for (int i = 0; i < event.getPointerCount(); i++) {
                                if (i == 0) {
                                    NewX1 = event.getX(i);
                                    NewY1 = event.getY(i);
                                } else if (i == 1) {
                                    NewX2 = event.getX(i);
                                    NewY2 = event.getY(i);
                                }
                            }
                            float disOld = (float) Math.sqrt((Math.pow(OldX2 - OldX1, 2) + Math.pow(
                                    OldY2 - OldY1, 2)));
                            float disNew = (float) Math.sqrt((Math.pow(NewX2 - NewX1, 2) + Math.pow(
                                    NewY2 - NewY1, 2)));
                            Log.d("onTouch", "disOld=" + disOld + "|disNew=" + disNew);
                            if (disOld - disNew >= 25) {
                                // 缩小
                                mWebView.zoomOut();

                            } else if (disNew - disOld >= 25) {
                                // 放大
                                mWebView.zoomIn();
                            }
                            OldX1 = NewX1;
                            OldX2 = NewX2;
                            OldY1 = NewY1;
                            OldY2 = NewY2;
                        }
                }

                return false;
            }
        });

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(WebActivity.this, mMenu);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_brower:
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(mUrl);
                                intent.setData(content_url);
                                startActivity(intent);
                                break;
                            case R.id.menu_copy:
                                ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData myClip = ClipData.newPlainText("text", mUrl);
                                myClipboard.setPrimaryClip(myClip);
                                ToastUtils.toastS(WebActivity.this, "已复制链接");
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.finish();
            }
        });
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setProgress(0);
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() == View.GONE)
                    mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.incrementProgressBy(newProgress / 4 * 3);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            mTitle.setText(title);
            super.onReceivedTitle(view, title);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class DefaultWebViewClient extends WebViewClient {

        public DefaultWebViewClient() {
            super();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

}
