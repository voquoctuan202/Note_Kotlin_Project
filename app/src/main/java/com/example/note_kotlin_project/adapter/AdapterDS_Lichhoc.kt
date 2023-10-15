package com.example.note_kotlin_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.dataclass.TenLichHoc

class AdapterDS_Lichhoc<T>(var context: Context, var mangLH: ArrayList<TenLichHoc>): BaseAdapter() {
    class ViewHolder(row: View){
        var textTenLH : TextView
        var tenNgayLH : TextView

        init {
            textTenLH = row.findViewById(R.id.textTenLichHoc) as TextView
            tenNgayLH =  row.findViewById(R.id.textNgayTaoLH) as TextView
        }

    }
    override fun getCount(): Int {
        return mangLH.size
    }

    override fun getItem(p0: Int): Any {
        return mangLH.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(p1==null){
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.dong_ds_lichhoc,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = p1
            viewHolder = p1.tag as ViewHolder
        }
        var lichhoc: TenLichHoc = getItem(p0) as TenLichHoc
        viewHolder.textTenLH.text = lichhoc.tenLH
        viewHolder.tenNgayLH.text = lichhoc.ngayLH
        return view as View
    }
}