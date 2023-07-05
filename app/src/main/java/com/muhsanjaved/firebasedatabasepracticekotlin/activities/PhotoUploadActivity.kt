package com.muhsanjaved.firebasedatabasepracticekotlin.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.muhsanjaved.firebasedatabasepracticekotlin.databinding.ActivityPhotoUploadBinding
import com.squareup.picasso.Picasso

class PhotoUploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoUploadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadImageButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            imageLauncher.launch(intent)
        }

    }

    val imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {

                if (it.data != null) {
                    var storageRef = Firebase.storage.reference.child("Photo/"+System.currentTimeMillis()+"."+getFileType(it.data!!.data!!))

                    storageRef.putFile(it.data!!.data!!).addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener {

                            Firebase.database.reference.child("Photo").push().setValue(it.toString())

//                            binding.imageView.setImageURI(it)
                            Toast.makeText(this@PhotoUploadActivity, "Photo Uploading", Toast.LENGTH_SHORT).show()
                            Picasso.get().load(it.toString()).into(binding.imageView)
                        }
                    }
                }
            }


    }

    private fun getFileType(data: Uri?): String? {
        val mimeType = MimeTypeMap.getSingleton()
        return mimeType.getMimeTypeFromExtension(contentResolver.getType(data!!))
    }
}