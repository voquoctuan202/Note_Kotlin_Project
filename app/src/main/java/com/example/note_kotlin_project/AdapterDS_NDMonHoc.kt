package com.example.note_kotlin_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdapterDS_NDMonHoc(var context: Context,var mangNDMH: ArrayList<NDMonHoc>): BaseAdapter() {
    class ViewHolder(row: View) {
        var textTenNDMH: TextView
        var hinh_NDMH: ImageView

        init {
            textTenNDMH = row.findViewById(R.id.textTenNoiDung) as TextView
            hinh_NDMH = row.findViewById(R.id.hinh_ndmonhoc) as ImageView
        }
    }
    override fun getCount(): Int {
        return mangNDMH.size
    }

    override fun getItem(p0: Int): Any {
        return mangNDMH.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(p1==null){
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.dong_nd_monhoc,null)
            viewHolder =ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = p1
            viewHolder = p1.tag as ViewHolder
        }
        var noidungMH: NDMonHoc = getItem(p0) as NDMonHoc
        viewHolder.textTenNDMH.text = noidungMH.tenNDMonHoc
        viewHolder.hinh_NDMH.setImageResource(noidungMH.hinh_NDMonHoc)
        return view as View
    }
}