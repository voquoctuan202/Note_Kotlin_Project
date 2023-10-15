package com.example.note_kotlin_project.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.note_kotlin_project.R
import kotlinx.android.synthetic.main.activity_noi_dung_ghi_chu.*

class NoiDung_GhiChuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noi_dung_ghi_chu)

        var tenGC  = ghichu
        edt_ten_nd_ghichu.setText(tenGC)

        back_nd_ghichu.setOnClickListener {
            val intent: Intent = Intent(this@NoiDung_GhiChuActivity, GhiChuActivity::class.java)
            startActivity(intent)
        }

    }
}