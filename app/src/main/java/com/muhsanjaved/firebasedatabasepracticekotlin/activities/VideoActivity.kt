package com.muhsanjaved.firebasedatabasepracticekotlin.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.muhsanjaved.firebasedatabasepracticekotlin.R
import com.muhsanjaved.firebasedatabasepracticekotlin.databinding.ActivityPhotoUploadBinding
import com.muhsanjaved.firebasedatabasepracticekotlin.databinding.ActivityVideoBinding
import com.squareup.picasso.Picasso

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    private lateinit var mediaController: MediaController
    private lateinit var progressDialog : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)

        binding.videoView.isVisible = false

        binding.btnVideoButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "video/*"
            videoLauncher.launch(intent)
        }

    }

    private val videoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {

                if (it.data != null) {
//               binding.btnVideoButton.isVisible=false
                    progressDialog.setTitle("Uploading...")
                    var storageRef = Firebase.storage.reference.child(
                        "Video/" + System.currentTimeMillis() + "." + getFileType(it.data!!.data!!)
                    )

                    storageRef.putFile(it.data!!.data!!).addOnSuccessListener {

                        storageRef.downloadUrl.addOnSuccessListener {

                            Firebase.database.reference.child("Video").push()
                                .setValue(it.toString())

                            progressDialog.dismiss()

                            Toast.makeText(this, "Video Uploading", Toast.LENGTH_SHORT).show()

                            binding.videoView.isVisible = true
//
                            mediaController = MediaController(this@VideoActivity)
                            mediaController.setAnchorView(binding.videoView)

                            binding.videoView.setMediaController(mediaController)
                            binding.videoView.setVideoURI(it)
                            binding.videoView.start()

                            binding.videoView.setOnCompletionListener {
                                storageRef.delete().addOnSuccessListener {
                                    Toast.makeText(this,"Delete", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                    }

                        .addOnProgressListener {
                            val value = (it.bytesTransferred/it.totalByteCount) * 100
                            progressDialog.setTitle("Uploaded ${value.toString()} %")
                        }

                }
            }


        }

    private fun getFileType(data: Uri?): String? {
        val mimeType = MimeTypeMap.getSingleton()
        return mimeType.getMimeTypeFromExtension(contentResolver.getType(data!!))
    }
}