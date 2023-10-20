package com.example.note_kotlin_project.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.NDMonHoc
import kotlinx.android.synthetic.main.activity_chitiet_nd_monhoc.*

class Chitiet_ND_MonhocActivity : AppCompatActivity() {
    val sql: SQLiteHelper = SQLiteHelper(this@Chitiet_ND_MonhocActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chitiet_nd_monhoc)

        var nd: NDMonHoc? =  sql.getNDMonHocByID(id_ndmh, idMonHoc)
        if (nd!=null){
            hinh_chitiet_monhoc.setImageURI(Uri.parse(nd.hinh_NDMonHoc))
            chitiet_ten_ndmh.setText(nd.tieudeNDMonHoc)
            chitiet_ndmh.setText(nd.noidungMonHoc)
        }

        save_chitiet_ndmh.setOnClickListener {
            var tieude = chitiet_ten_ndmh.text.toString()
            var noidung = chitiet_ndmh.text.toString()
            sql.updateNDMonHoc(id_ndmh,tieude,noidung)
            Toast.makeText(this,"Đã lưu",Toast.LENGTH_SHORT).show()
        }

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




