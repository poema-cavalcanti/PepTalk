package com.distracteddevelopment.peptalk;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.distracteddevelopment.peptalk.alarm.OnBootReceiver;
import com.distracteddevelopment.peptalk.settings.ColorPreferenceFragment;
import com.distracteddevelopment.peptalk.settings.GeneralPreferenceFragment;
import com.distracteddevelopment.peptalk.settings.NotificationPreferenceFragment;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeColors.MENU_THEME_ID);
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(onChange);
    }

    @Override
    public void onPause() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(onChange);
        super.onPause();
    }

    /**
     * HELPER METHODS of varying degrees of necessity.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || ColorPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This makes notifications work.
     */
    SharedPreferences.OnSharedPreferenceChangeListener onChange=
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                    if (getString(R.string.pref_new_message_notification_key).equals(key)) {
                        boolean enabled=prefs.getBoolean(key, false);
                        int flag=(enabled ?
                                PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                                PackageManager.COMPONENT_ENABLED_STATE_DISABLED);

                        ComponentName component=new ComponentName(getApplicationContext(),
                                OnBootReceiver.class);

                        getPackageManager().setComponentEnabledSetting(component, flag, PackageManager.DONT_KILL_APP);

                        if (enabled) {
                            OnBootReceiver.setAlarm(getApplicationContext());
                        } else {
                            OnBootReceiver.cancelAlarm(getApplicationContext());
                        }
                    } else if (getString(R.string.pref_notification_time).equals(key)) {
                        OnBootReceiver.cancelAlarm(getApplicationContext());
                        OnBootReceiver.setAlarm(getApplicationContext());
                    }
                }
            };
}
