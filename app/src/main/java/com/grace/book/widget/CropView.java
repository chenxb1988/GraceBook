package com.grace.book.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import com.grace.book.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ccheng on 10/24/14.
 */
public class CropView extends PhotoView {

    private PhotoViewAttacher mAttacher;
    private float mCropSize;
    private float mImageRatio;

    public CropView(Context context) {
        super(context);
        init();
    }

    public CropView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    public CropView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init();
    }

    private void init() {
        mCropSize = getResources().getDimension(R.dimen.crop_size);
        mAttacher = new PhotoViewAttacher(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundResource(android.R.color.black);
        setDrawingCacheEnabled(true);

        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                float w = getWidth();
                float h = w / mImageRatio;
                // Height is larger then screen size.
                if (h >= getHeight()) {
                    w = getHeight() / h * w;
                    h = getHeight();
                }
                float size = Math.min(w, h);
                float minScale = mCropSize / size;
                mAttacher.setAutoMininumScale(minScale);
                getViewTreeObserver().removeOnPreDrawListener(this);
                mAttacher.setScale(minScale);

                return true;
            }
        });
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        mImageRatio = (float) bm.getWidth() / bm.getHeight();
        super.setImageBitmap(bm);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = buildClipRectF();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        int borderWidth = 2;
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF, paint);

        Paint outerPaint = new Paint();
        outerPaint.setColor(getResources().getColor(R.color.black99));
        outerPaint.setStyle(Paint.Style.FILL);

        RectF rectFLeft = new RectF();
        rectFLeft.left = 0;
        rectFLeft.top = 0;
        rectFLeft.right = rectF.left - borderWidth;
        rectFLeft.bottom = getHeight();
        canvas.drawRect(rectFLeft, outerPaint);

        RectF rectFTop = new RectF();
        rectFTop.left = rectF.left - borderWidth;
        rectFTop.top = 0;
        rectFTop.right = rectF.right + borderWidth;
        rectFTop.bottom = rectF.top;
        canvas.drawRect(rectFTop, outerPaint);

        RectF rectFRight = new RectF();
        rectFRight.left = rectF.right + borderWidth;
        rectFRight.top = 0;
        rectFRight.right = getWidth();
        rectFRight.bottom = getHeight();
        canvas.drawRect(rectFRight, outerPaint);

        RectF rectFBottom = new RectF();
        rectFBottom.left = rectF.left - borderWidth;
        rectFBottom.top = rectF.bottom;
        rectFBottom.right = rectF.right + borderWidth;
        rectFBottom.bottom = getHeight();
        canvas.drawRect(rectFBottom, outerPaint);

    }

    public Bitmap getCroppedBitmap() {
        invalidate();
        Bitmap visibleRectangleBitmap = getDrawingCache();
        RectF rectF = buildClipRectF();
        Bitmap bitmap;
        try {
            bitmap = Bitmap.createBitmap(visibleRectangleBitmap, (int) rectF.left, (int) rectF.top, (int) rectF.width(), (int) rectF.height());
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private RectF buildClipRectF() {
        float x = getWidth() / 2 - mCropSize / 2;
        float y = getHeight() / 2 - mCropSize / 2;
        float width = mCropSize;
        float height = mCropSize;
        return new RectF(x, y, x + width, y + height);
    }

}
