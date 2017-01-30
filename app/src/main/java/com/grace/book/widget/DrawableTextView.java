package com.grace.book.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grace.book.R;


/**
 * Created by Emiya on 2016/7/25.
 */
public class DrawableTextView extends RelativeLayout{

    private TextView mTvText;

    private ImageView mIvDot;

    private Context mContext;

    public DrawableTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void init()
    {
        LayoutInflater.from(mContext).inflate(R.layout.layout_drawble_textview,this);
        mTvText = (TextView) findViewById(R.id.tv_text);
        mIvDot = (ImageView) findViewById(R.id.iv_dot);
    }

    public void setTextSize(int unit, float size)
    {
        mTvText.setTextSize(unit,size);
    }

    public void setTypeface(Typeface tf, int style)
    {
        mTvText.setTypeface(tf,style);
    }

    public void setTextColor(int color)
    {
        mTvText.setTextColor(color);
    }

    public void setAllCaps(boolean allCaps)
    {
        mTvText.setAllCaps(allCaps);
    }

    public CharSequence getText()
    {
        return mTvText.getText();
    }

    public void setText(CharSequence text)
    {
        mTvText.setText(text);
    }

    public void setImageDrawable(Drawable drawable)
    {
        mIvDot.setImageDrawable(drawable);
    }

    public void setSingleLine()
    {
        mTvText.setSingleLine();
    }
}
