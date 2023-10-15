package com.example.note_kotlin_project.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.note_kotlin_project.R
import kotlinx.android.synthetic.main.activity_lich_hoc.*
import kotlinx.android.synthetic.main.activity_ngay_hoc.*

class LichHocActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lich_hoc)

        var tenLH = tenlichhoc
        textTenLH.setText(tenLH)
        back_home_lh.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, MainActivity::class.java)
            startActivity(intent)
        }

        back_Lichhoc.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, Ds_lichhocActivity::class.java)
            startActivity(intent)
        }
        lichhocT2.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, NgayHocActivity::class.java)
            thu =2
            startActivity(intent)
        }
        lichhocT3.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, NgayHocActivity::class.java)

            thu =3
            startActivity(intent)
        }
        lichhocT4.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, NgayHocActivity::class.java)

           thu =4
            startActivity(intent)
        }
        lichhocT5.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, NgayHocActivity::class.java)
            thu =5
            startActivity(intent)
        }
        lichhocT6.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, NgayHocActivity::class.java)
            thu =6
            startActivity(intent)
        }
        lichhocT7.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, NgayHocActivity::class.java)
            thu =7
            startActivity(intent)
        }
        lichhocKhac.setOnClickListener {
            val intent: Intent = Intent(this@LichHocActivity, NgayHocActivity::class.java)
            thu =8
            startActivity(intent)
        }

    }
}