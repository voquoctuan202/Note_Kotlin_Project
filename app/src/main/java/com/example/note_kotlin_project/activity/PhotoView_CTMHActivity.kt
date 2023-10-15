package com.example.note_kotlin_project.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.note_kotlin_project.R
import kotlinx.android.synthetic.main.activity_photo_view_ctmhactivity.*

class PhotoView_CTMHActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view_ctmhactivity)

        photoview_chitiet_ndmh.setImageURI(Uri.parse(hinh_uri))
        back_photo_chitiet_ndmh.setOnClickListener {
            val intent: Intent = Intent(this@PhotoView_CTMHActivity, Chitiet_ND_MonhocActivity::class.java)
            startActivity(intent)
        }
    }


}