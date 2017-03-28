package com.hsalf.menuanim.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BitmapHelper {

    private static final String TAG = "BitmapHelper";

    public static final int FIT = 0;
    public static final int FILL = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FIT, FILL})
    public @interface Mode {

    }

    public static Bitmap getBitmapFromRes(Context context, Builder builder) {
        BitmapFactory.Options op = prepareOptions(context, builder);
        op.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(context.getResources(), builder.res, op);
    }

    public static BitmapFactory.Options prepareOptions(Context context, Builder builder) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.Options dimensOptions = getDimensions(context, builder.res);
        int scale = 1;
        boolean fit = FIT == builder.mode; // Currently always in fill mode
        if (builder.forHeight < 1 || builder.forWidth < 1) {
            options.inSampleSize = scale;
            return options;
        }
        if (dimensOptions.outHeight > dimensOptions.outWidth) {
            scale = getScaleFactor(fit ? dimensOptions.outWidth : dimensOptions.outHeight,
                    fit ? builder.forWidth : builder.forHeight);
        } else {
            scale = getScaleFactor(fit ? dimensOptions.outHeight : dimensOptions.outWidth,
                    fit ? builder.forHeight : builder.forWidth);
        }
        options.inSampleSize = scale;
        options.inScaled = true;
        options.inDensity = fit ? dimensOptions.outWidth : dimensOptions.outHeight;
        options.inTargetDensity = (fit ? builder.forWidth : builder.forHeight) * options.inSampleSize;
        return options;
    }

    public static int getScaleFactor(int originalDimen, int targetDimen) {
        int scale = 1;
        if (originalDimen > targetDimen) {
            int halfDimen = originalDimen / 2;
            while (halfDimen / scale > targetDimen) {
                scale *= 2;
            }
        }
        return scale;
    }

    public static BitmapFactory.Options getDimensions(Context context, int res) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), res, options);
        return options;
    }

    public static class Builder {
        public final int res;
        @Mode
        public int mode = FILL;
        public int forWidth;
        public int forHeight;

        public Builder(int res) {
            this.res = res;
        }
    }

}
