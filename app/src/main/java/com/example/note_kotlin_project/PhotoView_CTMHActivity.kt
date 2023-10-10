package com.example.note_kotlin_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chitiet_nd_monhoc.*
import kotlinx.android.synthetic.main.activity_photo_view_ctmhactivity.*

class PhotoView_CTMHActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view_ctmhactivity)

        back_photo_chitiet_ndmh.setOnClickListener {
            val intent: Intent = Intent(this@PhotoView_CTMHActivity, Chitiet_ND_MonhocActivity::class.java)
            startActivity(intent)
        }
    }


}