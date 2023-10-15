package com.example.note_kotlin_project.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.note_kotlin_project.activity.NoiDung_MonHocActivity
import com.example.note_kotlin_project.dataclass.MonHoc
import com.example.note_kotlin_project.dataclass.NDMonHoc
import com.example.note_kotlin_project.dataclass.TenLichHoc

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

        val createNDMonHocTableSQL = "CREATE TABLE NOIDUNGMONHOC (id INTEGER PRIMARY KEY,idMH INTERGER ,tieude TEXT,noidung TEXT,hinhanh TEXT);"
        p0.execSQL(createNDMonHocTableSQL)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

        val dropTenLichHocTableSQL = "DROP TABLE IF EXISTS TENLICHHOC;"
        p0.execSQL( dropTenLichHocTableSQL)

        val dropMonHocTableSQL = "DROP TABLE IF EXISTS MONHOC;"
        p0.execSQL(dropMonHocTableSQL)

        val dropNDMonHocTableSQL = "DROP TABLE IF EXISTS NOIDUNGMONHOC;"
        p0.execSQL(dropNDMonHocTableSQL)


    }



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

    fun addNDMonHoc(idMH: Int,tieudeMH: String,noidungMH: String,hinh: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("idMH",idMH)
        values.put("tieude",tieudeMH)
        values.put("noidung",noidungMH)
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
                val hinh = cursor.getString(cursor.getColumnIndex("hinhanh"))
                if(idMH == idMonHoc){
                    ndmonHoc.add(NDMonHoc(id, idMH,tieude,noidung,hinh))
                }

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return ndmonHoc
    }

}