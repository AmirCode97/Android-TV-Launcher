package com.smarttv.launcher.fragments

import android.os.Bundle
import androidx.leanback.preference.LeanbackPreferenceFragmentCompat
import androidx.leanback.preference.LeanbackSettingsFragmentCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.smarttv.launcher.R

class SettingsFragment : LeanbackSettingsFragmentCompat() {

    override fun onPreferenceStartInitialScreen() {
        startPreferenceFragment(MainSettingsFragment())
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        val fragment = childFragmentManager.fragmentFactory.instantiate(
            requireActivity().classLoader,
            pref.fragment!!
        )
        fragment.arguments = pref.extras
        startPreferenceFragment(fragment)
        return true
    }

    override fun onPreferenceStartScreen(
        caller: PreferenceFragmentCompat,
        pref: PreferenceScreen
    ): Boolean {
        val fragment = MainSettingsFragment()
        val args = Bundle().apply {
            putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, pref.key)
        }
        fragment.arguments = args
        startPreferenceFragment(fragment)
        return true
    }

    class MainSettingsFragment : LeanbackPreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }
    }
}
