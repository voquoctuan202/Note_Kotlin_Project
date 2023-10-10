package com.example.note_kotlin_project



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
import kotlinx.android.synthetic.main.activity_noi_dung_mon_hoc.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoiDung_MonHocActivity : AppCompatActivity() {

    private val IMAGE_CAPTURE_CORE: Int= 1001
    private val PERMISSION_CODE: Int =1000
    var image_uri: Uri? = null
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
        //arrayNDMH.add(NDMonHoc("Bai1.1",R.drawable.icon_lich_hoc))

        lw_nd_monhoc.adapter = AdapterDS_NDMonHoc<NDMonHoc>(this@NoiDung_MonHocActivity,arrayNDMH)

        lw_nd_monhoc.setOnItemClickListener { adapterView, view, i, l ->
            val intent: Intent= Intent(this@NoiDung_MonHocActivity, Chitiet_ND_MonhocActivity::class.java)

            intent.data = image_uri
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
            items.mangNDMH[position].tenNDMonHoc = newName
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Hủy") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun XoaNDMH(position: Int) {
        val items = lw_nd_monhoc.adapter as  AdapterDS_NDMonHoc<NDMonHoc>
        val itemName = items.getItem(position)

        val builder = AlertDialog.Builder(this@NoiDung_MonHocActivity)
        builder.setTitle("Xác nhận ")
        builder.setMessage("Bạn có chắc muốn xóa lịch học này?")

        builder.setPositiveButton("Có") { dialog: DialogInterface, which: Int ->
            items.mangNDMH.remove(itemName)
            items.notifyDataSetChanged()
        }

        builder.setNegativeButton("Không") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun themNDMH(img: Bitmap?) {
        val builder = AlertDialog.Builder(this@NoiDung_MonHocActivity)
        builder.setTitle("Thêm nội dung mới")

        val input = EditText(this@NoiDung_MonHocActivity)
        input.setHint("Nhập tên của nội dung mới")
        builder.setView(input)

        builder.setPositiveButton("Thêm") { dialog: DialogInterface, which: Int ->
            val newItemName = input.text.toString()
            if (newItemName.isNotBlank()) {
                val items = lw_nd_monhoc.adapter as AdapterDS_NDMonHoc<NDMonHoc>
                items.mangNDMH.add(NDMonHoc(newItemName,img))
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
        if (resultCode== Activity.RESULT_OK){
            var hinh: Bitmap? = loadBitmapFromUri(this@NoiDung_MonHocActivity,image_uri)
            themNDMH(hinh)
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
    fun saveBitmapToStorage(context: Context, bitmap: Bitmap?): String? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "IMG_$timeStamp.jpg"

        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, imageFileName)

        return try {
            val outputStream = FileOutputStream(imageFile)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            imageFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}