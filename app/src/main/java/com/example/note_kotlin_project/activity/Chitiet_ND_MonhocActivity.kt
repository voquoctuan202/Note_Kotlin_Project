package com.example.note_kotlin_project.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.note_kotlin_project.R
import kotlinx.android.synthetic.main.activity_chitiet_nd_monhoc.*

class Chitiet_ND_MonhocActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chitiet_nd_monhoc)


        hinh_chitiet_monhoc.setImageURI(Uri.parse(hinh_uri))
        hinh_chitiet_monhoc.setOnClickListener {
            val intent: Intent = Intent(this@Chitiet_ND_MonhocActivity, PhotoView_CTMHActivity::class.java)
            startActivity(intent)
        }
        back_chitiet_ndmh.setOnClickListener {
            val intent: Intent= Intent(this@Chitiet_ND_MonhocActivity, NoiDung_MonHocActivity::class.java)
            startActivity(intent)
        }
    }
}




