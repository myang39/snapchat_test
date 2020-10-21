package com.example.snapchat_test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.snapchat.kit.sdk.SnapCreative
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi
import com.snapchat.kit.sdk.creative.exceptions.SnapMediaSizeException
import com.snapchat.kit.sdk.creative.media.SnapLensLaunchData
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory
import com.snapchat.kit.sdk.creative.media.SnapPhotoFile
import com.snapchat.kit.sdk.creative.models.SnapLensContent
import com.snapchat.kit.sdk.creative.models.SnapPhotoContent
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


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
        snapLensContent.attachmentUrl = "https://yahoo.com"

        val snapCreativeKitApi = SnapCreative.getApi(this)
        snapCreativeKitApi.send(snapLensContent)
    }
}