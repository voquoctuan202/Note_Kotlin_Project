package com.example.note_kotlin_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AdapterDS_Ghichu<T>(var context: Context, var mangGC: ArrayList<GhiChu>): BaseAdapter() {
    class ViewHolder(row: View){
        var textTenGC : TextView


        init {
            textTenGC = row.findViewById(R.id.textTenGhiChu) as TextView
        }

    }
    override fun getCount(): Int {
        return mangGC.size
    }

    override fun getItem(p0: Int): Any {
        return mangGC.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(p1==null){
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.dong_ghichu,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = p1
            viewHolder = p1.tag as ViewHolder
        }
        var ghichu: GhiChu = getItem(p0) as GhiChu
        viewHolder.textTenGC.text = ghichu.tenGC
        return view as View
    }
}