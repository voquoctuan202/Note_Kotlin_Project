package com.example.note_kotlin_project.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.database.SQLiteHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*


var tenlichhoc: String =""
var idLichHoc : Int =0
var idMonHoc : Int = 0
var thu: Int =0
var tenmonhoc: String =""
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
        if (getID_truycapnhanh()==-1){

            main_truycapnhanh.setOnClickListener {
                Toast.makeText(this,"Bạn chưa thiết lập truy cập nhanh", Toast.LENGTH_SHORT).show()

            }
        }else{

            main_truycapnhanh.setOnClickListener {
                val intent: Intent = Intent(this@MainActivity, NgayHocActivity::class.java)
                thu = getDayOfWeekNumber()
                idLichHoc = getID_truycapnhanh()
                startActivity(intent)

            }

        }
        main_thongbao.setOnClickListener {
            val intent:Intent = Intent(this@MainActivity, ThongBaoActivity::class.java)
            startActivity(intent)
        }


    }
    fun getID_truycapnhanh():Int{
        val sharedPreferences = getSharedPreferences("id_lichhoc", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id_lichhoc", -1)
        return id
    }
    fun getDayOfWeekNumber(): Int {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Chuyển đổi từ thứ hai - thứ bảy sang số thứ tự
        val dayNumber = if (dayOfWeek == Calendar.SUNDAY) {
            8 // Chủ Nhật
        } else {
            dayOfWeek
        }
        return dayNumber
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