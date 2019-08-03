package com.main.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isEmpty
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var editMenuName: EditText
    lateinit var categories: Spinner
    lateinit var buttonSave: Button
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView

    lateinit var menuList: MutableList<Menu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("menus")

        editMenuName = findViewById(R.id.editMenuName)
        categories = findViewById(R.id.spinner)
        buttonSave = findViewById(R.id.button)
        listView = findViewById(R.id.menuList)

        buttonSave.setOnClickListener() {
            saveMenu()
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    menuList.clear()
                    for (m in p0.children) {
                        val menu = m.getValue(Menu::class.java)
                        menuList.add(menu!!)
                    }

                    val adapter = MenuAdapter(applicationContext, R.layout.menus, menuList)
                    listView.adapter = adapter
                }
            }
        })

    }

    private fun saveMenu() {
        val name = editMenuName.text.toString().trim()
        val category = categories.selectedItem.toString().trim()

        if (name.isEmpty()) {
            editMenuName.error = "Please enter a name "
            return
        }

        val menuId = ref.push().key.toString()

        val menu = Menu(menuId, name, category)

        ref.child(menuId).setValue(menu).addOnCompleteListener {
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
        }
    }
}
