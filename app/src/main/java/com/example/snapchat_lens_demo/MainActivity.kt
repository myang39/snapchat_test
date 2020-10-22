package com.example.snapchat_lens_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.snapchat.kit.sdk.SnapCreative
import com.snapchat.kit.sdk.creative.media.SnapLensLaunchData
import com.snapchat.kit.sdk.creative.models.SnapLensContent
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendStickers.setOnClickListener {
            sendSnapChatStickers()
        }
    }

    private fun sendSnapChatStickers() {
        // Create a SnapLensContent object with LensID to be shared
        val snapLensContent= SnapLensContent("52510440879")

        // build SnapLensLaunchData, add to snapLensContent
        val launchData = SnapLensLaunchData.Builder()
            .addStringKeyPair("result", "draw")
            .addStringKeyPair("nameTeam1", "homeTeamName")
            .addStringKeyPair("nameTeam2", "awayTeamName")
            .addNumberKeyPair("ptsTeam1", 10)
            .addNumberKeyPair("ptsTeam2", 20)
            .addNumberKeyPair("projTeam1", 30)
            .addNumberKeyPair("projTeam2", 40).build()
        snapLensContent.snapLensLaunchData = launchData
        snapLensContent.attachmentUrl = "https://football.fantasysports.yahoo.com" // needs to have https:// to work

        val snapCreativeKitApi = SnapCreative.getApi(this)
        snapCreativeKitApi.send(snapLensContent)
    }
}