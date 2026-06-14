package com.dicoding.courseschedule.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val notifyKey = context.getString(com.dicoding.courseschedule.R.string.pref_key_notify)
            val isEnabled = prefs.getBoolean(notifyKey, false)
            if (isEnabled) {
                DailyReminder().setDailyReminder(context)
            }
        }
    }
}
