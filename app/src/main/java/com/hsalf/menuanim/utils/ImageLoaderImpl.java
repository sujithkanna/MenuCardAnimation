package com.hsalf.menuanim.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.hsalf.menuanim.App;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class ImageLoaderImpl extends HandlerThread {

    private static final String TAG = "ImageLoaderImpl";
    private static final int LOAD_BITMAP = 90;

    private Handler mMainHandler;
    private Handler mThreadHandler;
    private Executor mThreadPoolExecutor = Executors.newFixedThreadPool(20);

    protected ImageLoaderImpl() {
        super(TAG);
        start();
        mThreadHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                processMessage(msg);
            }
        };
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    private void processMessage(final Message msg) {
        if (LOAD_BITMAP == msg.what) {
            Log.i(TAG, "Fetch: " + msg.obj);
            final BitmapHelper.Builder builder = new BitmapHelper.Builder((int) msg.obj);
            builder.forWidth = msg.arg1;
            builder.forHeight = msg.arg2;
            mThreadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    final Bitmap loadBitmap = BitmapHelper.getBitmapFromRes(App.getInstance(),
                            builder);
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onGotBitmap(builder.res, loadBitmap);
                        }
                    });
                }
            });
        }
    }

    protected abstract void onGotBitmap(int res, Bitmap bitmap);

    protected void loadBitmap(int res, int width, int height) {
        mThreadHandler.sendMessage(mThreadHandler.obtainMessage(LOAD_BITMAP, width, height, res));
    }

}
