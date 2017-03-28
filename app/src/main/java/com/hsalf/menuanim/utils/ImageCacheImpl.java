package com.hsalf.menuanim.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class ImageCacheImpl extends ImageLoaderImpl implements ImageCache {

    private static final String TAG = "ImageCacheImpl";

    private LruCache<Integer, Bitmap> mLruCache;
    private Map<Integer, Callback> mTasks = new HashMap<>();

    public ImageCacheImpl() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.i(TAG, "Allocated memory: " + maxMemory);
        mLruCache = new LruCache<Integer, Bitmap>(maxMemory) {
            @Override
            protected int sizeOf(Integer res, Bitmap bitmap) {
                // Just returning image size in kb
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public boolean isBitmapAvailable(int res) {
        return mLruCache.get(res) != null;
    }

    public Bitmap getBitmap(int res, Callback callback) {
        return getBitmap(res, callback, -1, -1);
    }

    public Bitmap getBitmap(int res, Callback callback, int width, int height) {
        if (isBitmapAvailable(res)) {
            return mLruCache.get(res);
        }
        mTasks.put(res, callback);
        loadBitmap(res, width, height);
        return null;
    }

    @Override
    public Bitmap getBitmap(final View target, final int res, final Callback callback) {
        if (isBitmapAvailable(res)) {
            return mLruCache.get(res);
        }
        target.post(new Runnable() {
            @Override
            public void run() {
                getBitmap(res, callback, target.getMeasuredWidth(), target.getMeasuredHeight());
            }
        });
        return null;
    }

    @Override
    protected void onGotBitmap(int res, Bitmap bitmap) {
        if (bitmap == null) {
            Log.w(TAG, "No bitmap available for: " + res);
            return;
        }
        mLruCache.put(res, bitmap);
        if (mTasks.containsKey(res)) {
            mTasks.get(res).onGotBitmap(res, bitmap);
            mTasks.remove(res);
        }
    }

    public interface Callback {
        void onGotBitmap(int res, Bitmap value);
    }

}
