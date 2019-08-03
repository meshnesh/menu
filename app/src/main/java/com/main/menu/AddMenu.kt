package com.main.menu

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddMenu: AppCompatActivity() {
    lateinit var editMenuName: EditText
    lateinit var description: EditText
    lateinit var categories: Spinner
    lateinit var buttonSave: Button
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView

    lateinit var menuList: MutableList<Menu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        menuList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("menus")

        editMenuName = findViewById(R.id.etMenuName)
        categories = findViewById(R.id.spinnerCategory)
        description = findViewById(R.id.etMenuDescription)
        buttonSave = findViewById(R.id.btnSave)

        buttonSave.setOnClickListener() {
            saveMenu()
        }

    }

    private fun saveMenu() {
        val name = editMenuName.text.toString()
        val category = categories.selectedItem.toString().trim()

        if (name.isEmpty()) {
            editMenuName.error = "Please enter a name "
            return
        }

        val menuId = ref.push().key.toString()

        val menu = Menu(menuId, name, category)

        ref.child(menuId).setValue(menu).addOnCompleteListener {
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            val goBack = Intent(this@AddMenu, MainActivity::class.java)
            startActivity(goBack)
        }
    }
}