package com.example.note_kotlin_project.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.note_kotlin_project.activity.NoiDung_MonHocActivity
import com.example.note_kotlin_project.dataclass.*

class SQLiteHelper(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME,null, DATABASE_VERSION
){
    companion object{
        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME= "note_kotlin.db"

    }

    override fun onCreate(p0: SQLiteDatabase) {
        val createTenLichHocTableSQL = "CREATE TABLE TENLICHHOC (id INTEGER PRIMARY KEY, tenLH TEXT,ngayLH TEXT);"
        p0.execSQL(createTenLichHocTableSQL)

        val createMonHocTableSQL = "CREATE TABLE MONHOC (id INTEGER PRIMARY KEY,idLH INTERGER ,tenMon TEXT,thu Interger);"
        p0.execSQL(createMonHocTableSQL)

        val createNDMonHocTableSQL = "CREATE TABLE NOIDUNGMONHOC (id INTEGER PRIMARY KEY,idMH INTERGER ,tieude TEXT,noidung TEXT,ngay TEXT,hinhanh TEXT);"
        p0.execSQL(createNDMonHocTableSQL)

        val createGhiChuTableSQL = "CREATE TABLE GHICHU (id INTEGER PRIMARY KEY ,tieude TEXT,noidung TEXT,ngay TEXT);"
        p0.execSQL(createGhiChuTableSQL)

        val createThongBaoTableSQL = "CREATE TABLE THONGBAO (id INTEGER PRIMARY KEY ,ten TEXT,ngay TEXT,doituong INTERGER, loai INTERGER);"
        p0.execSQL(createThongBaoTableSQL)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

        val dropTenLichHocTableSQL = "DROP TABLE IF EXISTS TENLICHHOC;"
        p0.execSQL( dropTenLichHocTableSQL)

        val dropMonHocTableSQL = "DROP TABLE IF EXISTS MONHOC;"
        p0.execSQL(dropMonHocTableSQL)

        val dropNDMonHocTableSQL = "DROP TABLE IF EXISTS NOIDUNGMONHOC;"
        p0.execSQL(dropNDMonHocTableSQL)

        val dropGhiChuTableSQL = "DROP TABLE IF EXISTS GHICHU;"
        p0.execSQL(dropGhiChuTableSQL)

        val dropThongBaoTableSQL = "DROP TABLE IF EXISTS THONGBAO;"
        p0.execSQL(dropThongBaoTableSQL)

    }


///////////////////////////////////////////////////////////////
    fun addLichHoc(tenLichHoc: String,ngayLichHoc: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("tenLH",tenLichHoc)
        values.put("ngayLH",ngayLichHoc)
        db.insert("TENLICHHOC", null, values)
        db.close()
    }

    // Sửa thông tin của một người dựa trên ID
    fun updateLichHoc(id: Int, tenLichHoc: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("tenLH", tenLichHoc)

        db.update("TENLICHHOC", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // Xóa một người dựa trên ID
    fun deleteLichHoc(id: Int) {
        val db = writableDatabase
        db.delete("TENLICHHOC", "id = ?", arrayOf(id.toString()))
        db.close()
    }
    @SuppressLint("Range")
    fun deleteAllLichHoc(){
        val db = writableDatabase
        val query = "SELECT * FROM TENLICHHOC"
        val cursor = db.rawQuery(query, null)
        Log.d("AAA","Chạy cursor trong database")
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                deleteLichHoc(id)
            } while (cursor.moveToNext())
        }

    }

    @SuppressLint("Range")
    fun getAllLichHoc(): ArrayList<TenLichHoc> {
        val lichHoc = ArrayList<TenLichHoc>()
        val db = readableDatabase
        val query = "SELECT * FROM TENLICHHOC"
        val cursor = db.rawQuery(query, null)
        Log.d("AAA","Chạy cursor trong database")
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("tenLH"))
                val date = cursor.getString(cursor.getColumnIndex("ngayLH"))
                Log.d("AAA",id.toString() + " "+ name + " "+ date)
                lichHoc.add(TenLichHoc(id, name,date))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return lichHoc
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    fun addMonHoc(idLH: Int,tenMonHoc: String,thu:Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("idLH",idLH)
        values.put("tenMon",tenMonHoc)
        values.put("thu",thu)
        db.insert("MONHOC", null, values)
        db.close()
    }


    fun updateMonHoc(id: Int, tenMonHoc: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("tenMon", tenMonHoc)

        db.update("MONHOC", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // Xóa một người dựa trên ID
    fun deleteMonHoc(id: Int) {
        val db = writableDatabase
        db.delete("MONHOC", "id = ?", arrayOf(id.toString()))
        db.close()
    }
    @SuppressLint("Range")
    fun deleteAllMonHoc(){
        val db = writableDatabase
        val query = "SELECT * FROM MONHOC"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                deleteMonHoc(id)
            } while (cursor.moveToNext())
        }

    }



    @SuppressLint("Range")
    fun getAllMonHoc(lh:Int,t:Int): ArrayList<MonHoc> {
        val monHoc = ArrayList<MonHoc>()
        val db = readableDatabase
        val query = "SELECT * FROM MONHOC "
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val idLH = cursor.getInt(cursor.getColumnIndex("idLH"))
                val name = cursor.getString(cursor.getColumnIndex("tenMon"))
                val thu = cursor.getInt(cursor.getColumnIndex("thu"))
                if(thu == t && idLH == lh){
                    monHoc.add(MonHoc(id, idLH,name,thu))
                }

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return monHoc
    }

    //////////////////////////////////////////////////////////

    fun addNDMonHoc(idMH: Int,tieudeMH: String,noidungMH: String,ngay: String,hinh: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("idMH",idMH)
        values.put("tieude",tieudeMH)
        values.put("noidung",noidungMH)
        values.put("ngay",ngay)
        values.put("hinhanh",hinh)
        db.insert("NOIDUNGMONHOC", null, values)
        db.close()
    }


    fun updateNDMonHoc(id: Int, tieudeMH: String,noidungMH: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("tieude", tieudeMH)
        values.put("noidung", noidungMH)
        db.update("NOIDUNGMONHOC", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // Xóa một người dựa trên ID
    fun deleteNDMonHoc(id: Int) {
        val db = writableDatabase
        db.delete("NOIDUNGMONHOC", "id = ?", arrayOf(id.toString()))
        db.close()
    }
    @SuppressLint("Range")
    fun deleteAllNDMonHoc(){
        val db = writableDatabase
        val query = "SELECT * FROM NOIDUNGMONHOC"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                deleteNDMonHoc(id)
            } while (cursor.moveToNext())
        }

    }



    @SuppressLint("Range")
    fun getAllNDMonHoc(idMonHoc:Int): ArrayList<NDMonHoc> {
        val ndmonHoc = ArrayList<NDMonHoc>()
        val db = readableDatabase
        val query = "SELECT * FROM NOIDUNGMONHOC "
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val idMH = cursor.getInt(cursor.getColumnIndex("idMH"))
                val tieude = cursor.getString(cursor.getColumnIndex("tieude"))
                val noidung = cursor.getString(cursor.getColumnIndex("noidung"))
                val ngay = cursor.getString(cursor.getColumnIndex("ngay"))
                val hinh = cursor.getString(cursor.getColumnIndex("hinhanh"))
                if(idMH == idMonHoc){
                    ndmonHoc.add(NDMonHoc(id, idMH,tieude,noidung,ngay,hinh))
                }

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return ndmonHoc
    }
    @SuppressLint("Range")
    fun getNDMonHocByID(idMon:Int,idMonHoc:Int): NDMonHoc? {
        val db = readableDatabase
        val query = "SELECT * FROM NOIDUNGMONHOC "
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val idMH = cursor.getInt(cursor.getColumnIndex("idMH"))
                val tieude = cursor.getString(cursor.getColumnIndex("tieude"))
                val noidung = cursor.getString(cursor.getColumnIndex("noidung"))
                val ngay = cursor.getString(cursor.getColumnIndex("ngay"))
                val hinh = cursor.getString(cursor.getColumnIndex("hinhanh"))
                if((idMH == idMonHoc) && (id == idMon)){
                    var ndmonHoc: NDMonHoc = NDMonHoc(id,idMH,tieude,ngay,noidung,hinh)
                    cursor.close()
                    db.close()
                    return ndmonHoc
                }

            } while (cursor.moveToNext())
        }
        return null
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    fun addGhiChu(tenGhiChu: String,ngay: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("tieude",tenGhiChu)
        values.put("ngay",ngay)
        values.put("noidung","")
        db.insert("GHICHU", null, values)
        db.close()
    }

    // Sửa thông tin của một người dựa trên ID
    fun updateGhiChu(id: Int, tenGhiChu: String,noidungGhiChu: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("tieude",tenGhiChu )
        values.put("noidung",noidungGhiChu )
        db.update("GHICHU", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }
    fun updateNameGhiChu(id: Int, tenGhiChu: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("tieude",tenGhiChu )
        db.update("GHICHU", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // Xóa một người dựa trên ID
    fun deleteGhiChu(id: Int) {
        val db = writableDatabase
        db.delete("GHICHU", "id = ?", arrayOf(id.toString()))
        db.close()
    }
    @SuppressLint("Range")
    fun deleteAllGhiChu(){
        val db = writableDatabase
        val query = "SELECT * FROM GHICHU"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                deleteGhiChu(id)
            } while (cursor.moveToNext())
        }

    }

    @SuppressLint("Range")
    fun getAllGhiChu(): ArrayList<GhiChu> {
        val ghiChu = ArrayList<GhiChu>()
        val db = readableDatabase
        val query = "SELECT * FROM GHICHU"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val tieude = cursor.getString(cursor.getColumnIndex("tieude"))
                val noidung = cursor.getString(cursor.getColumnIndex("noidung"))
                val ngay = cursor.getString(cursor.getColumnIndex("ngay"))
                ghiChu.add(GhiChu(id,tieude,noidung,ngay))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return ghiChu

    }
    @SuppressLint("Range")
    fun getGhiChuByID(idGC:Int): GhiChu? {
        val db = readableDatabase
        val query = "SELECT * FROM GHICHU "
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val tieude = cursor.getString(cursor.getColumnIndex("tieude"))
                val noidung = cursor.getString(cursor.getColumnIndex("noidung"))
                val ngay = cursor.getString(cursor.getColumnIndex("ngay"))
                if(id == idGC){
                    var ghiChu: GhiChu = GhiChu(id,tieude,noidung,ngay)
                    cursor.close()
                    db.close()
                    return ghiChu
                }

            } while (cursor.moveToNext())
        }
        return null
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    fun addThongBao(tenTB: String,ngay: String,doituong: Int, loai:Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("ten",tenTB)
        values.put("ngay",ngay)
        values.put("doituong",doituong)
        values.put("loai",loai)
        db.insert("THONGBAO", null, values)
        db.close()
    }

    // Sửa thông tin của một người dựa trên ID

    // Xóa một người dựa trên ID
    fun deleteThongBao(id: Int) {
        val db = writableDatabase
        db.delete("THONGBAO", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun deleteAllThongBao(){
        val db = writableDatabase
        val query = "SELECT * FROM THONGBAO"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                deleteThongBao(id)
            } while (cursor.moveToNext())
        }

    }

    @SuppressLint("Range")
    fun getAllThongBao(): ArrayList<ThongBao> {
        val thongBao = ArrayList<ThongBao>()
        val db = readableDatabase
        val query = "SELECT * FROM THONGBAO"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val ten = cursor.getString(cursor.getColumnIndex("ten"))
                val ngay = cursor.getString(cursor.getColumnIndex("ngay"))
                val doituong = cursor.getInt(cursor.getColumnIndex("doituong"))
                val loai = cursor.getInt(cursor.getColumnIndex("loai"))
                thongBao.add(ThongBao(id,ten,ngay,doituong,loai))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return thongBao

    }
    @SuppressLint("Range")
    fun getAllThongBaoToDay(today:String): ArrayList<ThongBao> {
        val thongBao = ArrayList<ThongBao>()
        val db = readableDatabase
        val query = "SELECT * FROM THONGBAO"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val ten = cursor.getString(cursor.getColumnIndex("ten"))
                val ngay = cursor.getString(cursor.getColumnIndex("ngay"))
                val doituong = cursor.getInt(cursor.getColumnIndex("doituong"))
                val loai = cursor.getInt(cursor.getColumnIndex("loai"))
                if(today.equals(ngay)) {
                    thongBao.add(ThongBao(id, ten, ngay, doituong, loai))
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return thongBao

    }

}
