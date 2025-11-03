package com.smarttv.launcher.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.smarttv.launcher.R
import com.smarttv.launcher.fragments.PlayStoreFragment

class PlayStoreActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playstore)
        
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://")
                setPackage("com.android.vending")
            }
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.playstore_fragment, PlayStoreFragment())
                    .commit()
            }
        }
    }
}
