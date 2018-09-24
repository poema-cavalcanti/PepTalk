package com.distracteddevelopment.peptalk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;

public class ThemeColors {

    @ColorInt
    public static int MENU_COLOR_INT;

    public static int MENU_THEME_ID;

    public static String MENU_COLOR_STRING;

    @ColorInt
    public static int BACKGROUND_COLOR_INT;

    public static String BACKGROUND_COLOR_STRING;


    public ThemeColors(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // MENU COLOR STUFF
        MENU_COLOR_INT = sharedPreferences.getInt(
                context.getResources().getString(R.string.pref_color_menu_key),
                R.color.colorDarkPink);
        MENU_COLOR_STRING = getColorHex(MENU_COLOR_INT);

        // BACKGROUND COLOR STUFF
        BACKGROUND_COLOR_INT = sharedPreferences.getInt(
                context.getResources().getString(R.string.pref_color_background_key),
                R.color.color_default);
        BACKGROUND_COLOR_STRING = getColorHex(BACKGROUND_COLOR_INT);

        // THEME STUFF
        MENU_THEME_ID = context.getResources().getIdentifier("T_" + MENU_COLOR_STRING.substring(1),
                "style", context.getPackageName());
    }

    public static void setNewThemeColor(Activity activity, int color) {
        Context context = activity.getApplicationContext();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        MENU_COLOR_STRING = getColorHex(color);
        MENU_COLOR_INT = color;

        editor.putInt(context.getResources().getString(R.string.pref_color_menu_key), color);

        editor.apply();

        //activity.recreate();
    }

    public static void setNewBackgroundColor(Activity activity, String stringColor) {
        Context context = activity.getApplicationContext();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        BACKGROUND_COLOR_STRING = stringColor;
        BACKGROUND_COLOR_INT = Color.parseColor(stringColor);

        editor.putInt(context.getResources().getString(R.string.pref_color_background_key), BACKGROUND_COLOR_INT);
        editor.apply();

        //activity.recreate();
    }

    public static String getColorHex(int color) {
        return String.format("#%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color));
    }

}