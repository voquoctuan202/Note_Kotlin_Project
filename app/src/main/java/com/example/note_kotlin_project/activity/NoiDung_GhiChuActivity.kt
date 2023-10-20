package com.example.note_kotlin_project.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.GhiChu
import kotlinx.android.synthetic.main.activity_noi_dung_ghi_chu.*

class NoiDung_GhiChuActivity : AppCompatActivity() {
    val sql: SQLiteHelper = SQLiteHelper(this@NoiDung_GhiChuActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noi_dung_ghi_chu)

        var ghiChu: GhiChu? = sql.getGhiChuByID(idGhiChu)
        if( ghiChu != null){
            edt_ten_nd_ghichu.setText(ghiChu.tenGC)
            edt_text_nd_ghichu.setText(ghiChu.ndGC)
            ngay_nd_ghichu.setText(ghiChu.ngay)
        }
        save_nd_ghichu.setOnClickListener {
            var tieude= edt_ten_nd_ghichu.text.toString()
            var noidung = edt_text_nd_ghichu.text.toString()
            sql.updateGhiChu(idGhiChu,tieude,noidung)
            Toast.makeText(this,"Đã lưu",Toast.LENGTH_SHORT).show()
        }


        back_nd_ghichu.setOnClickListener {
            val intent: Intent = Intent(this@NoiDung_GhiChuActivity, GhiChuActivity::class.java)
            startActivity(intent)
        }

    }
}