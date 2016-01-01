package edu.grinnell.appdev.grinnelldirectory.Other;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by samee on 1/1/16.
 */
public class Utils {

    public static float getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    public static float getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels / displayMetrics.density;
    }

    public static boolean isOrientationPortrait(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        } else {
            return false;
        }
    }
}
