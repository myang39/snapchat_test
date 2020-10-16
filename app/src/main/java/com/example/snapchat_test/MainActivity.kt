package com.example.snapchat_test

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.snapchat.kit.sdk.SnapCreative
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi
import com.snapchat.kit.sdk.creative.exceptions.SnapMediaSizeException
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory
import com.snapchat.kit.sdk.creative.media.SnapPhotoFile
import com.snapchat.kit.sdk.creative.models.SnapPhotoContent
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendPhotoButton.setOnClickListener {
//            println("You clicked me!")
            sendSnapchatPhoto()
        }
    }

    fun sendSnapchatPhoto() {
        val intent = Intent(Intent.ACTION_PICK);
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("Luke", "Photo was selected")

            Log.d("Luke", Environment.getExternalStorageState())
            val uri = data.data

            // test if the uri works (yes)
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
//            selected_photo.setImageBitmap(bitmap)
            // test end

            // store file locally
            if (uri != null) {
                val file = File(cacheDir, "tempPhoto")
                contentResolver.openInputStream(uri)?.copyTo(file.outputStream())

                // snapChat part
                val snapCreativeKitApi: SnapCreativeKitApi = SnapCreative.getApi(this)
                val snapMediaFactory : SnapMediaFactory? = SnapCreative.getMediaFactory(this);
                val photoFile : SnapPhotoFile?

                try {
                    photoFile = snapMediaFactory?.getSnapPhotoFromFile(file)
                } catch (e: SnapMediaSizeException) {
                    print(e)
                    return
                }
                if (photoFile != null) {
                    Log.d("Luke", "Sending photo to snapchat")
                    val snapPhotoContent: SnapPhotoContent = SnapPhotoContent(photoFile)
                    // The following line would cause the app the crash, "Failure to find the configured root..."
                    snapCreativeKitApi.send(snapPhotoContent)
                    Log.d("Luke", "end")
                }
            } // inputStream


//            Log.d("Luke", "uri: $uri")
//            Log.d("Luke", "uri?.path: ${uri?.path}")
//            // val file = File(uri?.path)
//            val uriPathHelper = URIPathHelper()
//            val filePath = uri?.let { uriPathHelper.getPath(this, it) }
//
//            Log.d("Luke", "filePath: $filePath")
//
//            val file = File(filePath)
//            Log.d("Luke", "absolutePath:" + file.absolutePath)
//            // test if the file exists and points to the photo I picked
//            if (file.exists()) { // somehow the file I just picked doesn't exist
//                Log.d("Luke", "photo file exists")
//                val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
//                selected_photo.setImageBitmap(myBitmap)
//            }
            // test end
        }
    }

//    fun copyInputStreamToFile(inputStream: InputStream, outputStream: FileOutputStream) {
//        val buffer = ByteArray(8192)
//        inputStream.use { input ->
//            outputStream.use { fileOut ->
//                while (true) {
//                    val length = input.read(buffer)
//                    if (length <= 0)
//                        break
//                    fileOut.write(buffer, 0, length)
//                }
//                fileOut.flush()
//                fileOut.close()
//            }
//        }
//        inputStream.close()
//    }
}