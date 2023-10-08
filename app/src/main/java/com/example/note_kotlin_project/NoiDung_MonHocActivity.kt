package com.example.note_kotlin_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.FillEventHistory
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_ds_lichhoc.*
import kotlinx.android.synthetic.main.activity_ngay_hoc.*
import kotlinx.android.synthetic.main.activity_noi_dung_mon_hoc.*

class NoiDung_MonHocActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noi_dung_mon_hoc)

        var tenMon = tenmonhoc
        var tenThu = thu
        var tenLH = tenlichhoc
        ND_MH_tenmon.setText(tenMon)

        back_nd_monhoc.setOnClickListener {
            val intent: Intent = Intent(this@NoiDung_MonHocActivity, NgayHocActivity::class.java)

            startActivity(intent)
        }
        var arrayNDMH : ArrayList<NDMonHoc> = ArrayList()
        arrayNDMH.add(NDMonHoc("Bai1.1",R.drawable.icon_lich_hoc))

        lw_nd_monhoc.adapter = AdapterDS_NDMonHoc(this@NoiDung_MonHocActivity,arrayNDMH)

        lw_nd_monhoc.setOnItemClickListener { adapterView, view, i, l ->
            val intent: Intent= Intent(this@NoiDung_MonHocActivity, Chitiet_ND_MonhocActivity::class.java)
            startActivity(intent)

        }

        add_nd_monhoc.setOnClickListener {
            showPopupMenu(add_nd_monhoc)
        }

    }
    fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_chupanh, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.chupanh_item -> {
                    Toast.makeText(this@NoiDung_MonHocActivity,"Chụp ảnh",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.chonanh_item -> {
                    Toast.makeText(this@NoiDung_MonHocActivity,"Chọn ảnh",Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}