package com.example.note_kotlin_project.activity



import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.adapter.AdapterDS_NDMonHoc
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.NDMonHoc
import kotlinx.android.synthetic.main.activity_noi_dung_mon_hoc.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
var hinh_uri : String =""
class NoiDung_MonHocActivity : AppCompatActivity() {
    val sql: SQLiteHelper = SQLiteHelper(this@NoiDung_MonHocActivity)
    private val IMAGE_CAPTURE_CORE: Int= 1001
    private val PERMISSION_CODE: Int =1000
    var image_uri: Uri? = null
    val PICK_IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noi_dung_mon_hoc)


        var tenMon = tenmonhoc
        ND_MH_tenmon.setText(tenMon)

        back_nd_monhoc.setOnClickListener {
            val intent: Intent = Intent(this@NoiDung_MonHocActivity, NgayHocActivity::class.java)

            startActivity(intent)
        }
        var arrayNDMH : ArrayList<NDMonHoc> = ArrayList()
        //arrayNDMH.add(NDMonHoc("Bai1.1",R.drawable.icon_lich_hoc))
        arrayNDMH = sql.getAllNDMonHoc(idMonHoc)
        lw_nd_monhoc.adapter = AdapterDS_NDMonHoc<NDMonHoc>(this@NoiDung_MonHocActivity,arrayNDMH)

        lw_nd_monhoc.setOnItemClickListener { adapterView, view, i, l ->
            val intent: Intent= Intent(this@NoiDung_MonHocActivity, Chitiet_ND_MonhocActivity::class.java)

            if (arrayNDMH[i].hinh_NDMonHoc != null){
                    hinh_uri= arrayNDMH[i].hinh_NDMonHoc

            }

            startActivity(intent)

        }

        add_nd_monhoc.setOnClickListener {
            showPopupMenu(add_nd_monhoc)
        }


    registerForContextMenu(lw_nd_monhoc)


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
                doiTenNDMH(selectedItemPosition)
                return true
            }
            R.id.xoa_item -> {
                XoaNDMH(selectedItemPosition)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }
    private fun doiTenNDMH(position: Int) {
        val items = lw_nd_monhoc.adapter as AdapterDS_NDMonHoc<NDMonHoc>

        val builder = AlertDialog.Builder(this@NoiDung_MonHocActivity)
        builder.setTitle("Đổi tên ")

        val input = EditText(this@NoiDung_MonHocActivity)
        input.setHint("Nhập tên mới")
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
            val newName = input.text.toString()
            sql.updateMonHoc(items.mangNDMH.get(position).id,newName)
            items.mangNDMH =sql.getAllNDMonHoc(idMonHoc)
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun XoaNDMH(position: Int) {
        val items = lw_nd_monhoc.adapter as AdapterDS_NDMonHoc<NDMonHoc>
        val itemName = items.getItem(position)

        val builder = AlertDialog.Builder(this@NoiDung_MonHocActivity)
        builder.setTitle("Xác nhận ")
        builder.setMessage("Bạn có chắc muốn xóa lịch học này?")

        builder.setPositiveButton("Có") { dialog: DialogInterface, which: Int ->
            sql.deleteMonHoc(items.mangNDMH.get(position).id)

            items.mangNDMH = sql.getAllNDMonHoc(idMonHoc)
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Không") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun themNDMH(img: Uri?) {
        val builder = AlertDialog.Builder(this@NoiDung_MonHocActivity)
        builder.setTitle("Thêm nội dung mới")

        val input = EditText(this@NoiDung_MonHocActivity)
        input.setHint("Nhập tên của nội dung mới")
        builder.setView(input)

        builder.setPositiveButton("Thêm") { dialog: DialogInterface, which: Int ->
            val newTieude = input.text.toString()
            if (newTieude.isNotBlank()) {
                val items = lw_nd_monhoc.adapter as AdapterDS_NDMonHoc<NDMonHoc>
                sql.addNDMonHoc(idMonHoc,newTieude,"",img.toString())

                items.mangNDMH = sql.getAllNDMonHoc(idMonHoc)
                items.notifyDataSetChanged()
            }
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_chupanh, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.chupanh_item -> {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (checkSelfPermission(android.Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                                val permission = arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                requestPermissions(permission,PERMISSION_CODE)

                        }else{
                                openCamera()
                        }
                    }else{

                    }
                    Toast.makeText(this@NoiDung_MonHocActivity,"Chụp ảnh",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.chonanh_item -> {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, PICK_IMAGE_REQUEST)
                    Toast.makeText(this@NoiDung_MonHocActivity,"Chọn ảnh",Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri)
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CORE)
    }

    override fun onRequestPermissionsResult( requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE ->{
                if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    Toast.makeText(this@NoiDung_MonHocActivity,"Permission denied",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode != PICK_IMAGE_REQUEST){

            themNDMH(image_uri)


        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data

            themNDMH(selectedImageUri)
            Toast.makeText(this@NoiDung_MonHocActivity,"Chọn ảnh",Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadBitmapFromUri(context: Context, uri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

}