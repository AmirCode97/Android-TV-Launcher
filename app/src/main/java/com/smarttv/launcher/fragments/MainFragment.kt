package com.smarttv.launcher.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.smarttv.launcher.R
import com.smarttv.launcher.activities.PlayStoreActivity
import com.smarttv.launcher.activities.SettingsActivity
import com.smarttv.launcher.ai.AIRecommendationEngine
import com.smarttv.launcher.models.AppModel
import com.smarttv.launcher.presenters.CardPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : BrowseSupportFragment() {

    private val aiEngine = AIRecommendationEngine()
    private lateinit var rowsAdapter: ArrayObjectAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        setupUIElements()
        loadRows()
        setupEventListeners()
        
        // Initialize AI recommendations
        CoroutineScope(Dispatchers.Main).launch {
            aiEngine.initialize(requireContext())
            updateAIRecommendations()
        }
    }

    private fun setupUIElements() {
        title = getString(R.string.app_name)
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = ContextCompat.getColor(requireContext(), R.color.fastlane_background)
        searchAffordanceColor = ContextCompat.getColor(requireContext(), R.color.search_opaque)
    }

    private fun loadRows() {
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        
        // Play Store Quick Access - First Row
        val playStoreHeader = HeaderItem(0, getString(R.string.menu_playstore))
        val playStoreAdapter = ArrayObjectAdapter(CardPresenter())
        playStoreAdapter.add(createPlayStoreItem())
        rowsAdapter.add(ListRow(playStoreHeader, playStoreAdapter))
        
        // AI Recommendations
        val aiHeader = HeaderItem(1, getString(R.string.ai_recommendations))
        val aiAdapter = ArrayObjectAdapter(CardPresenter())
        rowsAdapter.add(ListRow(aiHeader, aiAdapter))
        
        // Popular Apps
        val popularHeader = HeaderItem(2, getString(R.string.category_popular_apps))
        val popularAdapter = ArrayObjectAdapter(CardPresenter())
        loadPopularApps(popularAdapter)
        rowsAdapter.add(ListRow(popularHeader, popularAdapter))
        
        // Entertainment
        val entertainmentHeader = HeaderItem(3, getString(R.string.category_entertainment))
        val entertainmentAdapter = ArrayObjectAdapter(CardPresenter())
        loadEntertainmentApps(entertainmentAdapter)
        rowsAdapter.add(ListRow(entertainmentHeader, entertainmentAdapter))
        
        // Streaming Services
        val streamingHeader = HeaderItem(4, getString(R.string.category_streaming))
        val streamingAdapter = ArrayObjectAdapter(CardPresenter())
        loadStreamingApps(streamingAdapter)
        rowsAdapter.add(ListRow(streamingHeader, streamingAdapter))
        
        // Games
        val gamesHeader = HeaderItem(5, getString(R.string.category_games))
        val gamesAdapter = ArrayObjectAdapter(CardPresenter())
        loadGamesApps(gamesAdapter)
        rowsAdapter.add(ListRow(gamesHeader, gamesAdapter))
        
        // Settings
        val settingsHeader = HeaderItem(6, getString(R.string.menu_settings))
        val settingsAdapter = ArrayObjectAdapter(CardPresenter())
        settingsAdapter.add(createSettingsItem())
        rowsAdapter.add(ListRow(settingsHeader, settingsAdapter))
        
        adapter = rowsAdapter
    }

    private fun createPlayStoreItem(): AppModel {
        return AppModel(
            packageName = "com.android.vending",
            appName = getString(R.string.menu_playstore),
            iconResId = R.drawable.ic_play_store,
            isSystemApp = true
        )
    }

    private fun createSettingsItem(): AppModel {
        return AppModel(
            packageName = "settings",
            appName = getString(R.string.menu_settings),
            iconResId = R.drawable.ic_settings,
            isSystemApp = true
        )
    }

    private fun loadPopularApps(adapter: ArrayObjectAdapter) {
        CoroutineScope(Dispatchers.IO).launch {
            val popularPackages = listOf(
                "com.netflix.ninja",
                "com.amazon.avod.thirdpartyclient",
                "com.youtube.tv",
                "com.spotify.tv.android",
                "com.disney.disneyplus",
                "com.hbo.hbonow",
                "com.google.android.youtube.tv",
                "com.plexapp.android"
            )
            
            val installedApps = getInstalledApps(popularPackages)
            
            withContext(Dispatchers.Main) {
                installedApps.forEach { adapter.add(it) }
                
                // Add Play Store shortcut if no apps installed
                if (installedApps.isEmpty()) {
                    adapter.add(createPlayStoreItem())
                }
            }
        }
    }

    // سایر متودهای مشابه بالا برای بارگذاری Entertainment, Streaming, Games و Events 
    // و همچنین متودهای مرتبط با AI و Event Listeners ...

}
