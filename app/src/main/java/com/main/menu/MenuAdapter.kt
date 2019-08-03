package com.main.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextSwitcher
import android.widget.TextView

class MenuAdapter(val mctx: Context, val layoutResId: Int, val menuList: List<Menu>):
    ArrayAdapter<Menu>(mctx, layoutResId, menuList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mctx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewMenuName = view.findViewById<TextView>(R.id.tvMenuName)
        val textViewCategory = view.findViewById<TextView>(R.id.tvMenuCategory)

        val menu = menuList[position]

        textViewMenuName.text = menu.name
        textViewCategory.text = menu.category

        return view
    }

}