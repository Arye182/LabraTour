package com.example.labratour.presentation.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.labratour.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        loadSetings()
    }

    private fun loadSetings() {
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences((context))
        val distance_disabled = sp.getBoolean("refresh_disabled", true)
        val distance = sp.getInt("distance", 300)
        

    }
}