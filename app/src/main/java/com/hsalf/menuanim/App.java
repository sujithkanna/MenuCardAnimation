package com.hsalf.menuanim;

import android.app.Application;

import com.hsalf.menuanim.utils.ImageCache;
import com.hsalf.menuanim.utils.ImageCacheImpl;

public class App extends Application {

    private static final String TAG = "App";

    private static App sApp;

    private ImageCache mImageCacheImpl;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mImageCacheImpl = new ImageCacheImpl();
    }

    public static App getInstance() {
        return sApp;
    }

    public ImageCache getImageCache() {
        return mImageCacheImpl;
    }
}
