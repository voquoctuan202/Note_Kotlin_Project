package com.example.note_kotlin_project.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.adapter.AdapterDS_Lichhoc
import com.example.note_kotlin_project.adapter.AdapterDS_ThongBao
import com.example.note_kotlin_project.dataclass.TenLichHoc
import com.example.note_kotlin_project.dataclass.ThongBao
import kotlinx.android.synthetic.main.activity_ds_lichhoc.*
import kotlinx.android.synthetic.main.activity_thong_bao.*
import java.util.ArrayList

class ThongBaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_bao)

        back_thongbao.setOnClickListener {
            val intent: Intent = Intent(this@ThongBaoActivity, MainActivity::class.java)
            startActivity(intent)
        }
        var arrayTB : ArrayList<ThongBao> = ArrayList()
        arrayTB.add(ThongBao(1,"Bai1","30/10/2023",1,1))
        lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
    }
}