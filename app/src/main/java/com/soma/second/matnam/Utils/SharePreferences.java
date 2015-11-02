package com.soma.second.matnam.Utils;

import android.content.SharedPreferences;

import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;
import com.soma.second.matnam.App;
import com.soma.second.matnam.listdubbies.provider.SharedPrefFiles;
import com.soma.second.matnam.listdubbies.provider.SharedPrefKeys;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class SharePreferences {

    public static void saveCustomization(SharedPrefKeys prefKey, int progress) {
        SharedPreferences customize_pref = getCustomizePref();
        SharedPreferences.Editor editor = customize_pref.edit();
        editor.putInt(prefKey.toString(), progress);
        editor.commit();
    }

    public static int getValue(SharedPrefKeys prefKey) {
        SharedPreferences customize_pref = getCustomizePref();
        int defaultValue = getDefaultValue(prefKey);
        return customize_pref.getInt(prefKey.toString(), defaultValue);
    }

    private static SharedPreferences getCustomizePref() {
        return App.getAppContext().getSharedPreferences(SharedPrefFiles.CUSTOMIZE_SETTINGS.toString(), 0);
    }

    private static int getDefaultValue(SharedPrefKeys prefKey) {
        int defaultValue = 0;
        switch (prefKey) {
            case GAP_PROGRESS:
                defaultValue = App.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                break;
            case SPEED_PROGRESS:
                defaultValue = ListBuddiesLayout.DEFAULT_SPEED;
                break;
            case DIV_HEIGHT_PROGRESS:
                defaultValue = App.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                break;
        }
        return defaultValue;
    }

    public static void reset() {
        for (SharedPrefKeys key : SharedPrefKeys.values()) {
            saveCustomization(key, getDefaultValue(key));
        }
    }
}