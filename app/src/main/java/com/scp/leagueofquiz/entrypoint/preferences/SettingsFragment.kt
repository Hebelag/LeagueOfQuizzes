package com.scp.leagueofquiz.entrypoint.preferences

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.preference.*
import com.scp.leagueofquiz.R

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var signatureTextPreference: EditTextPreference
    private lateinit var replyListPreference: ListPreference
    private lateinit var userLikeExperience: SeekBarPreference
    private lateinit var syncToggle: SwitchPreferenceCompat
    private lateinit var attachmentDownloadToggle: SwitchPreferenceCompat
    private lateinit var feedbackPreference: Preference
    private lateinit var noobProtectionToggle: SwitchPreferenceCompat
    private lateinit var customPreference: Preference
    private lateinit var riotConnectPreference: Preference
    private lateinit var anotherPreference: Preference


    private lateinit var messageCategory: PreferenceCategory
    private lateinit var syncCategory: PreferenceCategory
    private lateinit var gameCategory: PreferenceCategory




    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        val context: Context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        signatureTextPreference = EditTextPreference(context)
        signatureTextPreference.key = "signature"
        signatureTextPreference.title = "Your Signature"

        replyListPreference = ListPreference(context)
        replyListPreference.setDefaultValue("reply")
        replyListPreference.setEntries(R.array.reply_entries)
        replyListPreference.setEntryValues(R.array.reply_values)
        replyListPreference.key = "reply"
        replyListPreference.title = "Default reply action"

        userLikeExperience = SeekBarPreference(context)
        userLikeExperience.key = "seekbar"
        userLikeExperience.title = "How much do you like the app?"

        messageCategory = PreferenceCategory(context)
        messageCategory.title = "Messages"

        syncCategory = PreferenceCategory(context)

        syncToggle = SwitchPreferenceCompat(context)
        syncToggle.key = "synchronize"
        syncToggle.title = "Sync email periodically"


        attachmentDownloadToggle = SwitchPreferenceCompat(context)
        attachmentDownloadToggle.key = "attachment"
        attachmentDownloadToggle.title = "Download incoming attachments"
        attachmentDownloadToggle.summaryOff = "Only download attachments when manually requested"
        attachmentDownloadToggle.summaryOn = "Automatically download attachments for incoming emails"



        feedbackPreference = Preference(context)
        feedbackPreference.key = "feedback"
        feedbackPreference.title = "Send feedback"
        feedbackPreference.summary = "Report technical issues or suggest new features"
        feedbackPreference.setIcon(R.drawable.heimerdinger)
        feedbackPreference.intent = Intent(ACTION_VIEW,
                Uri.parse("https://support-leagueoflegends.riotgames.com/hc/en-us"))

        noobProtectionToggle = SwitchPreferenceCompat(context)
        noobProtectionToggle.key = "noob_protection"
        noobProtectionToggle.title = "Protect yourself against Pro Players"
        noobProtectionToggle.summary = "If you're bronze please toggle this"

        gameCategory = PreferenceCategory(context)

        customPreference = Preference(context)
        customPreference.key = "custom"
        customPreference.title = "Press to launch google"
        customPreference.intent = Intent(ACTION_VIEW,
            Uri.parse("http://www.google.com"))

        riotConnectPreference = Preference(context)
        riotConnectPreference.key = "riot_connect"
        riotConnectPreference.title = "Connect your account with Riot Games"
        riotConnectPreference.summary = "Log In and receive 500 Blue Essence!"
        riotConnectPreference.intent = Intent(ACTION_VIEW,
            Uri.parse("https://na.leagueoflegends.com/en-gb/"))

        anotherPreference = SwitchPreferenceCompat(context)
        anotherPreference.key = "another_one"
        anotherPreference.title = "Check if scroll works"
        anotherPreference.summary = "If you can read this then scrolling works"



        addAllCategories(screen)
        addAllPreferences(screen)

        preferenceScreen = screen

        addDependencies()

    }

    private fun addAllPreferences(screen: PreferenceScreen) {
        messageCategory.addPreference(signatureTextPreference)
        messageCategory.addPreference(replyListPreference)
        messageCategory.addPreference(userLikeExperience)

        syncCategory.addPreference(syncToggle)
        syncCategory.addPreference(attachmentDownloadToggle)

        gameCategory.addPreference(feedbackPreference)
        gameCategory.addPreference(noobProtectionToggle)

        screen.addPreference(customPreference)
        screen.addPreference(riotConnectPreference)
        screen.addPreference(anotherPreference)

    }

    private fun addDependencies() {
        attachmentDownloadToggle.dependency = "synchronize"
    }

    private fun addAllCategories(screen: PreferenceScreen) {
        screen.addPreference(messageCategory)
        screen.addPreference(syncCategory)
        screen.addPreference(gameCategory)

    }
}