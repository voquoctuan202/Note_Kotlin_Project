package com.example.note_kotlin_project.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.adapter.AdapterDS_Ghichu
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.GhiChu
import kotlinx.android.synthetic.main.activity_ghi_chu.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
var idGhiChu : Int =0
class GhiChuActivity : AppCompatActivity() {
    val sql: SQLiteHelper = SQLiteHelper(this@GhiChuActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ghi_chu)
        var arrayGC : ArrayList<GhiChu> = ArrayList()
        arrayGC = sql.getAllGhiChu()
        lw_ghichu.adapter = AdapterDS_Ghichu<Any>(this@GhiChuActivity,arrayGC)

        lw_ghichu.setOnItemClickListener { adapterView, view, i, l ->
            val intent: Intent = Intent(this@GhiChuActivity, NoiDung_GhiChuActivity::class.java)
            arrayGC = sql.getAllGhiChu()
            idGhiChu = arrayGC[i].id
            startActivity(intent)

        }

        back_ghichu.setOnClickListener {
            val intent: Intent = Intent(this@GhiChuActivity, MainActivity::class.java)
            startActivity(intent)
        }
        registerForContextMenu(lw_ghichu)
        add_ghichu.setOnClickListener {
            themGC()
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
                doiTenGC(selectedItemPosition)
                return true
            }
            R.id.xoa_item -> {
                XoaGC(selectedItemPosition)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }
    private fun doiTenGC(position: Int) {
        val items = lw_ghichu.adapter as AdapterDS_Ghichu<GhiChu>

        val builder = AlertDialog.Builder(this@GhiChuActivity)
        builder.setTitle("Đổi tên")

        val input = EditText(this@GhiChuActivity)
        input.setHint("Nhập tên ghi chú mới")
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
            val newName = input.text.toString()
            sql.updateNameGhiChu(items.mangGC.get(position).id,newName)
            items.mangGC =sql.getAllGhiChu()
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun XoaGC(position: Int) {
        val items = lw_ghichu.adapter as AdapterDS_Ghichu<GhiChu>
        val itemName = items.getItem(position)

        val builder = AlertDialog.Builder(this@GhiChuActivity)
        builder.setTitle("Xác nhận ")
        builder.setMessage("Bạn có chắc muốn xóa ghi chú này?")

        builder.setPositiveButton("Có") { dialog: DialogInterface, which: Int ->
            sql.deleteGhiChu(items.mangGC.get(position).id)

            items.mangGC = sql.getAllGhiChu()
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Không") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun themGC() {
        val builder = AlertDialog.Builder(this@GhiChuActivity)
        builder.setTitle("Thêm ghi chú mới")

        val input = EditText(this@GhiChuActivity)
        input.setHint("Nhập tên của ghi chú mới")
        builder.setView(input)

        builder.setPositiveButton("Thêm") { dialog: DialogInterface, which: Int ->
            val newItemName = input.text.toString()
            if (newItemName.isNotBlank()) {
                val items = lw_ghichu.adapter as AdapterDS_Ghichu<GhiChu>
                //items.mangGC.add(GhiChu(newItemName))
                val cal = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val formattedDate = dateFormat.format(cal)

                sql.addGhiChu(newItemName,formattedDate)
                items.mangGC = sql.getAllGhiChu()
                items.notifyDataSetChanged()
            }
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
}