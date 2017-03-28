package com.hsalf.menuanim.utils;


import android.graphics.Bitmap;
import android.view.View;

public interface ImageCache {
    Bitmap getBitmap(int res, ImageCacheImpl.Callback callback);

    Bitmap getBitmap(int res, ImageCacheImpl.Callback callback,
                   int width, int height);

    Bitmap getBitmap(View target, int res, ImageCacheImpl.Callback callback);
}
