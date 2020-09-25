package com.localazy.quicknote

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Process
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.localazy.android.Localazy
import com.localazy.quicknote.localazy.LocaleViewModel
import com.localazy.quicknote.ui.ShowLocales

class LocaleActivity : AppCompatActivity() {

    private val localesViewModel by viewModels<LocaleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowLocales(
                localesViewModel.locales,
                onChange = {

                    // Change the locale and persist the new choice.
                    Localazy.forceLocale(it.locale, true)

                    // Stop the service and reopen MainActivity with clearing top.
                    // MainActivity restarts the service, so the locale change
                    // is applied across both activity and the service.
                    startFloatingService(INTENT_COMMAND_EXIT)
                    startActivity(Intent(this@LocaleActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    })

                },
                onHelp = {
                    // Open the project on Localazy to allow contributors to help us with translating.
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Localazy.getProjectUri()).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                    )
                }
            )
        }
    }

}
