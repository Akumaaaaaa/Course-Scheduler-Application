package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            val nightMode = when (newValue) {
                getString(R.string.pref_dark_auto) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                getString(R.string.pref_dark_off) -> AppCompatDelegate.MODE_NIGHT_NO
                getString(R.string.pref_dark_on) -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            updateTheme(nightMode)
            true
        }

        val notifySwitchPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notifySwitchPreference?.setOnPreferenceChangeListener { preference, newValue ->
            val isNotificationEnabled = newValue as Boolean
            if (isNotificationEnabled) {
                // Schedule notification
                scheduleNotification()
            } else {
                // Cancel notification
                cancelNotification()
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
    }

    private fun scheduleNotification() {
        DailyReminder().setDailyReminder(requireContext())
    }

    private fun cancelNotification() {
        DailyReminder().cancelAlarm(requireContext())
    }
}

