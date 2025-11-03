package com.smarttv.launcher.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.smarttv.launcher.R
import com.smarttv.launcher.fragments.SettingsFragment

class SettingsActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.settings_fragment, SettingsFragment())
                .commit()
        }
    }
}
