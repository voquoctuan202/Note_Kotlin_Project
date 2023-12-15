package com.example.note_kotlin_project.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.adapter.AdapterDS_Monhoc
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.MonHoc
import kotlinx.android.synthetic.main.activity_ngay_hoc.*

class NgayHocActivity : AppCompatActivity() {
    val sql: SQLiteHelper = SQLiteHelper(this@NgayHocActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngay_hoc)


        if(thu == 8 ){
            textNgayhoc.setText("Khác ")
        }else{
            textNgayhoc.setText("Thứ "+ thu)
        }

        var arrayMH : ArrayList<MonHoc> = ArrayList()
        //arrayMH.add(MonHoc("Lâp trình Android"))

        arrayMH = sql.getAllMonHoc(idLichHoc,thu)
        Log.d("AAA","Chạy getall Mon OK ")
        lw_ds_monhoc.adapter = AdapterDS_Monhoc<Any>(this@NgayHocActivity,arrayMH)

        lw_ds_monhoc.setOnItemClickListener { adapterView, view, i, l ->
            val intent: Intent= Intent(this@NgayHocActivity, NoiDung_MonHocActivity::class.java)
            arrayMH = sql.getAllMonHoc(idLichHoc,thu)
            tenmonhoc = arrayMH[i].tenMH
            idMonHoc = arrayMH[i].id
            startActivity(intent)
        }
        back_home_nh.setOnClickListener {
            val intent: Intent= Intent(this@NgayHocActivity, MainActivity::class.java)
            startActivity(intent)
        }
        back_ngayhoc.setOnClickListener {
            val intent: Intent= Intent(this@NgayHocActivity, LichHocActivity::class.java)
            startActivity(intent)
        }
        registerForContextMenu(lw_ds_monhoc)
        add_monhoc.setOnClickListener {
            themMH()
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
                doiTenMH(selectedItemPosition)
                return true
            }
            R.id.xoa_item -> {
                XoaMH(selectedItemPosition)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }
    private fun doiTenMH(position: Int) {
        val items = lw_ds_monhoc.adapter as AdapterDS_Monhoc<MonHoc>

        val builder = AlertDialog.Builder(this@NgayHocActivity)
        builder.setTitle("Đổi tên ")

        val input = EditText(this@NgayHocActivity)
        input.setHint("Nhập tên mới")
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
            val newName = input.text.toString()
            sql.updateMonHoc(items.mangMH.get(position).id,newName)
            items.mangMH =sql.getAllMonHoc(idLichHoc,thu)
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun XoaMH(position: Int) {
        val items = lw_ds_monhoc.adapter as AdapterDS_Monhoc<MonHoc>
        val itemName = items.getItem(position)

        val builder = AlertDialog.Builder(this@NgayHocActivity)
        builder.setTitle("Xác nhận ")
        builder.setMessage("Bạn có chắc muốn xóa lịch học này?")

        builder.setPositiveButton("Có") { dialog: DialogInterface, which: Int ->
            sql.deleteMonHoc(items.mangMH.get(position).id)

            items.mangMH = sql.getAllMonHoc(idLichHoc,thu)
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Không") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun themMH() {
        val builder = AlertDialog.Builder(this@NgayHocActivity)
        builder.setTitle("Thêm lịch học mới")

        val input = EditText(this@NgayHocActivity)
        input.setHint("Nhập tên của môn hoc mới")
        builder.setView(input)

        builder.setPositiveButton("Thêm") { dialog: DialogInterface, which: Int ->
            val newNameMonHoc = input.text.toString()
            if (newNameMonHoc.isNotBlank()) {
                val items = lw_ds_monhoc.adapter as AdapterDS_Monhoc<MonHoc>
                sql.addMonHoc(idLichHoc,newNameMonHoc, thu)

                items.mangMH = sql.getAllMonHoc(idLichHoc,thu)

                items.notifyDataSetChanged()
            }
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
}