package com.example.note_kotlin_project.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.adapter.AdapterDS_Lichhoc
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.TenLichHoc

import kotlinx.android.synthetic.main.activity_ds_lichhoc.*
import java.io.File

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Ds_lichhocActivity : AppCompatActivity() {
    val sql: SQLiteHelper = SQLiteHelper(this@Ds_lichhocActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ds_lichhoc)
        var arrayLH : ArrayList<TenLichHoc> = ArrayList()

        arrayLH= sql.getAllLichHoc()

        lw_ds_lichhoc.adapter = AdapterDS_Lichhoc<Any>(this@Ds_lichhocActivity,arrayLH)

        back_ds_lichhoc.setOnClickListener {
            val intent: Intent = Intent(this@Ds_lichhocActivity, MainActivity::class.java)
            startActivity(intent)
        }

        lw_ds_lichhoc.setOnItemClickListener { adapterView, view, i, l ->
            val intent: Intent = Intent(this@Ds_lichhocActivity, LichHocActivity::class.java)
            arrayLH = sql.getAllLichHoc()
            tenlichhoc = arrayLH.get(i).tenLH
            idLichHoc = arrayLH.get(i).id
            startActivity(intent)
        }

        registerForContextMenu(lw_ds_lichhoc)
        add_dslichhoc.setOnClickListener {
            addLH()
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_item, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedItemPosition = info.position // Đây là vị trí của item trong danh sách

        when (item.itemId) {
            R.id.doiten_item -> {
                doiTenLH(selectedItemPosition)
                return true
            }
            R.id.xoa_item -> {
                XoaLH(selectedItemPosition)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }
    private fun doiTenLH(position: Int) {
        val items = lw_ds_lichhoc.adapter as AdapterDS_Lichhoc<TenLichHoc>

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Đổi tên")

        val input = EditText(this)
        input.setHint("Nhập tên mới")
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
            val newName = input.text.toString()
            sql.updateLichHoc(items.mangLH.get(position).id,newName)
            items.mangLH =sql.getAllLichHoc()
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun XoaLH(position: Int) {
        val items = lw_ds_lichhoc.adapter as AdapterDS_Lichhoc<TenLichHoc>

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xác nhận ")
        builder.setMessage("Bạn có chắc muốn xóa lịch học này?")

        builder.setPositiveButton("Có") { dialog: DialogInterface, which: Int ->

            sql.deleteLichHoc(items.mangLH.get(position).id)

            items.mangLH = sql.getAllLichHoc()
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Không") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun addLH() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thêm lịch học mới")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val input = EditText(this)
        input.setHint("Nhập tên của lịch học mới")

        layout.addView(input)
        builder.setView(layout)

        builder.setPositiveButton("Thêm") { dialog: DialogInterface, which: Int ->
            val newName = input.text.toString()
            if (newName.isNotBlank()) {
                val items = lw_ds_lichhoc.adapter as AdapterDS_Lichhoc<TenLichHoc>
                val cal = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val formattedDate = dateFormat.format(cal)
                sql.addLichHoc(newName,formattedDate)

                items.mangLH = sql.getAllLichHoc()

                items.notifyDataSetChanged()
            }
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }


}



