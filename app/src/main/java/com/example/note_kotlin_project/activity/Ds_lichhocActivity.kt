package com.example.note_kotlin_project.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.adapter.AdapterDS_Lichhoc
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.TenLichHoc
import kotlinx.android.synthetic.main.activity_ds_lichhoc.*
import java.text.SimpleDateFormat
import java.util.*


class Ds_lichhocActivity : AppCompatActivity() {
    val sql: SQLiteHelper = SQLiteHelper(this@Ds_lichhocActivity)
    var arrayLH : ArrayList<TenLichHoc> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ds_lichhoc)


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
        menuInflater.inflate(R.menu.menu_ds_lichhoc, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedItemPosition = info.position // Đây là vị trí của item trong danh sách

        when (item.itemId) {
            R.id.truycapnhanh_item -> {
                Toast.makeText(this, "Thiết lập truy cập nhanh "+ arrayLH[selectedItemPosition].tenLH,Toast.LENGTH_SHORT).show()
                save_truycapnhanh(arrayLH[selectedItemPosition].id)

                return true
            }
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

                arrayLH= sql.getAllLichHoc()
                lw_ds_lichhoc.adapter = AdapterDS_Lichhoc<Any>(this@Ds_lichhocActivity,arrayLH)
                items.notifyDataSetChanged()
            }
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    fun save_truycapnhanh(id : Int){
        val sharedPreferences = getSharedPreferences("id_lichhoc", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("id_lichhoc", id)
        editor.apply()
    }


}



