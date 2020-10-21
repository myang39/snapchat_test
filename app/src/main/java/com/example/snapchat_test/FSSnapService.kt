package com.example.snapchat_test

import android.content.Context
import com.snapchat.kit.sdk.SnapCreative
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi_Factory
import com.snapchat.kit.sdk.creative.media.SnapLensLaunchData
import com.snapchat.kit.sdk.creative.models.SnapLensContent
import java.net.URL

class FSSnapService(context: Context) {
    enum class Result (val result: String) {
        win("victory"),
        tie("draw"),
        lost("defeat")
    }
    interface DestinationProps {
        fun lensId() : String
        fun attachmentURLString(): String?
        fun launchData(): SnapLensLaunchData
    }

    private val snapCreativeKitApi: SnapCreativeKitApi = SnapCreative.getApi(context)

    fun share(destination: DestinationProps) {
        val snapContent = SnapLensContent("52510440879")
        snapContent.attachmentUrl = destination.attachmentURLString()
        snapContent.snapLensLaunchData = destination.launchData()
        snapCreativeKitApi.send(snapContent)
    }

    class MatchupRecapDestination (
        private val recapURL: URL,
        val homeTeamName: String,
        val homeTeamPoints: Number,
        val homeTeamProjectedPoints: Number,
        val awayTeamName: String,
        val awayTeamPoints: String,
        val awayTeamProjectedPoints: Number
    ) : DestinationProps {
        override
        fun lensId(): String {
            return "52510440879"
        }

        override fun attachmentURLString(): String? {
            return recapURL.toString()
        }

        override fun launchData(): SnapLensLaunchData {
            TODO("Not yet implemented")
        }
    }
}