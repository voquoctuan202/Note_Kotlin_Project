package com.example.note_kotlin_project.activity

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.adapter.AdapterDS_Ghichu
import com.example.note_kotlin_project.adapter.AdapterDS_ThongBao
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.GhiChu
import com.example.note_kotlin_project.dataclass.ThongBao
import kotlinx.android.synthetic.main.activity_ghi_chu.*
import kotlinx.android.synthetic.main.activity_thong_bao.*
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ThongBaoActivity : AppCompatActivity() {
    val sql: SQLiteHelper = SQLiteHelper(this@ThongBaoActivity)
    var arrayTB : ArrayList<ThongBao> = ArrayList()
    val cal = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val currentDate = dateFormat.format(cal)
    var trangthai:Int =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_bao)

        back_thongbao.setOnClickListener {
            val intent: Intent = Intent(this@ThongBaoActivity, MainActivity::class.java)
            startActivity(intent)
        }
        var arrayTB : ArrayList<ThongBao> = ArrayList()
        arrayTB = sql.getAllThongBao()
        tieude_thongbao.setText("Tất cả thông báo")
        lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)

        menu_thongbao.setOnClickListener {
            showPopupMenu(menu_thongbao)
        }
        registerForContextMenu(lw_ds_thongbao)
    }
    fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_thongbao, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.tatca_item -> {
                    arrayTB = getAllThongBao()
                    trangthai=1
                    tieude_thongbao.setText("Tất cả thông báo")
                    lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
                    true
                }
                R.id.homnay_item -> {
                    arrayTB = getThongBaoHomNay(currentDate)
                    trangthai=2
                    tieude_thongbao.setText("Thông báo hôm nay")
                    lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
                    true
                }
                R.id.chuaden_item-> {
                    arrayTB = getThongBaoChuaDen(currentDate.toString())
                    trangthai=3
                    tieude_thongbao.setText("Thông báo chưa đến")
                    lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
                    true
                }
                R.id.daqua_item-> {
                    arrayTB = getThongBaoDaQua(currentDate)
                    trangthai=4
                    tieude_thongbao.setText("Thông báo đã qua")
                    lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
    fun getAllThongBao():ArrayList<ThongBao> {
        return sql.getAllThongBao()
    }
    fun getThongBaoHomNay(today: String):ArrayList<ThongBao>{
        return sql.getAllThongBaoToDay(today)
    }
    fun getThongBaoChuaDen(today: String):ArrayList<ThongBao>{
        var arrayTemp: ArrayList<ThongBao> = ArrayList()
        var arrayTBCD: ArrayList<ThongBao> = ArrayList()

        arrayTemp = sql.getAllThongBao()
        for(i in 0..arrayTemp.size-1){

            if(compareDate(today,arrayTemp[i].ngayTB)<0){
                arrayTBCD.add(arrayTemp[i])
            }
        }
        return arrayTBCD

    }
    fun getThongBaoDaQua(today: String):ArrayList<ThongBao>{
        var arrayTemp: ArrayList<ThongBao> = ArrayList()
        var arrayTBDQ: ArrayList<ThongBao> = ArrayList()

        arrayTemp = sql.getAllThongBao()
        for(i in 0..arrayTemp.size-1){
            if(compareDate(today,arrayTemp[i].ngayTB)>0){
                arrayTBDQ.add(arrayTemp[i])
            }
        }
        return arrayTBDQ
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_lw_thongbao, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedItemPosition = info.position // Đây là vị trí của item trong danh sách

        when (item.itemId) {
            R.id.xoa_item-> {
                XoaTB(selectedItemPosition)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }
    fun XoaTB(position: Int){
        val items = lw_ds_thongbao.adapter as AdapterDS_ThongBao<ThongBao>
        val itemName = items.getItem(position)

        val builder = AlertDialog.Builder(this@ThongBaoActivity)
        builder.setTitle("Xác nhận ")
        builder.setMessage("Bạn có chắc muốn xóa thông báo này?")

        builder.setPositiveButton("Có") { dialog: DialogInterface, which: Int ->
            sql.deleteThongBao(items.mangTB.get(position).id)

            when (trangthai) {
                1 -> {
                    arrayTB = getAllThongBao()
                    lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
                }
                2 -> {
                    arrayTB = getThongBaoHomNay(currentDate)
                    lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
                }
                3 -> {
                    arrayTB = getThongBaoChuaDen(currentDate.toString())
                    lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
                }
                else -> {
                    arrayTB = getThongBaoDaQua(currentDate)
                    lw_ds_thongbao.adapter = AdapterDS_ThongBao<Any>(this@ThongBaoActivity,arrayTB)
                }
            }
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Không") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }

    fun compareDate(date1: String, date2: String): Int {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date1 = dateFormat.parse(date1)
        val date2 = dateFormat.parse(date2)

        return date1.compareTo(date2)
    }

}