package com.example.note_kotlin_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

var tenlichhoc: String =""
var thu: String =""
var tenmonhoc: String =""
var ghichu : String =""
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_lichhoc.setOnClickListener{
            val intent:Intent = Intent(this@MainActivity, Ds_lichhocActivity::class.java)
            startActivity(intent)
        }
        main_ghichu.setOnClickListener{
            val intent:Intent = Intent(this@MainActivity, GhiChuActivity::class.java)
            startActivity(intent)
        }
    }
}