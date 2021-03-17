package com.scp.leagueofquiz.entrypoint.preferences

import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.scp.leagueofquiz.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val signaturePreference: EditTextPreference? = findPreference("signature")
        signaturePreference?.isVisible = true
        signaturePreference?.setIcon(R.drawable.aatrox)
        signaturePreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)){
                "Not set"
            } else{
                "Length of saved value: " + text.length
            }

        }
    }
}