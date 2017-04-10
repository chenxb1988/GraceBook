package com.grace.book.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.grace.book.R;
import com.grace.book.activity.base.BaseLoadingActivity;
import com.grace.book.widget.CropView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 截取图片
 */
public class CropPhotoActivity extends BaseLoadingActivity {

    private static final int BITMAP_MAX_SIZE = 2048;
    private CropView mCropView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_crop_photo);
        setTitle("裁剪图片", "使用");
        showContentView();

        Uri data = getIntent().getData();
        InputStream is;
        Bitmap bitmap = null;
        try {
            is = getContentResolver().openInputStream(data);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException | OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
        }

        bitmap = scaleDownIfTooLarge(bitmap);
        mCropView = (CropView) findViewById(R.id.crop);
        mCropView.setImageBitmap(bitmap);
    }

    @Override
    public void onClickRightText() {
        onCropImage();
    }

    @Override
    protected void loadData() {

    }

    private Bitmap scaleDownIfTooLarge(Bitmap bitmap) {
        if (bitmap.getHeight() >= BITMAP_MAX_SIZE || bitmap.getWidth() >= BITMAP_MAX_SIZE) {
            float w, h;
            float bw = bitmap.getWidth();
            float bh = bitmap.getHeight();
            if (bw > bh) {
                w = BITMAP_MAX_SIZE;
                h = w * (bh / bw);
            } else {
                h = BITMAP_MAX_SIZE;
                w = h * (bw / bh);
            }
            return Bitmap.createScaledBitmap(bitmap, (int) w, (int) h, false);
        } else {
            return bitmap;
        }
    }

    private void onCropImage() {
        Bitmap bitmap = mCropView.getCroppedBitmap();
        if (bitmap.getHeight() > 200 || bitmap.getWidth() > 200) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        }
        Uri uri = getIntent().getParcelableExtra(MediaStore.EXTRA_OUTPUT);
        writeBitmapToFile(bitmap, new File(uri.getPath()));
        setResult(RESULT_OK, getIntent());
        finish();
    }

    private void writeBitmapToFile(Bitmap bitmap, File file) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
