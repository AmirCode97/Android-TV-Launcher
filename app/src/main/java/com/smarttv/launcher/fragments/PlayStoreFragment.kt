package com.smarttv.launcher.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.smarttv.launcher.R
import com.smarttv.launcher.models.AppModel
import com.smarttv.launcher.presenters.CardPresenter

class PlayStoreFragment : BrowseSupportFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        title = getString(R.string.menu_playstore)
        setupRows()
    }

    private fun setupRows() {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        
        // Featured Apps
        val featuredHeader = HeaderItem(0, getString(R.string.playstore_featured))
        val featuredAdapter = ArrayObjectAdapter(CardPresenter())
        addFeaturedApps(featuredAdapter)
        rowsAdapter.add(ListRow(featuredHeader, featuredAdapter))
        
        // Top Free
        val topFreeHeader = HeaderItem(1, getString(R.string.playstore_top_free))
        val topFreeAdapter = ArrayObjectAdapter(CardPresenter())
        addTopFreeApps(topFreeAdapter)
        rowsAdapter.add(ListRow(topFreeHeader, topFreeAdapter))
        
        adapter = rowsAdapter
        
        onItemViewClickedListener = OnItemViewClickedListener { _, item, _, _ ->
            if (item is AppModel) {
                openPlayStoreApp(item.packageName)
            }
        }
    }

    private fun addFeaturedApps(adapter: ArrayObjectAdapter) {
        val featuredApps = listOf(
            AppModel("com.netflix.ninja", "Netflix"),
            AppModel("com.spotify.tv.android", "Spotify"),
            AppModel("com.disney.disneyplus", "Disney+"),
            AppModel("com.amazon.avod.thirdpartyclient", "Prime Video")
        )
        featuredApps.forEach { adapter.add(it) }
    }

    private fun addTopFreeApps(adapter: ArrayObjectAdapter) {
        val topFreeApps = listOf(
            AppModel("com.youtube.tv", "YouTube"),
            AppModel("com.plexapp.android", "Plex"),
            AppModel("org.xbmc.kodi", "Kodi")
        )
        topFreeApps.forEach { adapter.add(it) }
    }

    private fun openPlayStoreApp(packageName: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=$packageName")
                setPackage("com.android.vending")
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback to browser
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            }
            startActivity(intent)
        }
    }
}
