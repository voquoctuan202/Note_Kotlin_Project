package com.example.note_kotlin_project

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_ds_lichhoc.*
import kotlinx.android.synthetic.main.dong_ds_lichhoc.*

class Ds_lichhocActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ds_lichhoc)
        var arrayLH : ArrayList<TenLichHoc> = ArrayList()
        arrayLH.add(TenLichHoc("Hocki1"))
        arrayLH.add(TenLichHoc("Hocki2"))

        lw_ds_lichhoc.adapter = AdapterDS_Lichhoc<Any>(this@Ds_lichhocActivity,arrayLH)

        back_ds_lichhoc.setOnClickListener {
            val intent: Intent = Intent(this@Ds_lichhocActivity, MainActivity::class.java)
            startActivity(intent)
        }

        lw_ds_lichhoc.setOnItemClickListener { adapterView, view, i, l ->
            val intent: Intent = Intent(this@Ds_lichhocActivity, LichHocActivity::class.java)
            tenlichhoc = arrayLH.get(i).tenLH
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
            items.mangLH[position].tenLH = newName
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun XoaLH(position: Int) {
        val items = lw_ds_lichhoc.adapter as AdapterDS_Lichhoc<TenLichHoc>
        val itemName = items.getItem(position)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xác nhận ")
        builder.setMessage("Bạn có chắc muốn xóa lịch học này?")

        builder.setPositiveButton("Có") { dialog: DialogInterface, which: Int ->
            items.mangLH.remove(itemName)
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

        val input = EditText(this)
        input.setHint("Nhập tên của lịch học mới")
        builder.setView(input)

        builder.setPositiveButton("Thêm") { dialog: DialogInterface, which: Int ->
            val newItemName = input.text.toString()
            if (newItemName.isNotBlank()) {
                val items = lw_ds_lichhoc.adapter as AdapterDS_Lichhoc<TenLichHoc>
                items.mangLH.add(TenLichHoc(newItemName))
                items.notifyDataSetChanged()
            }
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
}



