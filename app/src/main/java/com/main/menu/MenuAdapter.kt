package com.main.menu

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class MenuAdapter(val mctx: Context, val layoutResId: Int, val menuList: List<Menu>):
    ArrayAdapter<Menu>(mctx, layoutResId, menuList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mctx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewMenuName = view.findViewById<TextView>(R.id.tvMenuName)
        val textViewCategory = view.findViewById<TextView>(R.id.tvMenuCategory)
        val textViewUpdate = view.findViewById<TextView>(R.id.tvUpdateName)

        val menu = menuList[position]

        textViewMenuName.text = menu.name
        textViewCategory.text = menu.category

        textViewUpdate.setOnClickListener {
            showUpdateDialog(menu)
        }

        return view
    }

    private fun showUpdateDialog(menu: Menu) {
        val builder = AlertDialog.Builder(mctx)
        builder.setTitle("Update Menu")

        val inflater = LayoutInflater.from(mctx)

        val view = inflater.inflate(R.layout.layout_update_menu, null)

        val editText = view.findViewById<EditText>(R.id.editUpdateMenuName)
        val spinner = view.findViewById<Spinner>(R.id.updateSpinner)

        editText.setText(menu.name)
//        add code for setting current value to spinner

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->
            val dbMenu = FirebaseDatabase.getInstance().getReference("menus")

            val name = editText.text.toString()
            val category = spinner.selectedItem.toString()

            if (name.isEmpty()) {
                editText.error = "Please enter a Menu Name "
                editText.requestFocus()
                return@setPositiveButton
            }

            val menu = Menu(menu.id, name, category)

            dbMenu.child(menu.id).setValue(menu)

            Toast.makeText(mctx, "Menu Updated", Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton("No") { dialog, which -> }

        val alert = builder.create()
        alert.show()
    }

}