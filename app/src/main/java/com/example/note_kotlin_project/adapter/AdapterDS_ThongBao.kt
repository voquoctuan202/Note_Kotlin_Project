package com.example.note_kotlin_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.dataclass.ThongBao

class AdapterDS_ThongBao<T>(var context: Context,var mangTB: ArrayList<ThongBao>): BaseAdapter() {
    class ViewHolder(row: View) {
        var textTenTB: TextView
        var textNgayTB: TextView

        init {
            textTenTB = row.findViewById(R.id.textTenThongBao) as TextView
            textNgayTB = row.findViewById(R.id.textNgayThongBao) as TextView
        }
    }
    override fun getCount(): Int {
        return mangTB.size
    }

    override fun getItem(p0: Int): Any {
        return mangTB.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(p1==null){
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.dong_thongbao,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = p1
            viewHolder = p1.tag as ViewHolder
        }
        var thongbao: ThongBao = getItem(p0) as ThongBao
        viewHolder.textTenTB.text = thongbao.tenTB
        viewHolder.textNgayTB.text = thongbao.ngayTB
        return view as View
    }
}