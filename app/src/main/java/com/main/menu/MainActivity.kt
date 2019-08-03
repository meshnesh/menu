package com.main.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var editMenuName: EditText
    lateinit var categories: Spinner
    lateinit var buttonSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editMenuName = findViewById(R.id.editMenuName)
        categories = findViewById(R.id.spinner)
        buttonSave = findViewById(R.id.button)

        buttonSave.setOnClickListener() {
            saveMenu()
        }

    }

    private fun saveMenu() {
        val name = editMenuName.text.toString().trim()
        val category = categories.selectedItem.toString().trim()

        if (name.isEmpty()) {
            editMenuName.error = "Please enter a name "
            return
        }


        val ref = FirebaseDatabase.getInstance().getReference("menus")
        val menuId = ref.push().key.toString()

        val menu = Menu(menuId, name, category)

        ref.child(menuId).setValue(menu).addOnCompleteListener{
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
        }
    }
}
