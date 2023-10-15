package com.example.note_kotlin_project.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.database.SQLiteHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

var tenlichhoc: String =""
var idLichHoc : Int =0
var idMonHoc : Int = 0
var thu: Int =0
var tenmonhoc: String =""
var ghichu : String =""
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sql: SQLiteHelper= SQLiteHelper(this)
        //deleteDatabase(this)
        main_lichhoc.setOnClickListener{
            val intent:Intent = Intent(this@MainActivity, Ds_lichhocActivity::class.java)
            startActivity(intent)
        }
        main_ghichu.setOnClickListener{
            val intent:Intent = Intent(this@MainActivity, GhiChuActivity::class.java)
            startActivity(intent)
        }
    }
    fun deleteDatabase(context: Context) {
        // Đặt tên cơ sở dữ liệu của bạn
        val dbName = "note_kotlin.db"

        // Đường dẫn đến tệp cơ sở dữ liệu
        val dbPath = context.getDatabasePath(dbName).path

        // Xóa tệp cơ sở dữ liệu nếu tồn tại
        val dbFile = File(dbPath)
        if (dbFile.exists()) {
            dbFile.delete()
        }
    }
}