package com.main.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isEmpty
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var listView: ListView
    lateinit var fab: TextView

    lateinit var menuList: MutableList<Menu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("menus")

        listView = findViewById(R.id.menuList)
        fab = findViewById(R.id.fab)




        fab.setOnClickListener {
            val addMenu = Intent(this@MainActivity, AddMenu::class.java)
            startActivity(addMenu)
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

                    val adapter = MenuAdapter(this@MainActivity, R.layout.menus, menuList)
                    listView.adapter = adapter
                }
            }
        })
    }
}
