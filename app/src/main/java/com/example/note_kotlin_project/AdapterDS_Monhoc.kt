package com.example.note_kotlin_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AdapterDS_Monhoc<T>(var context: Context, var mangMH: ArrayList<MonHoc>): BaseAdapter() {
    class ViewHolder(row: View) {
        var textTenMH: TextView

        init {
            textTenMH = row.findViewById(R.id.textTenMonHoc) as TextView
        }
    }

    override fun getCount(): Int {
        return mangMH.size
    }

    override fun getItem(p0: Int): Any {
        return mangMH.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(p1==null){
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.dong_monhoc,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = p1
            viewHolder = p1.tag as ViewHolder
        }
        var monhoc: MonHoc = getItem(p0) as MonHoc
        viewHolder.textTenMH.text = monhoc.tenMH

        return view as View
    }

}