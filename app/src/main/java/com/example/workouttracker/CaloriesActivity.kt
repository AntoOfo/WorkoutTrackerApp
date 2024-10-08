package com.example.workouttracker

import android.content.Intent
import android.graphics.Color
import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class CaloriesActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val levels: Array<String> = arrayOf("Sedentary", "Lightly Active",
        "Moderately Active", "Very Active", "Super Active")

    lateinit var radioGrp: RadioGroup       // creating radiogroup variable
    lateinit var selectedActivityLevel: String  // variable to store dropdown item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calories)

        val weightInput = findViewById<TextInputEditText>(R.id.weightInput)
        val heightInput = findViewById<TextInputEditText>(R.id.heightInput)
        val ageInput = findViewById<TextInputEditText>(R.id.ageInput)
        val nextBtn = findViewById<Button>(R.id.nextBtn)
        radioGrp = findViewById(R.id.radioGroup)
        val backBtn = findViewById<Button>(R.id.cbbackBtn)


        val dropdown = findViewById<Spinner>(R.id.activityDropdown)
        dropdown.onItemSelectedListener = this  // tells which item in list is clicked

        // custom arrayadapter for changing text colour
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, levels) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                // sets text colour for selected items
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(Color.BLACK)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                // sets text color for dropdown view
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.WHITE)
                return view
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdown.adapter = adapter


        // initialises with default selection
        selectedActivityLevel = levels[dropdown.selectedItemPosition]


        nextBtn.setOnClickListener{
            try {
                val weight = weightInput.text.toString().toInt()
                val height = heightInput.text.toString().toInt()
                val age = ageInput.text.toString().toInt()

                val selectedId = radioGrp.checkedRadioButtonId  // gets ID of selected button
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val gender = selectedRadioButton.text.toString()

                val intent = Intent(this, CalDisplayActivity::class.java)
                intent.putExtra("Weight", weight)   // int
                intent.putExtra("Height", height)   // int
                intent.putExtra("Age", age)         // int
                intent.putExtra("Gender", gender)   // string
                intent.putExtra("ActivityLevel", selectedActivityLevel)  // string

                startActivity(intent)
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter a valid number!", Toast.LENGTH_LONG).show()
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, "Please fill out form!", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Unexpected error!", Toast.LENGTH_LONG).show()
            }
        }


        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        selectedActivityLevel = levels[position]    // updates variable

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Null
    }
}
