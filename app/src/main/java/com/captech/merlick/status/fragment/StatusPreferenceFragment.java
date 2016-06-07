package com.captech.merlick.status.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.captech.merlick.status.R;

/**
 * Created by merlick on 5/24/16.
 */
public class StatusPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_sort_list_preference));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_pref_filter_enabled));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_pref_filter_config_change));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_pref_filter_battery_change));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_pref_filter_power_change));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_pref_filter_screen_change));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_pref_filter_time_ticks_change));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_pref_filter_log_length_change));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.key_pref_start_monitor_at_boot));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));

            if (prefIndex < 0) {
                prefIndex = 0;
            }

            listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            listPreference.setOnPreferenceChangeListener(new StatusListPreferenceChangeListener());
        } else if (preference instanceof CheckBoxPreference){
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;

            boolean defaultVal = true;

            if (key.equals(getString(R.string.key_pref_start_monitor_at_boot))
                    || key.equals(getString(R.string.key_pref_filter_enabled))) {
                defaultVal = false;
            }

            ((CheckBoxPreference) preference).setChecked(sharedPreferences.getBoolean(key, defaultVal));
        }
    }

    class StatusListPreferenceChangeListener implements Preference.OnPreferenceChangeListener {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            preference.setSummary(newValue.toString());
            return true;
        }
    }

}
