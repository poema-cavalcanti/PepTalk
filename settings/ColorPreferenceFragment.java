package com.distracteddevelopment.peptalk.settings;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.distracteddevelopment.peptalk.MainActivity;
import com.distracteddevelopment.peptalk.R;
import com.distracteddevelopment.peptalk.SettingsActivity;
import com.distracteddevelopment.peptalk.ThemeColors;
import com.kizitonwose.colorpreference.ColorDialog;
import com.kizitonwose.colorpreference.ColorPreference;

/**
 * This fragment shows COLOR preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ColorPreferenceFragment extends PreferenceFragment implements ColorDialog.OnColorSelectedListener {
    String MENU_KEY;
    String BACKGROUND_KEY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MENU_KEY = getResources().getString(R.string.pref_color_menu_key);
        BACKGROUND_KEY = getResources().getString(R.string.pref_color_background_key);

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_color);

        // MENU/THEME
        ColorPreference menuPreference = (ColorPreference) findPreference(MENU_KEY);
        menuPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        });

        // BACKGROUND
        ColorPreference backgroundPreference = (ColorPreference) findPreference(BACKGROUND_KEY);
        backgroundPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        });

        setHasOptionsMenu(true);
    }

    @Override
    public void onColorSelected(int color, String tag) {
        // BACKGROUND
        if (tag.equals(getResources().getString(R.string.pref_color_background_key))) {
            ColorPreference backgroundPreference = (ColorPreference) findPreference(BACKGROUND_KEY);
            ThemeColors.setNewBackgroundColor(getActivity(), ThemeColors.getColorHex(color));
            backgroundPreference.setValue(color);
        }
        // MENU/THEME
        if (tag.equals(getResources().getString(R.string.pref_color_menu_key))){
            ColorPreference menuPreference = (ColorPreference) findPreference(MENU_KEY);
            ThemeColors.setNewThemeColor(getActivity(), color);
            menuPreference.setValue(color);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (android.R.id.home) :
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}