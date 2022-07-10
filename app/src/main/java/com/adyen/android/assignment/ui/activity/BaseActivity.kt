package com.adyen.android.assignment.ui.activity

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    fun openSettings() {
        startActivity(
            Intent(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    Settings.Panel.ACTION_INTERNET_CONNECTIVITY
                else Settings.ACTION_SETTINGS
            )
        )
    }
}